package com.tablero.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tablero.app.model.Tablero;
import com.tablero.app.model.Tarea;
import com.tablero.app.service.TableroService;
import com.tablero.app.service.TareaService;

@RestController
@RequestMapping("/tareas")
public class TareaController {

	public final TareaService tareaService;
	public final TableroService tableroService;
	
	public TareaController(TareaService _tareaService,TableroService _tableroService) {
		this.tareaService=_tareaService;
		this.tableroService = _tableroService;
	}
	@GetMapping
	public ResponseEntity<List<Tarea>> listarTareas(){
		return ResponseEntity.ok(tareaService.getTareas());
	}
	
	 @PostMapping("/{id_tablero}")
	    public ResponseEntity<Tarea> crearTarea(@PathVariable Long id_tablero, @RequestBody Tarea tarea) {
	    	//Primero verificamos si la tarea viene con titulo y descripcion
	        if (tarea.getTitulo() == null || tarea.getDescripcion() == null) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	        //Buscamos a ver si existe el tablero
	        Tablero tablero = tableroService.findById(id_tablero)
	                .orElseThrow(() -> new RuntimeException("Tablero no encontrado"));
	        //Sumar la tarea al tablero
	        tarea.setTablero(tablero);
	        //
	        Tarea nuevaTarea = tareaService.guardarTarea(tarea);
	        return new ResponseEntity<>(nuevaTarea, HttpStatus.CREATED);
	    }
	 
	 @PutMapping("/{id_tablero}/{id_tarea}")
	    public ResponseEntity<Tarea> editarTablero(@PathVariable int id, @RequestBody Tarea tareaUpdate){
	    	 Optional<Tarea> existeTarea = tareaService.getTareasById(id);
	         if (existeTarea.isEmpty()) {
	             return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	         }
	         // Actualizamos los campos necesarios
	         Tarea tarea = existeTarea.get();
	         tarea.setId(tareaUpdate.getId());
	         tarea.setTitulo(tareaUpdate.getTitulo());
	         tarea.setDescripcion(tareaUpdate.getDescripcion());
	         
	         // Guarda los cambios
	         Tarea tareaGuardado = tareaService.guardarTarea(tarea);

	         // Devuelve la respuesta con el recurso actualizado
	         return ResponseEntity.ok(tareaGuardado);
	    }
	@GetMapping("/{id_tablero}/{id_tarea}")
	public Optional<Tarea> getTareaById(@PathVariable long id_tablero, @PathVariable int id_tarea){
		return tareaService.getTareasById(id_tarea);
	}
	
	
}
