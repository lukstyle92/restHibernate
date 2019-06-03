package com.recetas.spring.boot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sun.istack.NotNull;
@Entity
@Table(name = "usuarios_roles")
public class UsuarioRole implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Long usuario_id;
	@NotNull
	private Long role_id;
	
	public UsuarioRole() {
	}
	
	public UsuarioRole(Long id, Long usuario_id, Long role_id) {
		super();
		this.id = id;
		this.usuario_id = usuario_id;
		this.role_id = role_id;
	}
	public Long getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Long usuario_id) {
		this.usuario_id = usuario_id;
	}
	public Long getRole_id() {
		return role_id;
	}
	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
