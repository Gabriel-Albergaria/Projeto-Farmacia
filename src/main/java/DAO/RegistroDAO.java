/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Gabriel
 */
import Modelo.Registro_usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistroDAO {
    public boolean salvar(Registro_usuario registro) {
        String sql = "INSERT INTO funcionario (nome_func, cpf_func, email_func, senha_func, data_nascimento_func, endereço_func)" + "VALUES(?, ?, ?, ?, ?, ?)";

    // O try-with-resources garante que a conexão será fechada
    try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        // Desliga o auto-commit para gerenciar a transação manualmente
        conexao.setAutoCommit(false);


        stmt.setString(1, registro.getNome());     //cada numero como 1 é a ordem das ? lá em ciam 
        stmt.setString(2, registro.getCpf());
        stmt.setString(3, registro.getEmail());
        stmt.setString(4, registro.getSenha());

        
        java.sql.Date dataSQL = new java.sql.Date(registro.getNascimento().getTime()); // data slq T-T
        stmt.setDate(5, dataSQL);

        stmt.setString(6, registro.getEndereco());

  
        int linhasAfetadas = stmt.executeUpdate();

        if (linhasAfetadas > 0) {
            conexao.commit(); 
            return true;     
        } else {
            conexao.rollback(); 
            return false;    
        }

    } catch (SQLException e) {
        
        System.err.println("Erro ao salvar registro: " + e.getMessage());
        e.printStackTrace(); 
        return false; 
    }
}
    
}
