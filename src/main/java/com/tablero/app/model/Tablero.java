package com.tablero.app.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class Tablero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Basic
	private String titulo;
	@OneToMany(mappedBy = "tablero", cascade = CascadeType.ALL)
	private List<Tarea> tareas;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "usuario_id", nullable = false)
	@JsonIgnore
	private Usuario usuario;
	
	public Tablero() 
	{
		this.tareas = new ArrayList<Tarea>(); //PREGUNTAR ESTO
	}
	public Tablero(String titulo) { 
		super();
		this.titulo = titulo;
		this.tareas = new ArrayList<Tarea>();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<Tarea> getTareas() {
		return tareas;
	}
	public void setTareas(List<Tarea> tareas) {
		this.tareas = tareas;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
