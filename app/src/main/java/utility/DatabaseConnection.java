/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Marcus Rocha
 */
public class DatabaseConnection {
    
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver"; //informo qual driver quero utilizar e qual o banco
    public static final String URL = "jdbc:mysql://localhost:3306/todoapp"; //endereço do servidor onde está minha aplicação + nome do banco
    public static final String USER = "root"; 
    public static final String PASS = "";
    
    public static Connection getConnection(){
        try{
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        }catch(Exception e){
            throw new RuntimeException("Erro na conexão com o banco de dados");
        }
    }
    
    public static void closeConnection(Connection connection){
        try{
            if(connection != null){
                connection.close();
            }
        }catch(Exception e){
            throw new RuntimeException("Erro ao fechar conexão com o banco de dados");
        }        
    }
    
    public static void closeConnection(Connection connection, PreparedStatement statement){
        try{
            if(connection != null){
                connection.close();
            }
             if(statement != null){
                 statement.close();
             }
        }catch(Exception e){
            throw new RuntimeException("Erro ao fechar conexão com o banco de dados");
        }        
    }
    
    public static void closeConnection(Connection connection, PreparedStatement statement, ResultSet resultSet){
        try{
            if(connection != null){
                connection.close();
            }
             if(statement != null){
                 statement.close();
             }
             
             if(resultSet != null){
                 resultSet.close();
             }
        }catch(Exception e){
            throw new RuntimeException("Erro ao fechar conexão com o banco de dados");
        }        
    }
}
