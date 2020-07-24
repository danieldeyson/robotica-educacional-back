package com.example.robotica.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Visualizacao implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
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
