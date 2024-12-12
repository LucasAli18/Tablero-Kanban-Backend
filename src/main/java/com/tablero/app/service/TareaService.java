package com.tablero.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tablero.app.model.Tarea;
//import com.tablero.repository.TableroRepository;
import com.tablero.app.repository.TareaRepository;
@Service
public class TareaService {
	
	private TareaRepository tareaRepository;
	//private TableroRepository tableroRepository;
	public TareaService(TareaRepository _tareaRepository) {
		this.tareaRepository=_tareaRepository;
	}
	public List<Tarea> getTareas(){
		return tareaRepository.findAll();
	}
	public Tarea guardarTarea(Tarea tarea) {
		return tareaRepository.save(tarea);
	}
	public Optional<Tarea> getTareasById(int id) {
		return tareaRepository.findById(id);
	}
	
	
	
}
