package com.stefanini.controle_de_ofs.services;

import com.stefanini.controle_de_ofs.models.Mensagem;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceUserTest {

    @Mock
    private RepositoryUser action;

    @Mock
    private Mensagem mensagem;

    @InjectMocks
    private ServiceUser serviceUser;

    private User user;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setName("teste");
        user.setEmail("teste@email.com");
        user.setRole("user");
        user.setPassword("senha123");

        userList = new ArrayList<>();
        userList.add(user);
    }

    @Test
    void listarNomes() {
        List<String> nomes = Arrays.asList("User 1", "User 2", "User 3");
        when(action.findAllName()).thenReturn(nomes);

        List<String> resultado = serviceUser.listarNomes();

        assertEquals(nomes, resultado);
    }

    @Test
    void findAll() {
        when(action.findAll()).thenReturn(userList);

        ResponseEntity<?> response = serviceUser.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void findByIdSucesso() {
        when(action.countById(1)).thenReturn(1);
        when(action.findById(1)).thenReturn(userList);

        ResponseEntity<?> response = serviceUser.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void findByIdNaoEncontrado() {
        when(action.countById(999)).thenReturn(0);

        ResponseEntity<?> response = serviceUser.findById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findByRoleSucesso() {
        when(action.findByRole("user")).thenReturn(userList);

        ResponseEntity<?> response = serviceUser.findByRole("user");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void findByRoleVazio() {
        when(action.findByRole("admin")).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = serviceUser.findByRole("admin");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void editSucesso() {
        when(action.countById(1)).thenReturn(1);
        when(action.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = serviceUser.edit(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void editIdNaoEncontrado() {
        when(action.countById(999)).thenReturn(0);

        user.setId(999);
        ResponseEntity<?> response = serviceUser.edit(user);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void editSemNome() {
        when(action.countById(1)).thenReturn(1);
        user.setName("");

        ResponseEntity<?> response = serviceUser.edit(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editSemEmail() {
        when(action.countById(1)).thenReturn(1);
        user.setEmail("");

        ResponseEntity<?> response = serviceUser.edit(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void editSemRole() {
        when(action.countById(1)).thenReturn(1);
        user.setRole("");

        ResponseEntity<?> response = serviceUser.edit(user);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteByIdSucesso() {
        when(action.countById(1)).thenReturn(1);
        when(action.findById(1)).thenReturn(userList);

        ResponseEntity<?> response = serviceUser.deleteById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteByIdNaoEncontrado() {
        when(action.countById(999)).thenReturn(0);

        ResponseEntity<?> response = serviceUser.deleteById(999);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteByIdListaVazia() {
        when(action.countById(1)).thenReturn(1);
         when(action.findById(1)).thenReturn(new ArrayList<>());


        ResponseEntity<?> response = serviceUser.deleteById(1);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void findAllManagersSucesso() {
        when(action.findManagers()).thenReturn(userList);

        ResponseEntity<?> response = serviceUser.findAllManagers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());
    }

    @Test
    void findAllManagersVazio() {
        when(action.findManagers()).thenReturn(new ArrayList<>());

        ResponseEntity<?> response = serviceUser.findAllManagers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}