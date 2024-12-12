package com.tablero.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tablero.app.model.Tarea;
@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

}
