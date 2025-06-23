package com.stefanini.controle_de_ofs.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.services.ServiceEmployee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    ServiceEmployee serviceEmployee;

    private static Employee employee;

    @BeforeAll
    public static void setup(){
        employee = new Employee();
        User user = new User();
        user.setName("Fabio");
        employee.setEmployee(user);
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/employees/findAll")).andExpect(status().isOk());
    }

    @Test
    public void findByIdTest() throws Exception {
        mockMvc.perform(get("/employees/findByID/1")).andExpect(status().isOk());
    }

    @Test
    public void findByStatusTest() throws Exception {
        mockMvc.perform(get("/employees/findByStatus/Iniciada")).andExpect(status().isOk());
    }

    @Test
    public void editTest() throws Exception {
        mockMvc.perform(put("/employees/edit").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee))).andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(delete("/employees/delete/1")).andExpect(status().isOk());
    }
}