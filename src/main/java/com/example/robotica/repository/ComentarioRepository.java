package com.example.robotica.repository;

import java.util.List;

import com.example.robotica.Model.Comentario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    @Query("SELECT planoAula FROM Comentario WHERE CATEGORIA=?1 GROUP BY planoAula ORDER BY COUNT(*) DESC")
public List<String> findAllCont(String categoria);
}