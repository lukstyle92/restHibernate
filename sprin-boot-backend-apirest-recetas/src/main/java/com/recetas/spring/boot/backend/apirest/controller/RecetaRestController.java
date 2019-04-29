package com.recetas.spring.boot.backend.apirest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recetas.spring.boot.backend.apirest.models.entity.Receta;
import com.recetas.spring.boot.backend.apirest.models.service.IRecetaService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/rest")
public class RecetaRestController {

	@Autowired
	private IRecetaService recetaService;

	// Método GET que devuelve listado de todas las recetas de la BBDD
	@GetMapping("/recetas")
	public List<Receta> index() {
		return recetaService.findAll();
	}
	
	@GetMapping("/recetas/page/{page}")
	public Page<Receta> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 6);
		return recetaService.findAll(pageable);
	}

	// Método GET que devuelve una receta buscada por ID
	@GetMapping("/recetas/{id}")
	// @ResponseStatus(HttpStatus.OK)
	// esta anotación es redundante, ya que si ha ido todo OK por defecto ya
	// devuelve OK
	public ResponseEntity<?> show(@PathVariable Long id) {
		Receta receta = null;
		Map<String, Object> response = new HashMap<>();
		try {
			receta = recetaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (receta == null) {
			response.put("mensaje", "La receta con id ID: ".concat(id.toString().concat(" no existe!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Receta>(receta, HttpStatus.OK);
	}

	// Método POST que inserta una receta nueva
	@PostMapping("/recetas")
	public ResponseEntity<?> create(@Valid @RequestBody Receta receta, BindingResult result) {
		Receta recetaNew = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			recetaNew = recetaService.save(receta);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La receta ha sido creado con éxito!");
		response.put("receta", recetaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	// Método PUT que actualiza una receta
	@PutMapping("/recetas/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Receta receta, BindingResult result, @PathVariable Long id) {
		Receta recetaActual = recetaService.findById(id);
		Receta recetaUpdated = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (recetaActual == null) {
			response.put("mensaje", "Error: no ha sido posible editar, la receta"
					.concat(id.toString().concat(" no existe en la bbdd!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		try {
			recetaActual.setNombre(receta.getNombre());
			recetaActual.setTipo(receta.getTipo());
			recetaActual.setDificultad(receta.getDificultad());
			recetaActual.setComensales(receta.getComensales());
			recetaActual.setPreparacion(receta.getPreparacion());
			recetaActual.setIngredientes(receta.getIngredientes());
			recetaActual.setPath(receta.getPath());
			recetaUpdated = recetaService.save(recetaActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la receta en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La receta ha sido actualizada con éxito!");
		response.put("receta", recetaUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/recetas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			recetaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la receta en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La receta ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
