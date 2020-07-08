package com.example.robotica.Model;

public class Comentario {

    private int id;
    private String planoAula;
	private String conteudo;
	private int idUser;
	private String data;
	private String usuario;
     

    

    /**
     * @return the conteudo
     */
    public String getConteudo() {
        return conteudo;
    }




	public int getId() {
		return id;
	}




	public void setId(int id) {
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

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
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





    


}