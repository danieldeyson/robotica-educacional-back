package com.example.robotica.repository;

import java.util.List;

import com.example.robotica.Model.Views;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VizualizacoesRepository extends JpaRepository<Views, Integer> {
@Query("SELECT v.aula FROM Views v GROUP BY v.aula ORDER BY COUNT(*) DESC")
public List<String> findAllCont();
@Query("SELECT COUNT(*) FROM Views v WHERE v.aula=?1")
public Integer contByAula(String aula);

}