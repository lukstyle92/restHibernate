package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recetas.spring.boot.backend.apirest.models.entity.Receta;

public interface IRecetaService {

	public List<Receta> findAll();

	public Page<Receta> findAll(Pageable pageable);

	public Receta findById(Long id);

	public List<Receta> findByIngredientesContaining(String ingrediente);

	public List<Receta> findByNombreContaining(String nombreReceta);

	public Receta save(Receta receta);

	public void delete(Long id);
}
