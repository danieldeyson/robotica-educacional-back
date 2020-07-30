package com.example.robotica.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Views {
      /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
    private String aula;
	private Integer iduser;

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
      this.aula = aula;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

   
}