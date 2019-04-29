package com.recetas.spring.boot.backend.apirest.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "receta")
public class Receta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	@Column(nullable = false)
	private String nombre;
	
	@NotEmpty
	@Column(nullable = false)
	private String tipo;

	@NotEmpty
	@Column(nullable = false)
	private String dificultad;

	@NotEmpty
	@Column(nullable = false)
	private int comensales;

	@NotEmpty
	@Column(nullable = false)
	private String preparacion;

	@NotEmpty
	@Column(nullable = false)
	private String ingredientes;
	
	private String path;

	/*
	 * @Column(name="create_at")
	 * 
	 * @Temporal(TemporalType.DATE) private Date createAt;
	 */

	/*
	 * @PrePersist public void prePersist() { createAt = new Date(); }
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public int getComensales() {
		return comensales;
	}

	public void setComensales(int comensales) {
		this.comensales = comensales;
	}

	public String getPreparacion() {
		return preparacion;
	}

	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}

	public String getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
