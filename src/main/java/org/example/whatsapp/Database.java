package org.example.whatsapp;

import java.sql.*;

public class Database {

    final String user = "postgres";
    final String pass = "1234";
    final String url2 = "";

    private Connection connection = null;

    public Connection conect(){
        try {
            connection = DriverManager.getConnection(url2, user, pass);
            System.out.println("Conectado a la base de datos");
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return connection;
    }

    public void disconect(){
        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
                System.out.println("Base de datos desconectada");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ResultSet executeQuery(String consulta){
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(consulta);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return resultSet;
    }



}
