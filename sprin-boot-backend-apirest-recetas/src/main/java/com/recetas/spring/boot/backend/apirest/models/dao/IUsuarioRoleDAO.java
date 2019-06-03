package com.recetas.spring.boot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recetas.spring.boot.backend.apirest.models.entity.UsuarioRole;

public interface IUsuarioRoleDAO extends JpaRepository<UsuarioRole, Long>{
	@Query("select u from UsuarioRole u where u.usuario_id =?1")
	public UsuarioRole findById(long id);
}
