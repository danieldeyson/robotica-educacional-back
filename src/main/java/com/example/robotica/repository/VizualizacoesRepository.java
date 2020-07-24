package com.example.robotica.repository;

import java.util.List;

import com.example.robotica.Model.Visualizacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VizualizacoesRepository extends JpaRepository<Visualizacao, Integer> {
@Query("SELECT v.aula FROM Visualizacao v GROUP BY v.aula ORDER BY COUNT(*) DESC")
public List<String> findAllCont();
@Query("SELECT COUNT(*) FROM Visualizacao v WHERE v.aula=?1")
public Integer contByAula(String aula);
}