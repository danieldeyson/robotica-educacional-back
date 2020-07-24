package com.example.robotica.Model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comentario implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String planoAula;
	private String conteudo;
	private Integer idUser;
	private String data;
	private String usuario;
	private String categoria;
     

    

    /**
     * @return the conteudo
     */
    public String getConteudo() {
        return conteudo;
    }




	public Integer getId() {
		return id;
	}




	public void setId(Integer id) {
		this.id = id;
	}




	public String getPlanoAula() {
		return planoAula;
	}




	public void setPlanoAula(String planoAula) {
		this.planoAula = planoAula;
	}




	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}





    


}