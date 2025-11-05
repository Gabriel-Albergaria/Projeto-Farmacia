/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Gabriel
 */
public class Conexao_farmacia {
    public static Connection  getConnection(){
        Connection Conexao = null;
        
        try{
            String url = "jdbc:mysql://127.0.0.1:3306/farmacia";
            String usuario = "root";
            String senha = "1234";
            Conexao = DriverManager.getConnection(url,usuario,senha) ; 
        } catch (SQLException e){
            System.out.println("Erros" + e.getMessage());
        }
        
        return Conexao;
        
        
        
    }
    
    
}
