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
    // A query SQL está correta
        String sql = "INSERT INTO funcionario (nome, cpf, email, senha, data_nascimento, endereco)" + "VALUES(?, ?, ?, ?, ?, ?)";

    // O try-with-resources garante que a conexão será fechada
    try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        // Desliga o auto-commit para gerenciar a transação manualmente
        conexao.setAutoCommit(false);

        // 1. Vincular os valores do objeto aos '?'
        stmt.setString(1, registro.getNome());      // <-- CORREÇÃO 1: Usar getNome()
        stmt.setString(2, registro.getCpf());
        stmt.setString(3, registro.getEmail());
        stmt.setString(4, registro.getSenha());

        // Converte java.util.Date para java.sql.Date
        // Assumindo que o método getter na sua classe Modelo.Registro_usuario é getNascimento()
        java.sql.Date dataSQL = new java.sql.Date(registro.getNascimento().getTime()); // <-- CORREÇÃO 2: Usar getNascimento()
        stmt.setDate(5, dataSQL);

        stmt.setString(6, registro.getEndereco());

        // 2. Executar o comando
        int linhasAfetadas = stmt.executeUpdate();

        // 3. Gerenciar a transação
        if (linhasAfetadas > 0) {
            conexao.commit(); // Salva permanentemente no banco
            return true;      // Retorna sucesso
        } else {
            conexao.rollback(); // Desfaz a operação se nada foi inserido
            return false;     // Retorna falha
        }

    } catch (SQLException e) {
        // Imprime o erro no console para ajudar a depurar
        System.err.println("Erro ao salvar registro: " + e.getMessage());
        e.printStackTrace(); // Imprime o "rastro" completo do erro
        return false; // Retorna falha
    }
}
    
}
