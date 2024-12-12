package com.tablero.app.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Basic
	private String username;
	@Basic
	private String password;
	@Enumerated(EnumType.STRING)
	private Rol rol;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "usuario")
	List<Tablero> tableros;
	
	public Usuario() {}
	
	public Usuario(String username, String password, Rol rol) {
		super();
		this.username = username;
		this.password = password;
		this.rol = rol;
		this.tableros = new ArrayList<Tablero>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String nombre) {
		this.username = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Tablero> getTableros() {
		return tableros;
	}

	public void setTableros(List<Tablero> tableros) {
		this.tableros = tableros;
	}
	
}
