package com.recetas.spring.boot.backend.apirest.models.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recetas.spring.boot.backend.apirest.models.entity.Receta;

public interface IRecetaDAO extends JpaRepository<Receta, Long>{
	public List<Receta> findRecetaByIngredientesIn(String ingredientes);
	public List<Receta> findRecetaByNombreIn(String nombreReceta);
}
