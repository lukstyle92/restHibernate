package com.recetas.spring.boot.backend.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.recetas.spring.boot.backend.apirest.models.entity.Role;
import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;
import com.recetas.spring.boot.backend.apirest.models.entity.UsuarioRole;

public interface IUsuarioDAO extends JpaRepository<Usuario, Long>{

	public Usuario findByUsername(String username);
	
	public Usuario findByEmail(String email);

	@Query("select u from Usuario u where u.username =?1")
	public Usuario findByUsername2(String username);
	
	public Role findByNombre(String role);
	
	public UsuarioRole save(UsuarioRole usuarioRole);
}
