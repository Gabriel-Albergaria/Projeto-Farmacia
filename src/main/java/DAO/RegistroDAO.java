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
    
    public boolean salvar(Registro_usuario registro){
        String sql = "INSERT INTO Funcionario (nome, cpf, email, senha, data_nascimento, endereco)"  + "VALUES(?, ?, ?, ?, ?, ?)";
    
         try (Connection conexao = Conexao_farmacia.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            // Desliga o auto-commit para gerenciar a transação manualmente
            conexao.setAutoCommit(false); 
            
            // 2. Vincular os valores do objeto aos '?'
            stmt.setString(1, registro.getNome());
            stmt.setString(2, registro.getCpf());
            stmt.setString(3, registro.getEmail());
            stmt.setString(4, registro.getSenha());
            
            // Converte java.util.Date para java.sql.Date se necessário, 
            // ou apenas usa o objeto Date se for java.sql.Date
            // Assumindo que você usa java.util.Date no modelo e precisa de conversão simples para MySQL:
            java.sql.Date dataSQL = new java.sql.Date(registro.getData().getTime());
            stmt.setDate(5, dataSQL);
            
            stmt.setString(6, registro.getEndereco());

            // 3. Executar o comando
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                conexao.commit(); // Salva permanentemente no banco
                return true;
            } else {
                conexao.rollback(); // Reverte
                return false;
            }

        } catch (SQLException e) {
            // Se der erro (ex: CPF/Email duplicado ou NOT NULL violation)
            System.err.println("Erro ao salvar registro: " + e.getMessage());
            // No caso de UNIQUE KEY (CPF/Email), você pode tratar a exceção e retornar 'false'
            return false;
    
    
        }
    }
    
}
