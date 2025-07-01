package com.stefanini.controle_de_ofs.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.services.ServiceUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    ServiceUser serviceUser;
    private static User user;

    @BeforeAll
    public static void setup() {
        user = new User();
        user.setId(1);
        user.setName("teste");
        user.setEmail("teste@email.com");
        user.setRole("user");
        user.setPassword("senha123");
    }

    @Test
    public void listarNomesTest() throws Exception {
        List<String> nomes = Arrays.asList("User 1", "User 2", "User 3");
        when(serviceUser.listarEmails()).thenReturn(nomes);
        mockMvc.perform(get("/users/listarNomes"))
                .andExpect(status().isOk());
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/users/findAll"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdTest() throws Exception {

        mockMvc.perform(get("/users/findByID/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void editTest() throws Exception {

        mockMvc.perform(put("/users/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteByIdTest() throws Exception {

        mockMvc.perform(delete("/users/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findManagerTest() throws Exception {
        User manager = new User();
        manager.setId(2);
        manager.setName("Manager");
        manager.setEmail("manager@example.com");
        manager.setRole("manager");

        List<User> managers = List.of(manager);
        when(serviceUser.findByRole("manager")).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(get("/users/Managers"))
                .andExpect(status().isOk());
    }

    @Test
    public void findRtsTest() throws Exception {
        User rt = new User();
        rt.setId(3);
        rt.setName("RT");
        rt.setEmail("rt@example.com");
        rt.setRole("rt");

        List<User> rts = List.of(rt);
        when(serviceUser.findByRole("rt")).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mockMvc.perform(get("/users/RTs"))
                .andExpect(status().isOk());
    }
}