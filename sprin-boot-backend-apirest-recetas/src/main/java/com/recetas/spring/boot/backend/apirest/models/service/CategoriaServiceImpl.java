package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.recetas.spring.boot.backend.apirest.models.dao.ICategoriaDAO;
import com.recetas.spring.boot.backend.apirest.models.entity.Categoria;

public class CategoriaServiceImpl implements ICategoriaService {

	@Autowired
	private ICategoriaDAO categoriaDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Categoria> findAll() {
		return categoriaDAO.findAll();
	}

	@Override
	@Transactional
	public Categoria save(Categoria categoria) {
		return categoriaDAO.save(categoria);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		categoriaDAO.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Categoria findById(Long id) {
		return categoriaDAO.findById(id).orElse(null);
	}

}
