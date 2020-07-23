package com.example.robotica.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Visualizacao {
    @Id
    private String Aula;
	private int iduser;

    public String getAula() {
        return Aula;
    }

    public void setAula(String aula) {
        Aula = aula;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }


}
