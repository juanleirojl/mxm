package com.mxm.licitacao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mxm.licitacao.entity.Categoria;
import com.mxm.licitacao.repositories.CategoriaRepository;
import com.mxm.licitacao.repositories.TransacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final TransacaoRepository transacaoRepository;

    /** 📌 Criar uma nova categoria */
    public Categoria criarCategoria(Categoria categoria, Long categoriaPaiId) {
        if (categoriaPaiId != null) {
            Categoria categoriaPai = categoriaRepository.findById(categoriaPaiId)
                .orElseThrow(() -> new RuntimeException("Categoria Pai não encontrada"));
            categoria.setCategoriaPai(categoriaPai);
        }
        return categoriaRepository.save(categoria);
    }

    /** 📌 Listar todas as categorias */
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    /** 📌 Buscar categoria por ID */
    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    /** 📌 Editar uma categoria existente */
    public Categoria editarCategoria(Long id, Categoria novaCategoria) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoria.setNome(novaCategoria.getNome());
        categoria.setTipoTransacao(novaCategoria.getTipoTransacao());

        if (novaCategoria.getCategoriaPai() != null) {
            Categoria categoriaPai = categoriaRepository.findById(novaCategoria.getCategoriaPai().getId())
                .orElseThrow(() -> new RuntimeException("Categoria Pai não encontrada"));
            categoria.setCategoriaPai(categoriaPai);
        } else {
            categoria.setCategoriaPai(null);
        }

        return categoriaRepository.save(categoria);
    }

    /** 📌 Excluir uma categoria */
    public void excluirCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        // 🔹 Verifica se a categoria já foi usada em uma transação
        if (transacaoRepository.existsByCategoriaId(id)) {
            throw new RuntimeException("Não é possível excluir uma categoria que já está associada a uma transação.");
        }

        // 🔹 Verifica se a categoria tem subcategorias antes de excluir
        List<Categoria> subcategorias = categoriaRepository.findByCategoriaPai(categoria);
        if (!subcategorias.isEmpty()) {
            throw new RuntimeException("Não é possível excluir uma categoria que possui subcategorias.");
        }

        categoriaRepository.delete(categoria);
    }

}
