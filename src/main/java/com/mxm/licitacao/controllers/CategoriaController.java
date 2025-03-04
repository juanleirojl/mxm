package com.mxm.licitacao.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mxm.licitacao.controllers.request.CategoriaRequest;
import com.mxm.licitacao.controllers.response.CategoriaResponse;
import com.mxm.licitacao.entity.Categoria;
import com.mxm.licitacao.mapper.CategoriaMapper;
import com.mxm.licitacao.services.CategoriaService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {
	private final CategoriaService categoriaService;
	private final CategoriaMapper categoriaMapper;

	@PostMapping
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody CategoriaRequest request) {
        Categoria categoria = categoriaMapper.toEntity(request);
        categoria = categoriaService.criarCategoria(categoria, request.getCategoriaPaiId());
        return ResponseEntity.ok(categoriaMapper.toResponse(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        List<CategoriaResponse> response = categorias.stream()
            .map(categoriaMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(@PathVariable Long id) {
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }
}