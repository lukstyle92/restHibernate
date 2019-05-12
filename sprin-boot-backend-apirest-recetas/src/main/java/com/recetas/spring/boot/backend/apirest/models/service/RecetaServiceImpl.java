package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recetas.spring.boot.backend.apirest.models.dao.IRecetaDAO;
import com.recetas.spring.boot.backend.apirest.models.entity.Receta;

@Service
public class RecetaServiceImpl implements IRecetaService {

	@Autowired
	private IRecetaDAO recetaDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Receta> findAll() {
		return (List<Receta>) recetaDAO.findAll();
	}


	@Override
	public Page<Receta> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return recetaDAO.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Receta findById(Long id) {
		// TODO Auto-generated method stub
		return recetaDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Receta save(Receta receta) {
		// TODO Auto-generated method stub
		return recetaDAO.save(receta);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		recetaDAO.deleteById(id);
	}


	@Override
	public List<Receta> findByIngredientesContaining(String ingrediente) {		
		return recetaDAO.findByIngredientesContaining(ingrediente);
	}


	@Override
	public List<Receta> findByNombreContaining(String nombreReceta) {
		return recetaDAO.findByNombreContaining(nombreReceta);
	}

}
