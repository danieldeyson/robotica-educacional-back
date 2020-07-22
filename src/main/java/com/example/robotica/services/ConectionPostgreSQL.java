package com.example.robotica.services;

import java.sql.*;

public class ConectionPostgreSQL {
    public static void connect() {
        Connection connection;
        try {
                        
            connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-173-173.compute-1.amazonaws.com/d8o8ibqrhsnoer?user=jxfbevvuyycmav&password=a607ebffbfcb55e8034373ee206e0796b74e5e5f6d85400aa5de3e19d71279fc&sslmode=require");
            Statement statement = connection.createStatement();

            // criando uma tabela
            //statement.execute("CREATE TABLE IF NOT EXISTS USUARIO( ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME VARCHAR , EMAIL VARCHAR, SENHA VARCHAR, CIDADE VARCHAR, SEXO VARCHAR, CATEGORIA VARCHAR, IDADE INTEGER)");
            //statement.execute("CREATE TABLE IF NOT EXISTS COMENTARIOS(ID INTEGER PRIMARY KEY AUTOINCREMENT,IDUSER INTEGER,CATEGORIA VARCHAR, CONTEUDO VARCHAR, DATA VARCHAR, PLANO VARCHAR,foreign key (IDUSER) references USUARIO (ID))");
            //statement.execute("CREATE TABLE IF NOT EXISTS RECOMENDACOES (ID INTEGER PRIMARY KEY AUTOINCREMENT,IDUSER INTEGER,AULA VARCHAR,CATEGORIA VARCHAR,foreign key (IDUSER) references USUARIO (ID))");
            //sstatement.execute("CREATE TABLE IF NOT EXISTS VISUALIZACOES (IDUSER INTEGER,AULA VARCHAR,foreign key (IDUSER) references USUARIO (ID))");
    
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


