package com.recetas.spring.boot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recetas.spring.boot.backend.apirest.models.entity.Categoria;

public interface ICategoriaDAO extends JpaRepository<Categoria, Long>{

}
