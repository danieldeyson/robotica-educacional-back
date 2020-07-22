package com.example.robotica.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.example.robotica.Model.Recomendacao;
import com.example.robotica.services.ConectionPostgreSQL;

import org.springframework.boot.configurationprocessor.json.JSONException;

public class RecomendacoesDao {


    public void cadastrar(Recomendacao reco) throws SQLException {
        

        ConectionPostgreSQL.connect();
        Connection connection;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
            final Statement statement = connection.createStatement();
            
            statement.execute("INSERT INTO RECOMENDACOES(IDUSER, AULA, CATEGORIA) VALUES('"
                        + reco.getIdUser()+ "','" + reco.getAula() + "','" + reco.getCategoria() + "')");
               

        } catch (SQLException e) {

            e.printStackTrace();
        }


    }



   


    public List<Recomendacao> listarrecs() throws JSONException {

        List<Recomendacao> lista = new LinkedList<>();
        ConectionPostgreSQL.connect();

        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");

            PreparedStatement stmt = connection.prepareStatement("select * from RECOMENDACOES");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Recomendacao rec = new Recomendacao();

                
                rec.setAula(resultSet.getString("AULA"));
                rec.setIdUser(resultSet.getInt("IDUSER"));
                rec.setCategoria(resultSet.getString("CATEGORIA"));
                rec.setId(resultSet.getInt("ID"));
               

                lista.add(rec);

            }

            resultSet.close();

            return lista;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;

    }
    



    public List<Recomendacao> recomendar(String cat) throws JSONException {

        List<Recomendacao> lista = new LinkedList<>();
        ConectionPostgreSQL.connect();

        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");

            PreparedStatement stmt = connection.prepareStatement("select * from RECOMENDACOES WHERE CATEGORIA='"+cat+"'ORDER BY ID DESC LIMIT 10");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Recomendacao rec = new Recomendacao();

                
                rec.setAula(resultSet.getString("AULA"));
                rec.setIdUser(resultSet.getInt("IDUSER"));
                rec.setCategoria(resultSet.getString("CATEGORIA"));
                rec.setId(resultSet.getInt("ID"));
               

                lista.add(rec);

            }

            resultSet.close();

            return lista;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return null;

    }



    public int visualizacoes(String aula) throws JSONException {

        int resultado=0;
      
        ConectionPostgreSQL.connect();

        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");

            PreparedStatement stmt = connection.prepareStatement("select * from VISUALIZACOES where AULA='"+aula+"'");
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
               resultado++;

            }

            resultSet.close();

            return resultado;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;

    }


 public void visualizar(String aula,int idUser) throws SQLException {
        

        ConectionPostgreSQL.connect();
        Connection connection;

        try {

            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
             Statement statement = connection.createStatement();
            ResultSet result = statement
                    .executeQuery("SELECT * from VISUALIZACOES WHERE AULA='" + aula+ "' and IDUSER='"+idUser+"'");

            if (!result.next()) {

                statement.execute("INSERT INTO VISUALIZACOES( AULA, IDUSER) VALUES('"+aula+ "','" + idUser + "')");
                
              
            }
            result.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }


    }


    public List<String> visualizacoesCont() throws JSONException {

   
        List<String> lista=new LinkedList<String>();
      
        ConectionPostgreSQL.connect();

        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VISUALIZACOES GROUP BY AULA ORDER BY COUNT(*) DESC");

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                lista.add(resultSet.getString("AULA"));
            
            }

            resultSet.close();

            return lista;

        } catch (SQLException e) {

            e.printStackTrace();
        }
        return lista;

    }


    public List<String> comentariosCont(String cat) throws JSONException {
 
        List<String> lista=new LinkedList<String>();
      
        ConectionPostgreSQL.connect();
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM COMENTARIOS WHERE CATEGORIA='"+cat+"' GROUP BY PLANO ORDER BY COUNT(*) DESC");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                lista.add(resultSet.getString("PLANO"));
            
            }
            resultSet.close();
            return lista;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
}
}