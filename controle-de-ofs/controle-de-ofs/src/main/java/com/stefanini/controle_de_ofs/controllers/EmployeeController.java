package com.stefanini.controle_de_ofs.controllers;

import com.stefanini.controle_de_ofs.models.Employee;
import com.stefanini.controle_de_ofs.models.User;
import com.stefanini.controle_de_ofs.repository.RepositoryEmployee;
import com.stefanini.controle_de_ofs.repository.RepositoryUser;
import com.stefanini.controle_de_ofs.services.ServiceEmployee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employees")
@RequestMapping("/employees")
@RestController
@CrossOrigin(origins = "*")
public class EmployeeController {
    @Autowired
    private ServiceEmployee serviceEmployee;

    // rotas para listar todos, cadastrar novos, filtrar por id, filtrar nome, editar, deletar.

    @Operation(summary = "Rota para listar todos os funcionários")
    @GetMapping("/findAll")
    public ResponseEntity<?> findAll() {
        return serviceEmployee.findAll();
    }

    @Operation(summary = "Rota para buscar funcionário pelo ID")
    @GetMapping("/findByID/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return serviceEmployee.findById(id);
    }

    @Operation(summary = "Rota para buscar funcionários pelo status")
    @GetMapping("/findByStatus/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable String status) {
        return serviceEmployee.findByStatus(status);
    }

    @Operation(summary = "Rota para editar um funcionário existente")
    @PutMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody Employee obj) {
        return serviceEmployee.edit(obj);
    }

    @Operation(summary = "Rota para deletar um funcionário pelo ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return serviceEmployee.deleteById(id);
    }

}
