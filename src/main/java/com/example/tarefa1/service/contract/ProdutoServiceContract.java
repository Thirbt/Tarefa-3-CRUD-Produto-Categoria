package com.example.tarefa1.service.contract;

import java.util.List;

import com.example.tarefa1.dto.ProdutoDTO;
import com.example.tarefa1.dto.ProdutoSaveDTO;

public interface ProdutoServiceContract {
    ProdutoDTO inserir(ProdutoSaveDTO produto);
    ProdutoDTO editar(Long id, ProdutoSaveDTO produto);
    void excluir(Long id);
    List<ProdutoDTO> obterTodos();
    ProdutoDTO obterPorId(Long id);
}
