package com.example.robotica.repository;

import java.util.List;

import com.example.robotica.Model.Visualizacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VizualizacoesRepository extends JpaRepository<Visualizacao, Integer> {
@Query("SELECT Aula FROM Visualizacao GROUP BY aula ORDER BY COUNT(*) DESC")
public List<String> findAllCont();
}