package com.stefanini.controle_de_ofs.services;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.Mensagem;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryEmployee;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ServiceEmployeeTest {
    @InjectMocks
    private ServiceEmployee serviceEmployee;

    @Mock
    private RepositoryEmployee action;

    @Mock
    private RepositoryUser actionUser;

    @Mock
    private Mensagem mensagem;

    @Test
    public void findAllTest(){
        assertEquals(new ResponseEntity<>(action.findAll(), HttpStatus.OK), serviceEmployee.findAll());
    }

    @Test
    public void colaboradorNaoEncontrado(){
        when(action.existsById(anyInt())).thenReturn(false);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND), serviceEmployee.findById(anyInt()));
    }

    @Test
    public void colaboradorEncontrado(){
        when(action.existsById(anyInt())).thenReturn(true);
        when(action.findById(anyInt())).thenReturn(Optional.of(new Employee()));

        assertEquals(new ResponseEntity<>(action.findById(anyInt()).get(), HttpStatus.OK), serviceEmployee.findById(anyInt()));
    }

    @Test
    public void colaboradorNaoEncontradoPorStatus(){
        when(action.findByStatus(anyString())).thenReturn(new ArrayList<>());

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND), serviceEmployee.findByStatus(anyString()));
    }

    @Test
    public void colaboradorEncontradoPorStatus(){
        Employee employee = new Employee();
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        when(action.findByStatus(anyString())).thenReturn(employees);

        assertEquals(new ResponseEntity<>(action.findByStatus(anyString()), HttpStatus.OK), serviceEmployee.findByStatus(anyString()));
    }

    @Test
    public void colaboradorNaoEncontradoParaEditar(){
        Employee employee = new Employee();
        employee.setId(1);
        when(action.findById(anyInt())).thenReturn(Optional.empty());

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND), serviceEmployee.edit(employee));
    }

    @Test
    public void colaboradorNullParaEditar(){
        Employee employee = new Employee();
        employee.setId(1);
        when(action.findById(anyInt())).thenReturn(Optional.of(employee));

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), serviceEmployee.edit(employee));
    }

    @Test
    public void colaboradorComStatusNullParaEditar(){
        Employee employee = new Employee();
        employee.setId(1);
        User colaborador = new User();
        employee.setUser(colaborador);
        when(action.findById(anyInt())).thenReturn(Optional.of(employee));

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), serviceEmployee.edit(employee));
    }

    @Test
    public void colaboradorComStatusVazioParaEditar(){
        Employee employee = new Employee();
        employee.setId(1);
        User colaborador = new User();
        employee.setUser(colaborador);
        employee.setStatus("");
        when(action.findById(anyInt())).thenReturn(Optional.of(employee));

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST), serviceEmployee.edit(employee));
    }

    @Test
    public void colaboradorEditado(){
        Employee employee = new Employee();
        employee.setId(1);
        User colaborador = new User();
        employee.setUser(colaborador);
        employee.setStatus("Pendente de Cadastro");
        when(action.findById(anyInt())).thenReturn(Optional.of(employee));

        assertEquals(new ResponseEntity<>(action.save(employee), HttpStatus.OK), serviceEmployee.edit(employee));
    }

    @Test
    public void colaboradorNaoEncontradoParaDeletar(){
        Employee employee = new Employee();
        employee.setId(1);

        when(action.existsById(anyInt())).thenReturn(false);

        assertEquals(new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND), serviceEmployee.deleteById(anyInt()));
    }

    @Test
    public void colaboradorDeletado(){
        Employee employee = new Employee();
        employee.setId(1);

        when(action.existsById(anyInt())).thenReturn(true);

        assertEquals(new ResponseEntity<>(HttpStatus.OK), serviceEmployee.deleteById(anyInt()));
    }
}