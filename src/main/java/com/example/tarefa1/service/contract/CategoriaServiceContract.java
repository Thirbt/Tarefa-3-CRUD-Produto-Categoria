package com.example.tarefa1.service.contract;
import java.util.List;
import com.example.tarefa1.dto.CategoriaDTO;
import com.example.tarefa1.dto.CategoriaInsertDTO;
import com.example.tarefa1.dto.CategoriaOnCreateDTO;
import com.example.tarefa1.dto.CategoriaSaveDTO;

public interface CategoriaServiceContract {
    CategoriaOnCreateDTO inserir(CategoriaInsertDTO categoria);
    CategoriaDTO editar(Long id, CategoriaSaveDTO categoria);
    void excluir(Long id);
    List<CategoriaDTO> obterTodos();
    CategoriaDTO obterPorId(Long id);
}
