/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import model.Project;
import utility.DatabaseConnection;

/**
 *
 * @author Marcus Rocha
 */
public class ProjectController {
    
    public void saveProject(Project project){
        String sql = "INSERT INTO project (name, description, createdAt, updatedAt) VALUES (?,?,?,?)";
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            statement.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            
            statement.execute();
            
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar projeto no banco de dados " + ex.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn, statement);
        }
    }
    
    public void removeProject(int idProject){
        
        String sql = "DELETE FROM project WHERE id = ?";
        
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement(sql);
            
            statement.setInt(1, idProject);
            
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar projeto do banco de dados " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn, statement);
        }
    }
    
    public void updateProject(Project project){
        String sql = "UPDATE project SET name = ?, description = ?, createdAt = ?, updatedAt = ?"
                + "WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement(sql);
            
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            statement.setDate(4, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            statement.setInt(5, project.getId());
            //new Date(project.getUpdatedAt().getTime())
            
            statement.execute();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar projeto no banco de dados " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn, statement);
        }  
    }
    
    public List<Project> getAll(){
        
        String sql = "SELECT * FROM project";
        
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Project> projects = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            statement = conn.prepareStatement(sql);
            
            resultSet = statement.executeQuery();
            
           while(resultSet.next()){
               Project project = new Project();
               project.setId(resultSet.getInt("id"));
               project.setName(resultSet.getString("name"));
               project.setDescription(resultSet.getString("description")); 
               project.setCreatedAt(resultSet.getDate("createdAt"));        
               project.setCreatedAt(resultSet.getDate("updatedAt"));  
               
               projects.add(project);
           }
                    
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar projetos do banco de dados " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(conn, statement, resultSet);
        }
        return projects;
    }
    
    
}
