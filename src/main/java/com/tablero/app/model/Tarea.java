package com.tablero.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tarea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Basic
	private String titulo;
	@Basic
	private String descripcion;
	@Enumerated(EnumType.STRING)
	private EstadoTarea estado;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "tablero_id", nullable = false)
	@JsonIgnore
	private Tablero tablero;
	
	
	public Tarea() {}
	
	public Tarea(String titulo, String descripcion, EstadoTarea estado, Tablero tablero) {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.estado = estado;
		this.tablero = tablero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public EstadoTarea getEstado() {
		return estado;
	}

	public void setEstado(EstadoTarea estado) {
		this.estado = estado;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}
	
	
	
	
	
	
}
