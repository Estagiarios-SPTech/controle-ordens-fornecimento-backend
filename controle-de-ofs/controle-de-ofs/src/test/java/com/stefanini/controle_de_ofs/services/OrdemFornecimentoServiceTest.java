package com.stefanini.controle_de_ofs.services;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.Mensagem;
import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryEmployee;
import com.stefanini.controle_de_ofs.repository.RepositoryOrdemFornecimento;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrdemFornecimentoServiceTest {
    @InjectMocks
    OrdemFornecimentoService ordemFornecimentoService;

    @Mock
    RepositoryOrdemFornecimento acao;

    @Mock
    RepositoryEmployee acaoEmployee;

    @Mock
    RepositoryUser acaoUser;

    @Mock
    Mensagem mensagem;

    @Test
    public void cadastrarComDescriptionVazio(){
        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setEmployee(user);
        ordemFornecimento.setDescription("");
        ordemFornecimento.setStatus("Pendente de Cadastramento");
        ordemFornecimento.setCollaborator(employee);

        ResponseEntity resposta = ordemFornecimentoService.cadastrar(ordemFornecimento);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), resposta);
    }

    @Test
    public void cadastrarComStatusVazio(){
        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setEmployee(user);
        ordemFornecimento.setDescription("Manutenção");
        ordemFornecimento.setStatus("");
        ordemFornecimento.setCollaborator(employee);

        ResponseEntity resposta = ordemFornecimentoService.cadastrar(ordemFornecimento);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), resposta);
    }

    @Test
    public void cadastrarTest(){
        OrdemFornecimento ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setEmployee(user);
        ordemFornecimento.setDescription("Manutenção");
        ordemFornecimento.setStatus("Pendente de Cadastramento");
        ordemFornecimento.setCollaborator(employee);

        when(acao.save(ordemFornecimento)).thenReturn(ordemFornecimento);
        ResponseEntity resposta = ordemFornecimentoService.cadastrar(ordemFornecimento);

        assertEquals(new ResponseEntity<>(ordemFornecimento,HttpStatus.CREATED), resposta);
    }

//    @Test
//    public void listarTudo(){
//        ResponseEntity resposta = ordemFornecimentoService.listarTudo();
//
//        assertEquals(new ResponseEntity<>(HttpStatus.OK), resposta);
//    }
}