package com.mxm.licitacao.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mxm.licitacao.entity.Categoria;
import com.mxm.licitacao.repositories.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public Categoria criarCategoria(Categoria categoria, Long categoriaPaiId) {
        if (categoriaPaiId != null) {
            Categoria categoriaPai = categoriaRepository.findById(categoriaPaiId)
                .orElseThrow(() -> new RuntimeException("Categoria Pai não encontrada"));
            categoria.setCategoriaPai(categoriaPai);
        }
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public void excluirCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
