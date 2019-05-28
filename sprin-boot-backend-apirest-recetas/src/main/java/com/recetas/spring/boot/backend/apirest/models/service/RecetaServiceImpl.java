package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
	
	@PersistenceContext
    private EntityManager entityManager;

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
	public List<Receta> findRecetaByIngredientesIn(Set<String> ingredientes) {		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Receta> query = cb.createQuery(Receta.class);
        Root<Receta> receta = query.from(Receta.class);
 
        Path<String> ingredientePath = receta.get("ingredientes");
 
        List<Predicate> predicates = new ArrayList<>();
        for (String ingrediente : ingredientes) {
            predicates.add(cb.like(ingredientePath, ingrediente));
        }
        query.select(receta)
            .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
 
        return entityManager.createQuery(query)
            .getResultList();    
	}


	@Override
	public List<Receta> findRecetaByNombreIn(Set<String> ingredientes) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Receta> query = cb.createQuery(Receta.class);
        Root<Receta> receta = query.from(Receta.class);
 
        Path<String> nombrePath = receta.get("nombre");
 
        List<Predicate> predicates = new ArrayList<>();
        for (String ingrediente : ingredientes) {
            predicates.add(cb.like(nombrePath, ingrediente));
        }
        query.select(receta)
            .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
 
        return entityManager.createQuery(query)
            .getResultList(); 
	}

}
