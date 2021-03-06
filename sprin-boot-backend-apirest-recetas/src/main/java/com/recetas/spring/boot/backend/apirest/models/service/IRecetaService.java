package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.recetas.spring.boot.backend.apirest.models.entity.Receta;

public interface IRecetaService {

	public List<Receta> findAll();

	public Page<Receta> findAll(Pageable pageable);

	public Receta findById(Long id);

	public List<Receta> findRecetaByIngredientesIn(Set<String> ingredientes);

	public List<Receta> findRecetaByNombreIn(Set<String> ingredientes);;

	public Receta save(Receta receta);

	public void delete(Long id);
}
