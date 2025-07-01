package com.stefanini.controle_de_ofs.services;

import com.stefanini.controle_de_ofs.models.Mensagem;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceUser {
    private String message;

    @Autowired
    private RepositoryUser action;

    @Autowired
    private Mensagem mensagem;

    public List<String> listarEmails(){
        return action.findAllEmail();
    }

    public ResponseEntity<?> findAll(){
        return new ResponseEntity<>(action.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> findById(int  id){
        if (action.countById(id) == 0){
            mensagem.setMessage("Usuário não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(action.findById(id), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> findByRole(String role){
        if (action.findByRole(role).isEmpty()){
            mensagem.setMessage("É necessário inserir um cargo");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(action.findByRole(role), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> edit(User obj){
        if(obj.getName().isEmpty()){
            mensagem.setMessage("O nome precisa ser preenchido");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (obj.getEmail().isEmpty()) {
            mensagem.setMessage("Informe um endereço de e-mail válido");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else if (obj.getRole().isEmpty()) {
            mensagem.setMessage("Informe um cargo válido");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(action.save(obj), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteById(int id) {
        if (action.countById(id) == 0) {
            mensagem.setMessage("Usuário não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            List<User> obj = action.findById(id);
            if (!obj.isEmpty()) {
                User user = obj.get(0);;
                action.delete(user);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    public ResponseEntity<?> findAllManagers(){
        if (action.findManagers().isEmpty()){
            mensagem.setMessage("Nenhum gerente encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(action.findManagers(), HttpStatus.OK);
        }
    }




}
