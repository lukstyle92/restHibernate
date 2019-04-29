package com.recetas.spring.boot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recetas.spring.boot.backend.apirest.models.entity.Receta;

public interface IRecetaDAO extends JpaRepository<Receta, Long>{
	
}
