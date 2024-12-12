package com.tablero.app.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tablero.app.dtos.DtoAuthRespuesta;
import com.tablero.app.dtos.DtoLogin;
import com.tablero.app.dtos.DtoRegistro;
import com.tablero.app.model.Rol;
import com.tablero.app.model.Usuario;
import com.tablero.app.repository.UsuarioRepository;
import com.tablero.app.security.JwtGenerador;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private AuthenticationManager authenticationManager;
	private PasswordEncoder passwordEncoder;
	private UsuarioRepository usuarioRepository;
	private JwtGenerador jwtGenerador;

	public AuthController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
			UsuarioRepository usuarioRepository, JwtGenerador jwtGenerador) {
		super();
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.usuarioRepository = usuarioRepository;
		this.jwtGenerador = jwtGenerador;
	}
	
	// metodo para poder registrar usuarios con rol user
	@PostMapping("register")
	public ResponseEntity<String> registrar(@RequestBody DtoRegistro registro){
		
		if(usuarioRepository.existsByUsername(registro.getUsername()) ) {
			return new ResponseEntity<String>("El usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = new Usuario();
		usuario.setUsername(registro.getUsername());
		usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
		usuario.setRol(Rol.USER);
		usuarioRepository.save(usuario);
		
		return new ResponseEntity<String>("Registro de usuario existoso",HttpStatus.OK);
	}
	
	@PostMapping("registerAdmin")
	public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro registro){
		
		if(usuarioRepository.existsByUsername(registro.getUsername()) ) {
			return new ResponseEntity<String>("El usuario ya existe",HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = new Usuario();
		usuario.setUsername(registro.getUsername());
		usuario.setPassword(passwordEncoder.encode(registro.getPassword()));
		usuario.setRol(Rol.ADMIN);
		usuarioRepository.save(usuario);
		return new ResponseEntity<String>("Registro de usuario existoso",HttpStatus.OK);
	}
	
	//Metodo para poder loguear un usuario y obtener un token
	
	@PostMapping("login")
	public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin){
		
		 Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	                dtoLogin.getUsername(), dtoLogin.getPassword()));
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        String token = jwtGenerador.generarToken(authentication);
	        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);

	}
	
	@GetMapping("user")
	public ResponseEntity<List<Usuario>> mostrarUsuarios(){
		return new ResponseEntity<>(usuarioRepository.findAll(), HttpStatus.OK);
	}
	
}
