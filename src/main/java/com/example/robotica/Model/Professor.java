package com.example.robotica.Model;

public class Professor {
   private int id;
    private String nome;
    private String escola;
    private String usuario;
    private String senha;
    private String cidade;
    private String sexo;
    private int idade;
    
    
    /**
     * @return the escola
     */
    public String getEscola() {
        return escola;
    }
     /**
      * @return the id
      */
     public int getId() {
         return id;
     }
     /**
      * @return the nome
      */
     public String getNome() {
         return nome;
     }
     /**
      * @return the senha
      */
     public String getSenha() {
         return senha;
     }
     /**
      * @return the usuario
      */
     public String getUsuario() {
         return usuario;
     }

     /**
      * @param escola the escola to set
      */
     public void setEscola(final String escola) {
         this.escola = escola;
     }

     /**
      * @param id the id to set
      */
     public void setId(final int id) {
         this.id = id;
     }

     /**
      * @param nome the nome to set
      */
     public void setNome(final String nome) {
         this.nome = nome;
     }

     /**
      * @param senha the senha to set
      */
     public void setSenha(final String senha) {
         this.senha = senha;
     }

     /**
      * @param usuario the usuario to set
      */
     public void setUsuario(final String usuario) {
         this.usuario = usuario;
     }

     public String getCidade() {
         return cidade;
     }

     public void setCidade(String cidade) {
         this.cidade = cidade;
     }

     public String getSexo() {
         return sexo;
     }

     public void setSexo(String sexo) {
         this.sexo = sexo;
     }

     public int getIdade() {
         return idade;
     }

     public void setIdade(int idade) {
         this.idade = idade;
     }
     
}