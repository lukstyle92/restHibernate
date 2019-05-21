package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;

import com.recetas.spring.boot.backend.apirest.models.entity.Categoria;

public interface ICategoriaService {
	public List<Categoria> findAll();

	public Categoria save(Categoria categoria);

	public void delete(Long id);

	public Categoria findById(Long id);
}
