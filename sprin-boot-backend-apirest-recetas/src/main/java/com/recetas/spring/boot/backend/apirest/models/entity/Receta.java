package com.recetas.spring.boot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "recetas")
public class Receta implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotEmpty
	@Size(min = 4, max = 100)
	@Column(nullable = false)
	private String nombre;

	@NotEmpty
	@Column(nullable = false)
	private String tipo;

	@NotEmpty
	@Column(nullable = false)
	private String dificultad;

	@DecimalMin(value = "1")
	@Column(nullable = false)
	private int comensales;

	@NotEmpty
	@Size(max = 99999)
	@Column(nullable = false)
	private String preparacion;

	@NotEmpty
	@Size(max = 99999)
	@Column(nullable = false)
	private String ingredientes;

	private String path;
	
	private String video;

	@Column(name = "create_at")
	private long createAt;

	@PrePersist
	public void prePersist() {
		createAt = new Date().getTime();
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "recetas_categorias", joinColumns = @JoinColumn(name = "receta_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "receta_id", "categoria_id" }) })
	private List<Categoria> categorias;

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

	public long getCreateAt() {
		return createAt;
	}

	public void setCreateAt(long createAt) {
		this.createAt = createAt;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	

	public String getVideo() {
		return video;
	}

	public void setVideo(String urlVideo) {
		this.video = urlVideo;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
