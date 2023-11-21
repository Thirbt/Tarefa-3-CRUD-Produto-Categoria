package com.example.tarefa1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tarefa1.dto.CategoriaDTO;
import com.example.tarefa1.dto.CategoriaInsertDTO;
import com.example.tarefa1.dto.CategoriaOnCreateDTO;
import com.example.tarefa1.dto.CategoriaSaveDTO;
import com.example.tarefa1.exception.GeneralApiError;
import com.example.tarefa1.model.Categoria;
import com.example.tarefa1.model.Produto;
import com.example.tarefa1.repository.CategoriaRepository;
import com.example.tarefa1.repository.ProdutoRepository;
import com.example.tarefa1.service.contract.CategoriaServiceContract;

@Service
public class CategoriaService implements CategoriaServiceContract {

        private CategoriaRepository categoriaRepository;

        private ProdutoRepository produtoRepository;

        public CategoriaService(CategoriaRepository categoriaRepository, ProdutoRepository produtoRepository) {
                this.categoriaRepository = categoriaRepository;
                this.produtoRepository = produtoRepository;
        }

        @Override
        public List<CategoriaDTO> obterTodos() {
                return categoriaRepository.findAll().stream().map(
                                (Categoria categoria) -> {
                                        List<Long> produtoIdList = categoria.getProdutos().stream()
                                                        .map(produto -> produto.getId()).collect(Collectors.toList());

                                        return CategoriaDTO.builder()
                                                        .id(categoria.getId())
                                                        .nome(categoria.getNome())
                                                        .listProdutoId(produtoIdList)
                                                        .build();
                                }).collect(Collectors.toList());
        }

        @Override
        public CategoriaDTO obterPorId(Long id) {
                Categoria categoria = categoriaRepository.findById(id)
                                .orElseThrow(() -> new GeneralApiError(
                                                "Não foi possível encontrar a categoria informada."));

                List<Long> listProdutoIds = categoria.getProdutos().stream().map(produto -> produto.getId())
                                .collect(Collectors.toList());

                return CategoriaDTO.builder()
                                .id(categoria.getId())
                                .nome(categoria.getNome())
                                .listProdutoId(listProdutoIds)
                                .build();
        }

        @Override
        public CategoriaOnCreateDTO inserir(CategoriaInsertDTO categoria) {

                Categoria categoriaSaved = categoriaRepository.save(Categoria.builder()
                                .nome(categoria.getNome())
                                .produtos(null)
                                .build());

                return CategoriaOnCreateDTO.builder()
                                .id(categoriaSaved.getId())
                                .nome(categoriaSaved.getNome())
                                .build();
        }

        @Override
        public CategoriaDTO editar(Long id, CategoriaSaveDTO categoria) {
                Categoria categoriaEncontrada = categoriaRepository.findById(id)
                                .orElseThrow(() -> new GeneralApiError(
                                                "Não foi possível encontrar a categoria informada."));

                List<Produto> produtoList = produtoRepository.findAllById(categoria.getListProdutoId());

                if (produtoList.size() != categoria.getListProdutoId().size()) {
                        throw new GeneralApiError("Um ou mais produtos informados não foram encontrados");
                }

                for (Produto produto : produtoList) {
                        produto.setCategoriaProdutos(categoriaEncontrada);
                        produtoRepository.save(produto);
                }

                categoriaEncontrada.setId(id);
                categoriaEncontrada.setNome(categoria.getNome());
                categoriaEncontrada.setProdutos(produtoList);

                categoriaRepository.save(categoriaEncontrada);

                List<Long> listProdutoId = produtoList.stream().map(produto -> produto.getId())
                                .collect(Collectors.toList());

                return CategoriaDTO.builder()
                                .id(categoriaEncontrada.getId())
                                .nome(categoriaEncontrada.getNome())
                                .listProdutoId(listProdutoId)
                                .build();
        }

        @Override
        public void excluir(Long id) {
                Categoria categoria = categoriaRepository.findById(id).orElseThrow(
                                () -> new GeneralApiError("Não foi possível encontrar a Categoria informada."));

                List<Produto> produtoList = categoria.getProdutos();

                if (!produtoList.isEmpty()) {
                        for (Produto produto : produtoList) {
                                produto.setCategoriaProdutos(null);
                                produtoRepository.save(produto);
                        }
                }
                categoriaRepository.deleteById(id);
        }
}
