package com.stefanini.controle_de_ofs.controllers;

import com.stefanini.controle_de_ofs.models.OrdemFornecimento;
import com.stefanini.controle_de_ofs.services.OrdemFornecimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@Tag(name = "Ordem de Fornecimento")
@RestController
@RequestMapping("/ordemFornecimento")
public class OrdemFornecimentoController {
    @Autowired
    private OrdemFornecimentoService ordemFornecimentoService;

    @GetMapping("/quantidadePorMes")
    public ResponseEntity<?> listarPorMes(){
        return ordemFornecimentoService.listarPorMes();
    }

    @Operation(summary = "Cadastro")
    @PostMapping("/criar")
    public ResponseEntity<?> criarOrdemFornecimento(@RequestBody OrdemFornecimento obj){
        return ordemFornecimentoService.cadastrar(obj);
    }

    @Operation(summary = "Listagem")
    @GetMapping("/listar/{rt}")
    public ResponseEntity<?> listarTudo(@PathVariable Integer rt){
        return ordemFornecimentoService.listarTudo(rt);
    }

    @Operation(summary = "Listagem por código")
    @GetMapping("/listarPorCodigo/{codigo}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer codigo){
        return ordemFornecimentoService.listarPorCodigo(codigo);
    }

    @GetMapping("/contarPorStatus/{status}")
    public ResponseEntity<?> ContarPorStatus(@PathVariable String status){
        return ordemFornecimentoService.ContarPorStatus(status);
    }

    @Operation(summary = "Alteração")
    @PutMapping("/alterar")
    public ResponseEntity<?> alterarOrdemFornecimento(@RequestBody OrdemFornecimento obj){
        return ordemFornecimentoService.alterar(obj);
    }

    @Operation(summary = "Exclusão por código")
    @DeleteMapping("/deletar/{codigo}")
    public ResponseEntity<?> deletarOrdemFornecimento(@PathVariable Integer codigo){
        return ordemFornecimentoService.excluir(codigo);
    }
}
