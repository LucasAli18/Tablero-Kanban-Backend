package com.tablero.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tablero.app.model.Tablero;
@Repository
public interface TableroRepository extends JpaRepository<Tablero, Long> {

}
