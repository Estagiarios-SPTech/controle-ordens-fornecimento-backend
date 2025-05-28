package com.stefanini.controle_de_ofs.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.services.OrdemFornecimentoService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrdemFornecimentoController.class)
class OrdemFornecimentoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    OrdemFornecimentoService ordemFornecimentoService;

    private static OrdemFornecimento ordemFornecimento;

    @BeforeAll
    public static void setup(){
        ordemFornecimento = new OrdemFornecimento();
        Employee employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setEmployee(user);
        ordemFornecimento.setDescription("test");
        ordemFornecimento.setStatus("test");
        ordemFornecimento.setCollaborator(employee);
    }

    @Test
    public void listarPorMesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ordemFornecimento/quantidadePorMes")).andExpect(status().isOk());
    }

    @Test
    public void criarOrdemFornecimentoTest() throws Exception {
        when(ordemFornecimentoService.cadastrar(any(OrdemFornecimento.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        mockMvc.perform(post("/ordemFornecimento/criar").
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ordemFornecimento))).andExpect(status().isCreated());
    }

    @Test
    public void listarTudoTest() throws Exception {
        mockMvc.perform(get("/ordemFornecimento/listar"))
                .andExpect(status().isOk());
    }

    @Test
    public void listarPorIdTest() throws Exception {
        mockMvc.perform(get("/ordemFornecimento/listarPorCodigo/1")).andExpect(status().isOk());
    }

    @Test
    public void ContarPorStatusTest() throws Exception {
        mockMvc.perform(get("/ordemFornecimento/contarPorStatus/Pendente de Cadastramento")).andExpect(status().isOk());
    }

    @Test
    public void alterarOrdemFornecimentoTest() throws Exception {
        mockMvc.perform(put("/ordemFornecimento/alterar").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ordemFornecimento))).andExpect(status().isOk());
    }

    @Test
    public void deletarOrdemFornecimentoTest() throws Exception {
        mockMvc.perform(delete("/ordemFornecimento/deletar/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(ordemFornecimento))).andExpect(status().isOk());
    }

}