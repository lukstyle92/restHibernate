package com.recetas.spring.boot.backend.apirest.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recetas.spring.boot.backend.apirest.models.dao.IUsuarioDAO;
import com.recetas.spring.boot.backend.apirest.models.entity.Receta;
import com.recetas.spring.boot.backend.apirest.models.entity.Usuario;

@Service
public class UsuarioServiceImpl implements IUsuarioService, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private IUsuarioDAO usuarioDAO;

	private JavaMailSender javaMailSender;

	@Autowired
	public UsuarioServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendNotification(Usuario usuario, String password) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(usuario.getEmail());
		mail.setFrom("soporte.tus.recetas@gmail.com");
		mail.setSubject("Solicitud de recuperación de contraseña");
		mail.setText("Tu contraseña ha sido actualizada." + "\nNueva password: " + password);
		javaMailSender.send(mail);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioDAO.findByUsername(username);
		if (usuario == null) {
			logger.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException(
					"Error en el login: no existe el usuario '" + username + "' en el sistema!");
		}
		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority())).collect(Collectors.toList());
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioDAO.findByUsername(username);
	}

	@Override
	@Transactional
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioDAO.save(usuario);
	}

	@Override
	public Usuario findByEmail(String email) {
		// TODO Auto-generated method stub
		return usuarioDAO.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		// TODO Auto-generated method stub
		return usuarioDAO.findById(id).orElse(null);
	}

	@Override
	public List<Usuario> findAll() {
		return usuarioDAO.findAll();
	}
}
