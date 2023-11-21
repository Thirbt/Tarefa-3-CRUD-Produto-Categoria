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

import com.example.tarefa1.dto.CategoriaDTO;
import com.example.tarefa1.dto.CategoriaInsertDTO;
import com.example.tarefa1.dto.CategoriaOnCreateDTO;
import com.example.tarefa1.dto.CategoriaSaveDTO;
import com.example.tarefa1.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    
    private CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaDTO> obterTodos(){
        return categoriaService.obterTodos();
    }

    @GetMapping("{id}")
    public CategoriaDTO obterPorId(@PathVariable Long id){
        return categoriaService.obterPorId(id);
    }

    @PostMapping
    public CategoriaOnCreateDTO inserir(@RequestBody CategoriaInsertDTO categoria){
        return categoriaService.inserir(categoria);
    }

    @PutMapping("{id}")
    public CategoriaDTO editar(@PathVariable Long id, @RequestBody CategoriaSaveDTO categoria){
        return categoriaService.editar(id, categoria);
    }

    @DeleteMapping("{id}")
    public void excluir(@PathVariable Long id){
        this.categoriaService.excluir(id);
    }
}
