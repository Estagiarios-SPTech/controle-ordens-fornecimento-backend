package com.stefanini.controle_de_ofs.services;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.Mensagem;
import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryEmployee;
import com.stefanini.controle_de_ofs.repository.RepositoryOrdemFornecimento;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrdemFornecimentoServiceTest {
    @InjectMocks
    private OrdemFornecimentoService ordemFornecimentoService;

    @Mock
    private RepositoryOrdemFornecimento acao;

    @Mock
    private RepositoryEmployee acaoEmployee;

    @Mock
    private RepositoryUser acaoUser;

    @Mock
    private Mensagem mensagem;

    @Test
    public void cadastrarComDescriptionVazio(){
        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setUser(user);
        ordemFornecimento.setDescription("");
        ordemFornecimento.setStatus("Pendente de Cadastramento");
        ordemFornecimento.setEmployee(employee);

        ResponseEntity resposta = ordemFornecimentoService.cadastrar(ordemFornecimento);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), resposta);
    }

    @Test
    public void cadastrarComStatusVazio(){
        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setUser(user);
        ordemFornecimento.setDescription("Manutenção");
        ordemFornecimento.setStatus("");
        ordemFornecimento.setEmployee(employee);

        ResponseEntity resposta = ordemFornecimentoService.cadastrar(ordemFornecimento);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), resposta);
    }

    @Test
    public void cadastrarTest(){
        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setUser(user);
        ordemFornecimento.setDescription("Manutenção");
        ordemFornecimento.setStatus("Pendente de Cadastramento");
        ordemFornecimento.setEmployee(employee);

        when(acao.save(ordemFornecimento)).thenReturn(ordemFornecimento);
        ResponseEntity resposta = ordemFornecimentoService.cadastrar(ordemFornecimento);

        assertEquals(new ResponseEntity<>(ordemFornecimento,HttpStatus.CREATED), resposta);
    }

    @Test
    public void listarTudoTest(){
        ResponseEntity resposta = ordemFornecimentoService.listarTudo(2);

        assertEquals(new ResponseEntity<>(acao.acharTudoSemMandarObjetoChaveEstrangeira(2),HttpStatus.OK), resposta);
    }

    @Test
    public void ContarPorStatusTest(){
        ResponseEntity resposta = ordemFornecimentoService.ContarPorStatus("Pendente de Cadastro");

        assertEquals(new ResponseEntity<>(acao.findByStatus("Pendente de Cadastro").size(), HttpStatus.OK), resposta);
    }

    @Test
    public void listarPorMesTest(){
        ResponseEntity resposta = ordemFornecimentoService.listarPorMes();

        assertEquals(new ResponseEntity<>(acao.listarPorMes(),HttpStatus.OK), resposta);
    }

    @Test
    public void listarPorCodigoNaoEncontrado(){
        when(acao.countByCodigo(1)).thenReturn(0);

        ResponseEntity resposta = ordemFornecimentoService.listarPorCodigo(1);

        assertEquals(new ResponseEntity<>(mensagem,HttpStatus.NOT_FOUND), resposta);
    }

    @Test
    public void listarPorCodigoEncontrado(){
        when(acao.countByCodigo(1)).thenReturn(1);

        ResponseEntity resposta = ordemFornecimentoService.listarPorCodigo(1);

        assertEquals(new ResponseEntity<>(acao.findByCodigo(1),HttpStatus.OK), resposta);
    }

//    @Test
//    public void alterarCodigoNaoEncontrado(){
//        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
//        ordemFornecimento.setCodigo(1);
//        Employee employee = new Employee();
//        User user = new User();
//        user.setName("Fabio");
//        employee.setUser(user);
//        ordemFornecimento.setDescription("Manutenção");
//        ordemFornecimento.setStatus("Pendente de Cadastramento");
//        ordemFornecimento.setEmployee(employee);
//
//        when(acao.countByCodigo(ordemFornecimento.getCodigo())).thenReturn(0);
//
//        ResponseEntity resposta = ordemFornecimentoService.alterar(ordemFornecimento);
//
//        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND), resposta);
//    }

//    @Test
//    public void alterarDescriptionVazio(){
//        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
//        ordemFornecimento.setCodigo(1);
//        Employee employee = new Employee();
//        User user = new User();
//        user.setName("Fabio");
//        employee.setUser(user);
//        ordemFornecimento.setDescription("");
//        ordemFornecimento.setStatus("Pendente de Cadastramento");
//        ordemFornecimento.setEmployee(employee);
//
//        when(acao.countByCodigo(ordemFornecimento.getCodigo())).thenReturn(1);
//
//        ResponseEntity resposta = ordemFornecimentoService.alterar(ordemFornecimento);
//
//        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), resposta);
//    }
//
//    @Test
//    public void alterarStatusVazio(){
//        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
//        ordemFornecimento.setCodigo(1);
//        Employee employee = new Employee();
//        User user = new User();
//        user.setName("Fabio");
//        employee.setUser(user);
//        ordemFornecimento.setDescription("Manutenção");
//        ordemFornecimento.setStatus("");
//        ordemFornecimento.setEmployee(employee);
//
//        when(acao.countByCodigo(ordemFornecimento.getCodigo())).thenReturn(1);
//
//        ResponseEntity resposta = ordemFornecimentoService.alterar(ordemFornecimento);
//
//        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), resposta);
//    }
//
//    @Test
//    public void alterarSucesso(){
//        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
//        ordemFornecimento.setCodigo(1);
//        Employee employee = new Employee();
//        User user = new User();
//        user.setName("Fabio");
//        employee.setUser(user);
//        ordemFornecimento.setDescription("Manutenção");
//        ordemFornecimento.setStatus("Pendente de Cadastramento");
//        ordemFornecimento.setEmployee(employee);
//
//        when(acao.countByCodigo(ordemFornecimento.getCodigo())).thenReturn(1);
//        when(acao.findByCodigo(ordemFornecimento.getCodigo())).thenReturn(ordemFornecimento);
//        when(acao.save(ordemFornecimento)).thenReturn(ordemFornecimento);
//
//        ResponseEntity resposta = ordemFornecimentoService.alterar(ordemFornecimento);
//
//        assertEquals(new ResponseEntity<>(acao.findByCodigo(ordemFornecimento.getCodigo()), HttpStatus.CREATED), resposta);
//    }

    @Test
    public void excluirCodigoNaoEncontrado(){
        when(acao.countByCodigo(any(Integer.class))).thenReturn(0);

        ResponseEntity resposta = ordemFornecimentoService.excluir(1);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND), resposta);
    }

    @Test
    public void excluirComSucesso(){
        when(acao.countByCodigo(any(Integer.class))).thenReturn(1);

        ResponseEntity resposta = ordemFornecimentoService.excluir(1);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.OK), resposta);
    }
}