package com.recetas.spring.boot.backend.apirest.models.service;

import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;

public interface IUsuarioService {
	public Usuario findByUsername(String username);

	public Usuario save(Usuario usuario);
	
	public Usuario findByEmail(String email);
}
