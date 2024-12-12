package com.tablero.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.tablero.app.model.Rol;
import com.tablero.app.model.Tablero;
import com.tablero.app.model.Usuario;
import com.tablero.app.repository.UsuarioRepository;
import com.tablero.app.service.TableroService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tableros")
public class TableroController {
	
	@Autowired
    private final TableroService tableroService;
	private final UsuarioRepository usuarioRepository;

    public TableroController(TableroService tableroService, UsuarioRepository usuarioRepository) {
        this.tableroService = tableroService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Tablero> getAllBoards() {
        return tableroService.getAllTableros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tablero> getTableroById(@PathVariable Long id) {
        return tableroService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Tablero> editarTablero(@PathVariable Long id, @RequestBody Tablero tableroUpdate){

    	 Optional<Tablero> existeTablero = tableroService.findById(id);
         if (existeTablero.isEmpty()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
         // Actualizamos los campos necesarios
         Tablero tablero = existeTablero.get();
         tablero.setTitulo(tableroUpdate.getTitulo());
         tablero.setTareas(tableroUpdate.getTareas());
         
         // Guarda los cambios
         Tablero tableroGuardado = tableroService.guardarTablero(tablero);

         // Devuelve la respuesta con el recurso actualizado
         return ResponseEntity.ok(tableroGuardado);
    }

    @PostMapping
    public Tablero createTablero(@RequestBody Tablero tablero) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario;
    	if(usuarioRepository.findByUsername(username).isEmpty()) {
    		throw new RuntimeException("El principal no es un objeto Usuario");
    	}else {
    		usuario = usuarioRepository.findByUsername(username).get();
    	}
    	tablero.setUsuario(usuario);
        
        return tableroService.guardarTablero(tablero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrarTablero(@PathVariable Long id) {
    	
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
        Usuario usuario;
        Tablero tablero = tableroService.findById(id).orElse(null);
	    if (tablero == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tablero no encontrado.");
	    }
    	if(usuarioRepository.findByUsername(username).isEmpty()) {
    		throw new RuntimeException("El principal no es un objeto Usuario");
    	}else {
    		usuario = usuarioRepository.findByUsername(username).get();
    	}
    	if (tablero.getUsuario().getId().equals(usuario.getId()) || usuario.getRol() == Rol.ADMIN) {
	        tableroService.borrarTablero(id);
	        return ResponseEntity.ok("Tablero eliminado con éxito.");
    	}

	    // Si no cumple los requisitos, denegar acceso
	    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar este tablero.");
	    
    }
}

