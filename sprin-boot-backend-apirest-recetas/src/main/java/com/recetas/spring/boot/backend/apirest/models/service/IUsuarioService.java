package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;

import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {
	public List<Usuario> findAll();
	public Usuario findByUsername(String username);
	
	public Usuario findById(Long id);

	public Usuario save(Usuario usuario);
	
	public Usuario findByEmail(String email);
}
