package com.recetas.spring.boot.backend.apirest.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.recetas.spring.boot.backend.apirest.models.entity.Categoria;
import com.recetas.spring.boot.backend.apirest.models.entity.Receta;
import com.recetas.spring.boot.backend.apirest.models.service.IRecetaService;
import com.recetas.spring.boot.backend.apirest.models.service.IUploadFileService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/rest")
public class RecetaRestController {

	@Autowired
	private IRecetaService recetaService;

	@Autowired
	private IUploadFileService uploadService;

	// Método GET que devuelve listado de todas las recetas de la BBDD
	@GetMapping("/recetas")
	public List<Receta> index() {
		return recetaService.findAll();
	}

	@GetMapping("/recetas/page/{page}")
	public Page<Receta> index(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 18);
		return recetaService.findAll(pageable);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
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

	@Secured({ "ROLE_ADMIN" })
	// Método POST que inserta una receta nueva
	@PostMapping("/recetas")
	public ResponseEntity<?> create(@Valid @RequestBody Receta receta, BindingResult result) {
		Receta recetaNew = null;
		Map<String, Object> response = new HashMap<>();
		receta.setIngredientes(receta.getIngredientes().replace("null", ""));
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

	@Secured({ "ROLE_ADMIN" })
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

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/recetas/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Receta receta = recetaService.findById(id);
			if (receta != null) {
				String nombreFotoAnterior = receta.getPath();
				uploadService.eliminar(nombreFotoAnterior);
				recetaService.delete(id);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la receta en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La receta ha sido eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/recetas/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		Receta receta = recetaService.findById(id);
		if (receta != null) {
			if (!archivo.isEmpty()) {
				String nombreArchivo = null;
				try {
					nombreArchivo = uploadService.copiar(archivo);
				} catch (IOException e) {
					e.printStackTrace();
					response.put("mensaje", "Error al subir la imagen de la receta");
					response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				String nombreFotoAnterior = receta.getPath();
				uploadService.eliminar(nombreFotoAnterior);
				receta.setPath(nombreArchivo);
				recetaService.save(receta);
				response.put("receta", receta);
				response.put("mensaje", "Has subido correctamente la imagen: " + nombreArchivo);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			}
		}
		response.put("mensaje", "La receta no existe");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto) {
		Resource recurso = null;
		try {
			recurso = uploadService.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
		return new ResponseEntity<Resource>(recurso, cabecera, HttpStatus.OK);
	}

	@GetMapping("/recetas/page/{page}/buscar")
	public ResponseEntity<?> buscarPorIngredientes(@PathVariable Integer page, @RequestParam String params) {
		String[] buscar;
		Set<String> ingredientes = new HashSet<>();
		if (params.contains("-")) {
			buscar = params.split("-");
			for (int i = 0; i < buscar.length; i++) {
				ingredientes.add("%" + buscar[i] + "%");
			}
		} else {
			ingredientes.add("%" + params + "%");
		}
		// filling the set with any number of items
		recetaService.findRecetaByIngredientes(ingredientes);
		Map<String, Object> response = new HashMap<>();
		Pageable pageable = PageRequest.of(page, 3);
		List<Receta> listaRecetas = null;
		listaRecetas = recetaService.findRecetaByIngredientes(ingredientes);
		int size = 18;
		int start = (int) new PageRequest(page, size).getOffset();
		int end = (start + new PageRequest(page, size).getPageSize()) > listaRecetas.size() ? listaRecetas.size()
				: (start + new PageRequest(page, size).getPageSize());
		PageImpl<Receta> pageReceta = new PageImpl<Receta>(listaRecetas.subList(start, end),
				new PageRequest(page, size), listaRecetas.size());

		if (!listaRecetas.isEmpty()) {
			response.put("mensaje", "Se han encontrado recetas con el " + ingredientes.toString() + " buscados!");
			response.put("receta", pageReceta);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}
		response.put("mensaje", "No se han encontrado recetas con el termino facilitado!");
		response.put("termino", ingredientes.toString());
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

}
