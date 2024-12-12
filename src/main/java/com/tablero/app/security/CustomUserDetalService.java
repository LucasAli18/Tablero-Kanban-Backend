package com.tablero.app.security;

import java.util.Collection;
import java.util.Collections;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.tablero.app.model.Rol;
import com.tablero.app.model.Usuario;
import com.tablero.app.repository.UsuarioRepository;

//Encontrar un usuario mediante un username y al encontrarlo traer todos los datos
@Service
public class CustomUserDetalService implements UserDetailsService {
	
	private UsuarioRepository usuarioRepository;

	//inyeccion de dependencia a traves del constructor
	public CustomUserDetalService(UsuarioRepository _usuarioRepository) {
		this.usuarioRepository = _usuarioRepository;
	}
	
	//mapear nuestra lista de roles o de autoridad
	public Collection<GrantedAuthority> mapToAuthorities(Rol roles){

		//return 	Stream.of(Rol.values())
        //       .map(rol-> new SimpleGrantedAuthority(rol.name()))
		//       .collect(Collectors.toList());
		return Collections.singletonList(new SimpleGrantedAuthority(roles.name()));
	}
	
	//Metodo para traer todos los datos de un usuario a traves de su username
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
		return new User(usuario.getUsername(),usuario.getPassword(), mapToAuthorities(usuario.getRol()));
	}
}
