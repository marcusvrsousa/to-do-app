/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Task;
import utility.DatabaseConnection;

/**
 *
 * @author Marcus Rocha
 */
public class TaskController {
    
    
    public void save(Task task) throws SQLException{
        String sql = "INSERT INTO tasks (idProject,"
                + " name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES (?,?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            
            System.out.println(task.isIsCompleted());
        } catch(Exception ex){
            throw new RuntimeException("Erro ao salvar tarefa no banco de dados" + ex.getMessage());
        } finally{
            DatabaseConnection.closeConnection(conn, statement);
        }
        
    }
    public void update(Task task){
        String sql = "UPDATE tasks SET "
                + "idProject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "completed = ?,"
                + "notes = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?"
                + "WHERE id  = ?";
        Connection conn = null; //crio a variavel do tipo Connection
        PreparedStatement statement = null; // Crio a variavel tipo PreparedStatement

        try {
            // chamo o metodo da classe DatabaseConnection e atribuo na variavel conn
            conn = DatabaseConnection.getConnection(); 
            
            //preparo o comando SQL que vai ser executado no BD 
            statement = conn.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            statement.execute();
            System.out.println(statement);
        }catch (Exception ex){
            throw new RuntimeException("Erro ao atualizar informações no banco de dados" + ex.getMessage());
        } finally{
            DatabaseConnection.closeConnection(conn, statement);
        }
            
    }
    public void removeById(int taskId){
        String sql = "DELETE FROM tasks WHERE id = ?"; //comando a ser executado no BD
        Connection conn = null; //crio a variavel do tipo Connection
        PreparedStatement statement = null; // Crio a variavel tipo PreparedStatement
        
        try {
            // chamo o metodo da classe DatabaseConnection e atribuo na variavel conn
            conn = DatabaseConnection.getConnection(); 
            
            //preparo o comando SQL que vai ser executada no BD 
            statement = conn.prepareStatement(sql);
            
            /*seto o id da task que vai ser informada como parametro 
            e indico qual interrogação vai ser alterada, nesse caso é a primeira. */
            statement.setInt(1, taskId);
            statement.execute(); //executa o comando SQL
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao remover tarefa do banco de dados" + ex.getMessage());
        }finally{
           DatabaseConnection.closeConnection(conn, statement);
        }
    }
    public List<Task> getAll(int idProject){
        
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            // chamo o metodo da classe DatabaseConnection e atribuo na variavel conn
            conn = DatabaseConnection.getConnection(); 
            
            //preparo o comando SQL que vai ser executada no BD 
            statement = conn.prepareStatement(sql);
            
            //setando o valor que vai ser buscado no BD
            statement.setInt(1, idProject);
            
            //o metodo executeQuery retorna a resposta da consulta ao banco de dados
            resultSet = statement.executeQuery();
            
            //enquanto houver uma proxima linha...
            while(resultSet.next()){
                
                Task task = new Task();
                //Uso o metodo set passando como parametro o resultado que está no resultSet informando o nome dos campos
                task.setId(resultSet.getInt("id"));
                task.setId(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setUpdatedAt(resultSet.getDate("updatedAt"));
                
                //e adiciono na minha array list 
                tasks.add(task);
            }
                    
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao listar tarefas do banco de dados" + ex.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn, statement, resultSet);
        }
        
        //retorna a lista de tarefas gerada
        return tasks;
    }
}
