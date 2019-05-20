package com.recetas.spring.boot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	@Query("select u from Usuario u where u.username =?1")
	public Usuario findByUsername2(String username);
	
	public Usuario findByEmail(String email);

}