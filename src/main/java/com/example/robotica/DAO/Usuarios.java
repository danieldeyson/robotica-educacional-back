package com.example.robotica.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.Model.Professor;
import com.example.robotica.services.ConectionPostgreSQL;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.boot.configurationprocessor.json.JSONException;

public class Usuarios {

    int resultado = 0;

    public int cadastrar(Professor usuario) throws SQLException {

        ConectionPostgreSQL.connect();
        Connection connection;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
            final Statement statement = connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * from USUARIO WHERE EMAIL='" + usuario.getUsuario() + "'");

            if (!result.next()) {
                statement.execute("INSERT INTO USUARIO( NOME, EMAIL, SENHA, CIDADE, SEXO, CATEGORIA,IDADE) VALUES('"
                        + usuario.getNome() + "','" + usuario.getUsuario() + "','" + usuario.getSenha() + "','"
                        + usuario.getCidade() + "','" + usuario.getSexo() + "','" + usuario.getEscola() + "','"
                        + usuario.getIdade() + "')");

                ResultSet resulta = statement
                        .executeQuery("SELECT ID from USUARIO WHERE EMAIL='" + usuario.getUsuario() + "'");
                resultado = resulta.getInt("ID");

                result.close();
                resulta.close();
                return resultado;
            }
            result.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }

    public Professor loguin(String loguin, String senha) {

        ConectionPostgreSQL.connect();
        Professor user = new Professor();
        final Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");

            PreparedStatement stmt = connection
                    .prepareStatement("select * from USUARIO where EMAIL='" + loguin + "'and SENHA='" + senha + "'");
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                user.setCidade(resultSet.getString("CIDADE"));
                user.setSexo(resultSet.getString("SEXO"));
                user.setEscola(resultSet.getString("CATEGORIA"));
                user.setId(resultSet.getInt("ID"));
                user.setSenha(resultSet.getString("SENHA"));
                user.setIdade(resultSet.getInt("IDADE"));
                user.setNome(resultSet.getString("NOME"));
                user.setUsuario(resultSet.getString("EMAIL"));
            }

            resultSet.close();
            return user;

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    public List<Professor> listarUsers() throws JSONException {

        List<Professor> lista = new LinkedList<>();
        ConectionPostgreSQL.connect();

        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");

            PreparedStatement stmt = connection.prepareStatement("select * from USUARIO");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Professor user = new Professor();

                user.setCidade(resultSet.getString("CIDADE"));
                user.setSexo(resultSet.getString("SEXO"));
                user.setEscola(resultSet.getString("CATEGORIA"));
                user.setId(resultSet.getInt("ID"));
                user.setSenha(resultSet.getString("SENHA"));
                user.setIdade(resultSet.getInt("IDADE"));
                user.setNome(resultSet.getString("NOME"));
                user.setUsuario(resultSet.getString("EMAIL"));

                lista.add(user);

            }

            resultSet.close();

            return lista;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;

    }

    public Professor alterarUsuario(Professor professor) {

        try {
            String consulta="UPDATE USUARIO SET ID='"+professor.getId()+"'";

                        if(professor.getNome()!=null){
                            consulta+=",NOME='"+professor.getNome()+"'";
                    }
                    if(professor.getCidade()!=null){
                        consulta+=", CIDADE='"+professor.getCidade()+"'";
                }
                if(professor.getUsuario()!=null){
                    consulta+=",EMAIL='"+professor.getUsuario()+"'";
            }
            if(professor.getIdade()!=0){
                consulta+=",IDADE='"+professor.getIdade()+"'";
            }
            if(professor.getSexo()!=null){
                consulta+=",SEXO='"+professor.getSexo()+"'";
            }
            if(professor.getSenha()!=null){
                consulta+=",SENHA='"+professor.getSenha()+"'";
            }
            if(professor.getEscola()!=null){
                consulta+=",CATEGORIA'"+professor.getEscola()+"'";
            }
            consulta+= "where ID='" + professor.getId() + "'";

            Connection connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
            Statement stmt = connection.createStatement();
            stmt.execute(consulta);

            return professor;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;

    }

    
     public String esqueciSenha(int id,String email) throws EmailException {
      
      
      try {
     
      String senha=gerarSenhaAleatoria();
      
      Connection connection =
      DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require"); Statement
      stmt=connection.createStatement(); stmt.execute("update USUARIO set SENHA='"
      + senha + "'where ID='" + id + "'"); 
      if( enviarEmail(email, senha)){
      return "Enviado com Sucesso";
    }
      
      } catch (SQLException e) {
      
     e.printStackTrace(); } return "error";
     }
     

    private static String gerarSenhaAleatoria() {
        int qtdeMaximaCaracteres = 8;
        String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
                "X", "Y", "Z" };

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
    }
    

    public boolean enviarEmail(String destino, String senha) throws EmailException {


    SimpleEmail email = new SimpleEmail();
    email.setHostName("smtp.googlemail.com");
email.setSmtpPort(465);
email.setAuthenticator(new DefaultAuthenticator("",""));
email.setSSLOnConnect(true);
email.addTo(destino); //destinatário
email.setFrom("jlvieira248@gmail.com"); // remetente
email.setSubject("Nova Senha"); // assunto do e-mail
email.setMsg("Sua nova senha para o sistema Robotica computacional é: "+senha+""); //conteudo do e-mail
email.send(); //envia o e-mail

return true;

}





public String categId(int id) throws SQLException {
    ConectionPostgreSQL.connect();
    Connection connection;

    
        connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
        final Statement statement = connection.createStatement();
        ResultSet result;
		try {
			result = statement
                    .executeQuery("SELECT CATEGORIA from USUARIO WHERE ID='" + id + "'");
                   
                    result.next();
                    String Resultado=result.getString("CATEGORIA"); 
                   
                    result.close();
                    return Resultado;  
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
          
                
        return null;



    }
    public Professor nomeId(int id) throws SQLException {
        ConectionPostgreSQL.connect();
        Professor prof = new Professor();
        Connection connection;
    
        
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
            final Statement statement = connection.createStatement();
            ResultSet result;
            try {
                result = statement
                        .executeQuery("SELECT * from USUARIO WHERE ID='" + id + "'");
                       
                        result.next();
                        prof.setNome(result.getString("NOME"));
                        prof.setCidade(result.getString("CIDADE")); 
                        prof.setIdade(result.getInt("IDADE"));
                        prof.setSenha(result.getString("SENHA"));
                        prof.setUsuario(result.getString("EMAIL"));
                        prof.setSexo(result.getString("SEXO"));
                        prof.setEscola(result.getString("CATEGORIA"));
                         result.close();
                        
                        return prof;  
                        
            } catch (SQLException e) {
                
                e.printStackTrace();
            }
              
        
            return prof;
    
    
    
        }

}









    
