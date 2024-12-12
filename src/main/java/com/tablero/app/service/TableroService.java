package com.tablero.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.tablero.app.model.Tablero;
import com.tablero.app.repository.TableroRepository;
import com.tablero.app.repository.UsuarioRepository;

@Service
public class TableroService {
	
	private TableroRepository tableroRepository;
	private UsuarioRepository usuarioRepository;
	
	public TableroService(TableroRepository _tableroRepository, UsuarioRepository usuarioRepository) {
		this.tableroRepository = _tableroRepository;
		this.usuarioRepository = usuarioRepository;
	}
	
	public Tablero guardarTablero(Tablero tablero) {
		return tableroRepository.save(tablero);
	}
	
	public List<Tablero> getAllTableros() {
		return tableroRepository.findAll();
	}
	
	public Optional<Tablero> getTableroById(long id) {
		return tableroRepository.findById(id);
	}

	public Optional<Tablero> findById(Long id_tablero) {
		return tableroRepository.findById(id_tablero);
	}

	public void borrarTablero(Long id) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombre = auth.getName();
		usuarioRepository.findByUsername(nombre).get();
		if(tableroRepository.findById(id) != null) {
			System.out.println(nombre+" "+"auth.getAuthor"+auth.getAuthorities()+"auth.getCred"+auth.getCredentials());
		}
				
		tableroRepository.deleteById(id);
	}
	
	
	
	
	
	
}
