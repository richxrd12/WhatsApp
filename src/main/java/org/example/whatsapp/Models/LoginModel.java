package org.example.whatsapp.Models;

import java.sql.*;

public class LoginModel {

    private String bd = "whatsapp";
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String pass = "1234";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Connection connection;

    public void startConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        connection = DriverManager.getConnection(url+bd, user, pass);
    }

    public void endConnection() throws SQLException {
        connection.close();
    }

    public boolean comprobarLogin(String correo, String password){
        final String SELECT_QUERY = "SELECT * FROM usuarios where correo = ? and password = ?";

        try{
            startConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);

            preparedStatement.setString(1, correo);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                endConnection();
                return true;
            }else{
                endConnection();
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
