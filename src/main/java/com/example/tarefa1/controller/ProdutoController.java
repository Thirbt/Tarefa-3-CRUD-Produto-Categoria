package com.example.tarefa1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tarefa1.dto.ProdutoDTO;
import com.example.tarefa1.dto.ProdutoSaveDTO;
import com.example.tarefa1.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<ProdutoDTO> obterTodos(){
        return produtoService.obterTodos();
    }

    @GetMapping("{id}")
    public ProdutoDTO obterPorId(@PathVariable Long id){
        return produtoService.obterPorId(id);
    }

    @PostMapping
    public ProdutoDTO inserir(@RequestBody ProdutoSaveDTO produto){
        return produtoService.inserir(produto);
    }

    @PutMapping("{id}")
    public ProdutoDTO editar(@PathVariable Long id, @RequestBody ProdutoSaveDTO produtoSaveDTO){
        return produtoService.editar(id, produtoSaveDTO);
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable Long id){
        this.produtoService.excluir(id);
    }
}
