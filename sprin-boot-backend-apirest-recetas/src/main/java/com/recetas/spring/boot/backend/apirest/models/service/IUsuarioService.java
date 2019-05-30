package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.recetas.spring.boot.backend.apirest.models.entity.Role;
import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;
import com.recetas.spring.boot.backend.apirest.models.entity.UsuarioRole;

public interface IUsuarioService {
	public List<Usuario> findAll();

	public Usuario findByUsername(String username);
	
	public Role findByNombre(String role);

	public Usuario findById(Long id);

	public Usuario save(Usuario usuario);
	
	public UsuarioRole save(UsuarioRole usuarioRole);

	public void delete(Long id);
	
	public void deleteUsuarioRole(Long id);

	public Usuario findByEmail(String email);	
	
	public UsuarioRole findRoleById(Long id);

}
