package com.example.tarefa1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.tarefa1.dto.ProdutoDTO;
import com.example.tarefa1.dto.ProdutoSaveDTO;
import com.example.tarefa1.exception.GeneralApiError;
import com.example.tarefa1.model.Categoria;
import com.example.tarefa1.model.Produto;
import com.example.tarefa1.repository.CategoriaRepository;
import com.example.tarefa1.repository.ProdutoRepository;
import com.example.tarefa1.service.contract.ProdutoServiceContract;

@Service
public class ProdutoService implements ProdutoServiceContract {

        private ProdutoRepository produtoRepository;

        private CategoriaRepository categoriaRepository;

        public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
                this.produtoRepository = produtoRepository;
                this.categoriaRepository = categoriaRepository;
        }

        @Override
        public List<ProdutoDTO> obterTodos() {
                return produtoRepository.findAll().stream().map(
                                (Produto produto) -> {
                                        return ProdutoDTO.builder()
                                                        .id(produto.getId())
                                                        .nome(produto.getNome())
                                                        .preco(produto.getPreco())
                                                        .nomeCategoria(produto.getCategoriaProdutos() == null
                                                                        ? "Produto sem categoria"
                                                                        : produto.getCategoriaProdutos().getNome())
                                                        .build();
                                }).collect(Collectors.toList());
        }

        @Override
        public ProdutoDTO obterPorId(Long id) {
                Produto produto = produtoRepository.findById(id)
                                .orElseThrow(() -> new GeneralApiError(
                                                "Não foi possível encontrar o produto informado."));

                return ProdutoDTO.builder()
                                .id(produto.getId())
                                .nome(produto.getNome())
                                .preco(produto.getPreco())
                                .nomeCategoria(produto.getCategoriaProdutos().getNome())
                                .build();
        }

        @Override
        public ProdutoDTO inserir(ProdutoSaveDTO produto) {
                Categoria categoria = categoriaRepository.findByNome(produto.getNomeCategoria())
                                .orElseThrow(() -> new GeneralApiError(
                                                "Não foi possível encontrar a Categoria informada."));

                Produto produtoSaved = produtoRepository.save(Produto.builder()
                                .nome(produto.getNome())
                                .preco(produto.getPreco())
                                .categoriaProdutos(categoria)
                                .build());

                return ProdutoDTO.builder()
                                .id(produtoSaved.getId())
                                .nome(produtoSaved.getNome())
                                .preco(produtoSaved.getPreco())
                                .nomeCategoria(produtoSaved.getCategoriaProdutos().getNome())
                                .build();
        }

        @Override
        public ProdutoDTO editar(Long id, ProdutoSaveDTO produto) {
                Produto produtoEncontrado = produtoRepository.findById(id)
                                .orElseThrow(() -> new GeneralApiError(
                                                "Não foi possível encontrar o Produto informado."));

                Categoria categoria = categoriaRepository.findByNome(produto.getNomeCategoria())
                                .orElseThrow(() -> new GeneralApiError(
                                                "Não foi possível encontrar a Categoria informada."));

                produtoEncontrado.setNome(produto.getNome());
                produtoEncontrado.setPreco(produto.getPreco());
                produtoEncontrado.setCategoriaProdutos(categoria);

                produtoRepository.save(produtoEncontrado);

                return ProdutoDTO.builder()
                                .id(produtoEncontrado.getId())
                                .nome(produtoEncontrado.getNome())
                                .preco(produtoEncontrado.getPreco())
                                .nomeCategoria(produtoEncontrado.getCategoriaProdutos().getNome())
                                .build();
        }

        @Override
        public void excluir(Long id) {
                produtoRepository.deleteById(id);
        }
}
