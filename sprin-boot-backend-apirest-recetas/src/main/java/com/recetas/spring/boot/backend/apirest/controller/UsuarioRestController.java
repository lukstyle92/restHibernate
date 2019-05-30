package com.recetas.spring.boot.backend.apirest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.recetas.spring.boot.backend.apirest.models.entity.Receta;
import com.recetas.spring.boot.backend.apirest.models.entity.Role;
import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;
import com.recetas.spring.boot.backend.apirest.models.entity.UsuarioRole;
import com.recetas.spring.boot.backend.apirest.models.service.IUsuarioService;
import com.recetas.spring.boot.backend.apirest.models.service.UsuarioServiceImpl;
import com.recetas.spring.boot.backend.apirest.models.utility.Mail;

import ch.qos.logback.classic.Logger;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/rest")
public class UsuarioRestController {
	@Autowired
	private IUsuarioService usuarioService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UsuarioServiceImpl usuarioServiceImp;
	private final Logger log = (Logger) LoggerFactory.getLogger(UsuarioRestController.class);

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/usuario")
	public List<Usuario> index() {
		return usuarioService.findAll();
	}

	@PostMapping("/usuario")
	public ResponseEntity<?> create(@Valid @RequestBody Usuario usuario, BindingResult result) {
		Usuario usuarioNew = null;
		UsuarioRole usuarioRole = new UsuarioRole();
		UsuarioRole usuarioRoleNew = null;
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			String passwordBcrypt = passwordEncoder.encode(usuario.getPassword());
			usuario.setPassword(passwordBcrypt);
			usuarioNew = usuarioService.save(usuario);
			usuarioRole.setRole_id((long)1);
			usuarioRole.setUsuario_id(usuarioNew.getId());
			usuarioRoleNew = usuarioService.save(usuarioRole);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			log.info(e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El usuario ha sido creado con éxito!");
		response.put("usuario", usuarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	//@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@PostMapping("/cambiar/{email}")
	public ResponseEntity<?> updatePassword(@Valid @RequestBody String password, @PathVariable String email) {
		log.info(email);
		Usuario usuarioActual = usuarioService.findByEmail(email);
		Usuario usuarioUpdated = null;
		Map<String, Object> response = new HashMap<>();
		try {
			String passwordBcrypt = passwordEncoder.encode(password);
			usuarioActual.setPassword(passwordBcrypt);
			usuarioUpdated = usuarioService.save(usuarioActual);
		} catch (DataAccessException e) {
			log.info("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			;
			response.put("mensaje", "Error al actualizar la receta en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "La contraseña ha sido actualizada con éxito!");
		response.put("usuario", usuarioUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/usuario/{email}")
	public ResponseEntity<?> delete(@PathVariable String email) {
		Map<String, Object> response = new HashMap<>();
		try {
			Usuario usuario = usuarioService.findByEmail(email);
			UsuarioRole role = usuarioService.findRoleById(usuario.getId());
			if (usuario != null && role != null) {
				usuarioService.deleteUsuarioRole(role.getId());
				usuarioService.delete(usuario.getId());
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar la receta en bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Usuario borrado con éxito.");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/comprobar")
	public boolean comprobarUsuarioDisponible(@RequestParam String username) {
		log.info(username);
		Usuario u = null;
		u = usuarioService.findByUsername(username);
		return u == null;
	}

	@GetMapping("/comprobarr")
	public boolean comprobarMailDisponible(@RequestParam String email) {
		log.info(email);
		Usuario u = null;
		u = usuarioService.findByEmail(email);
		return u == null;
	}

	@ReadOnlyProperty
	@PostMapping("/recuperar")
	public ResponseEntity<?> recuperar(@Valid @RequestBody Mail mail) {
		Map<String, Object> response = new HashMap<>();
		Usuario u = null;
		String email = "";
		String password = "";
		try {
			log.info(email);
			u = usuarioService.findByEmail(mail.getMail());
			if (u == null) {
				response.put("mensaje", "Error al intentar regenerar contraseña, mail especificado no existe.");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}
			log.info(generarRandomPassword(8));
			password = generarRandomPassword(10);
			u.setPassword(passwordEncoder.encode(password));
			usuarioServiceImp.save(u);
			usuarioServiceImp.sendNotification(u, password);
			response.put("mensaje", "Se ha enviado un correo con su nueva contraseña.");
			response.put("password", password);
		} catch (MailException e) {
			response.put("mensaje", "Error al intentar regenerar contraseña.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	public String generarRandomPassword(int lenght) {
		String password = "";
		for (int i = 0; i < lenght - 2; i++) {
			password = password + randomCharacter("abcdefghijklmnopqrstwxyzABCDEFGHIJKLMNOPQRSTUWXYZ");
		}
		String randomDigit = randomCharacter("0123456789");
		password = insertAtRandom(password, randomDigit);
		return password;
	}

	public String randomCharacter(String characters) {
		int n = characters.length();
		int r = (int) (n * Math.random());
		return characters.substring(r, r + 1);
	}

	public String insertAtRandom(String str, String toInsert) {
		int n = str.length();
		int r = (int) ((n + 1) * Math.random());
		return str.substring(0, r) + toInsert + str.substring(r);
	}

}
