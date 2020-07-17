package com.example.robotica.services;

import java.sql.*;

public class ConectionSQLite {
    public static void connect() {
        Connection connection;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:BancoSQL.db");
            Statement statement = connection.createStatement();

            // criando uma tabela
            statement.execute("CREATE TABLE IF NOT EXISTS USUARIO( ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME VARCHAR , EMAIL VARCHAR, SENHA VARCHAR, CIDADE VARCHAR, SEXO VARCHAR, CATEGORIA VARCHAR, IDADE INTEGER)");
            statement.execute("CREATE TABLE IF NOT EXISTS COMENTARIOS(ID INTEGER PRIMARY KEY AUTOINCREMENT,IDUSER INTEGER,CATEGORIA VARCHAR, CONTEUDO VARCHAR, DATA VARCHAR, PLANO VARCHAR,foreign key (IDUSER) references USUARIO (ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS RECOMENDACOES (ID INTEGER PRIMARY KEY AUTOINCREMENT,IDUSER INTEGER,AULA VARCHAR,CATEGORIA VARCHAR,foreign key (IDUSER) references USUARIO (ID))");
            statement.execute("CREATE TABLE IF NOT EXISTS VISUALIZACOES (IDUSER INTEGER,AULA VARCHAR,foreign key (IDUSER) references USUARIO (ID))");
    
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
           
            // inserindo registros
            //statement.execute("INSERT INTO RC_TEST( ID, NOME) VALUES (1, 'Wolmir'), (2, 'Garbin')");

            // lendo os registros
         /*  PreparedStatement stmt = connection.prepareStatement("select * from USUARIO");
           ResultSet resultSet = stmt.executeQuery();

           while (resultSet.next()) {
            Integer id = resultSet.getInt("ID");
            String nome = resultSet.getString("NOME");

            System.out.println( id + " - " + nome);*/
        }
        

        
         
    }


