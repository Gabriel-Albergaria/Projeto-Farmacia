/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Registro_usuario;
import java.sql.ResultSet;

/**
 *
 * @author Gabriel
 */
public class BuscarDAO {
    public boolean loginFunc(String email, String senha, String pessoa){
       String sql = "";
       String senha_selecionar = "";
        if(pessoa.equals("Funcionário")){
            sql = "SELECT senha_func FROM funcionario WHERE email_func = ?";
            senha_selecionar = "senha_func";
        }else if (pessoa.equals("Cliente")){
            sql = "SELECT senha_cliente FROM cliente WHERE email_cliente = ?";
            senha_selecionar = "senha_cliente";
        }else{
            System.err.println("pessoa inválido: " + pessoa); 
        }
        
        
        
        try (Connection conexao = Conexao_farmacia.getConnection();
                
            PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            
            ResultSet busca = stmt.executeQuery();
            
             if (busca.next()) {
                 String senhaDoBanco = busca.getString(senha_selecionar);
                 
                 if (senha.equals(senhaDoBanco)) {
                    return true; 
                 }
            }

    } catch (SQLException e) {
        System.err.println("Erro ao verificar login: " + e.getMessage());
        e.printStackTrace();
    }
         return false;   
        
    }
        
}
   
                



