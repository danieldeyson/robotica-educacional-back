package com.example.robotica.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.Model.Comentario;
import com.example.robotica.services.ConectionSQLite;



public class Comentarios {
Usuarios user= new Usuarios();


public boolean comentar(Comentario comentario){


  ConectionSQLite.connect();
        Connection connection;

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:BancoSQL.db");
            final Statement statement = connection.createStatement();
           
               statement.execute("INSERT INTO COMENTARIOS( IDUSER, CONTEUDO, DATA, PLANO) VALUES('"
                    + comentario.getIdUser()+ "','" + comentario.getConteudo() + "','" + comentario.getData()+"','"
                    + comentario.getPlanoAula() + "')");  
                    statement.close();

                    return true;
            
            

        } catch (SQLException e) {

            e.printStackTrace();
        }
        
            return false;
    }





    
    public List<Comentario> getComentarios(String plano) {
       
       
        List<Comentario> lista= new LinkedList<>();
        ConectionSQLite.connect();
      
      Connection connection;
      try {
          connection = DriverManager.getConnection("jdbc:sqlite:BancoSQL.db");

       PreparedStatement stmt = connection.prepareStatement("select * from COMENTARIOS where PLANO='"+plano+"'");
         ResultSet resultSet = stmt.executeQuery();         
       
         while (resultSet.next()) {
          Comentario comentario= new Comentario();
          
          comentario.setConteudo(resultSet.getString("CONTEUDO"));
          comentario.setData(resultSet.getString("DATA"));
          comentario.setId(resultSet.getInt("ID"));
          comentario.setIdUser(resultSet.getInt("IDUSER"));
          comentario.setPlanoAula(resultSet.getString("PLANO"));
          comentario.setUsuario(user.nomeId(resultSet.getInt("IDUSER")).getNome());
     

          lista.add(comentario);
         }

         

          resultSet.close();

          return lista;


      } catch (SQLException e) {
          
          e.printStackTrace();
      }        
       
        return null;

    }
    public List<Comentario> gettComentarios() {
       
       
        List<Comentario> lista= new LinkedList<>();
        ConectionSQLite.connect();
      
      Connection connection;
      try {
          connection = DriverManager.getConnection("jdbc:sqlite:BancoSQL.db");

       PreparedStatement stmt = connection.prepareStatement("select * from COMENTARIOS");
         ResultSet resultSet = stmt.executeQuery();         
       
         while (resultSet.next()) {
          Comentario comentario= new Comentario();
          
          comentario.setConteudo(resultSet.getString("CONTEUDO"));
          comentario.setData(resultSet.getString("DATA"));
          comentario.setId(resultSet.getInt("ID"));
          comentario.setIdUser(resultSet.getInt("IDUSER"));
          comentario.setPlanoAula(resultSet.getString("PLANO"));

          lista.add(comentario);
         }

         

          resultSet.close();

          return lista;


      } catch (SQLException e) {
          
          e.printStackTrace();
      }        
       
        return null;

    }



    public boolean deleteComentario(int id){



  ConectionSQLite.connect();
        Connection connection;

        try {

            connection = DriverManager.getConnection("jdbc:sqlite:BancoSQL.db");
            final Statement statement = connection.createStatement();
           
               statement.execute("DELETE FROM COMENTARIOS WHERE ID='"+id+"'");  
                    
                    return true;
            
            

        } catch (SQLException e) {

            e.printStackTrace();
        }
        
            return false;
    }



        
    }








