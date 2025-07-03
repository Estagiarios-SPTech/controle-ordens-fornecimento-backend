package com.stefanini.controle_de_ofs.services;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.Mensagem;
import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryEmployee;
import com.stefanini.controle_de_ofs.repository.RepositoryOrdemFornecimento;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrdemFornecimentoService {
    private String message;

    @Autowired
    private RepositoryOrdemFornecimento acao;
    @Autowired
    private RepositoryEmployee acaoEmployee;
    @Autowired
    private RepositoryUser acaoUser;

    @Autowired
    private Mensagem mensagem;

    public ResponseEntity<?> cadastrar(OrdemFornecimento obj){
        if(obj.getDescription() == "" ||
            obj.getStatus() == ""){
            mensagem.setMessage("Nenhum campo pode estar vazio");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else{
            User objUser = acaoUser.findById(obj.getEmployee().getUser().getId()).orElse(null);
            Employee objEmployee = acaoEmployee.findByUser(objUser);
            obj.setEmployee(objEmployee);
            obj.setCreated_at(LocalDate.now());
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> listarTudo(Integer rt){
        return new ResponseEntity<>(acao.findAllByEmployeeRtIdOrderByCodigoDesc(rt), HttpStatus.OK);
    }

    public ResponseEntity<?> ContarPorStatus(String status){
        return new ResponseEntity<>(acao.findByStatus(status).size(), HttpStatus.OK);
    }

    public ResponseEntity<?> listarPorMes(){
        return new ResponseEntity<>(acao.listarPorMes(), HttpStatus.OK);
    }

    public ResponseEntity<?> listarPorCodigo(Integer codigo){
        if(acao.countByCodigo(codigo) == 0){
            mensagem.setMessage("Código não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(acao.findByCodigo(codigo), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> alterar(OrdemFornecimento obj){
        if(acao.countByCodigo(obj.getCodigo()) == 0){
            mensagem.setMessage("Código não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        else if(obj.getDescription() == "" ||
                obj.getStatus() == ""){
            mensagem.setMessage("Nenhum campo pode estar vazio");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }
        else{
            //Procura o objeto usuario relacionado
            User objUser = acaoUser.findById(obj.getEmployee().getUser().getId()).orElse(null);
            //Coloca em Employee
            Employee objEmployee = acaoEmployee.findByUser(objUser);
            //Coloca em OrdemFornecimento
            obj.setEmployee(objEmployee);
            //Procura o obj OrdemFornecimento com base no codigo
            OrdemFornecimento objEncontrado = acao.findByCodigo(obj.getCodigo());
            //Pega a data que foi criada para impedir alterações
            obj.setCreated_at(objEncontrado.getCreated_at());
            obj.setUpdated_at(LocalDate.now());
            return new ResponseEntity<>(acao.save(obj), HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> excluir(Integer codigo){
        if(acao.countByCodigo(codigo) == 0){
            mensagem.setMessage("Código não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
        else{
            OrdemFornecimento objEncontrado = acao.findByCodigo(codigo);
            acao.delete(objEncontrado);

            mensagem.setMessage("Exclusão realizada com sucesso");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }
    }
}
