/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Gabriel
 */
import Modelo.Registro_fornecedor;
import Modelo.Registro_produto;
import Modelo.Registro_usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistroDAO {
    public boolean salvar(Registro_usuario registro, String pessoa) {
        String sql ="";
        if(pessoa.equals("Funcionário")){
            sql = "INSERT INTO funcionario (nome_func, cpf_func, email_func, senha_func, data_nascimento_func, endereco_func, telefone_func)" + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        }else if (pessoa.equals("Cliente")){
            sql = "INSERT INTO cliente (nome_cliente, cpf_cliente, email_cliente, senha_cliente, data_nascimento, endereco_cliente, telefone_cliente)" + "VALUES(?, ?, ?, ?, ?, ?, ?)";
        }
        else{
            System.err.println("pessoa inválido: " + pessoa);      
        }

    // O try-with-resources garante que a conexão será fechada
    try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        // Desliga o auto-commit para gerenciar a transação manualmente
        conexao.setAutoCommit(false);


        stmt.setString(1, registro.getNome());     //cada numero como 1 é a ordem das ? lá em ciam 
        stmt.setString(2, registro.getCpf());
        stmt.setString(3, registro.getEmail());
        stmt.setString(4, registro.getSenha());
        stmt.setString(7, registro.getTelefone());

        
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
  
    public boolean Registrofornecedor(Registro_fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedor (cnpj_fornecedor, email_fornecedor, endereco_fornecedor, nome_fornecedor, telefone_fornecedor)" + "VALUES(?, ?, ?, ?, ?)";
        
        try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        // Desliga o auto-commit para gerenciar a transação manualmente
        conexao.setAutoCommit(false);


        
        stmt.setString(1, fornecedor.getCnpj());     //cada numero como 1 é a ordem das ? lá em ciam 
        stmt.setString(2, fornecedor.getEmail());
        stmt.setString(3, fornecedor.getEndereco());
        stmt.setString(4, fornecedor.getNome());
        stmt.setString(5, fornecedor.getTelefone());
        
        
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
    
    public boolean RegistroProduto(Registro_produto produto){
        String sql = "INSERT INTO produto (preco_produto, nome_produto, quantidade_produto)" + "VALUES(?, ?, ?)";
        
        try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {

        // Desliga o auto-commit para gerenciar a transação manualmente
        conexao.setAutoCommit(false);


        
        stmt.setDouble(1, produto.getPreco_produto());     //cada numero como 1 é a ordem das ? lá em ciam 
        stmt.setString(2, produto.getNome_produto());
        stmt.setInt(3, produto.getQuantidade_produto());
      
        
        
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
    
    public boolean atualizar_usuario(Registro_usuario usuario_para_atualizar, String tipo_pessoa) {
    
    String sql = "";

    if (tipo_pessoa.equals("Cliente")) {
        sql = "UPDATE cliente SET nome_cliente = ?, email_cliente = ?, " + "endereco_cliente = ?, telefone_cliente = ?, data_nascimento = ? " + "WHERE cpf_cliente = ?";
    } else if (tipo_pessoa.equals("Funcionário")) {
        sql = "UPDATE funcionario SET nome_func = ?, email_func = ?, " + "endereco_func = ?, telefone_func = ?, data_nascimento_func = ? " + "WHERE cpf_func = ?";
    } else {
        System.err.println("Tipo de pessoa inválido para atualização.");
        return false; 
    }

    try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
         
        conexao.setAutoCommit(false);
        
        stmt.setString(1, usuario_para_atualizar.getNome());
        stmt.setString(2, usuario_para_atualizar.getEmail());
        stmt.setString(3, usuario_para_atualizar.getEndereco());
        stmt.setString(4, usuario_para_atualizar.getTelefone());
        stmt.setDate(5, new java.sql.Date(usuario_para_atualizar.getNascimento().getTime()));       
        stmt.setString(6, usuario_para_atualizar.getCpf()); 

        int linhas_afetadas = stmt.executeUpdate();
  
        if (linhas_afetadas > 0) {
            conexao.commit(); 
            return true;
        } else {
            conexao.rollback();
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Erro ao atualizar registro: " + e.getMessage());
        e.printStackTrace();
        return false;
    }

    }
    
    public boolean atualizar_produto(Registro_produto produto_para_editar) {
    
    String sql = "UPDATE produto SET nome_produto = ?, preco_produto = ?, quantidade_produto = ? WHERE id_produto = ?" ;

  
    try (Connection conexao = Conexao_farmacia.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql)) {
         
        conexao.setAutoCommit(false);
        
        stmt.setString(1, produto_para_editar.getNome_produto());
        stmt.setDouble(2, produto_para_editar.getPreco_produto());
        stmt.setInt(3, produto_para_editar.getQuantidade_produto());
        stmt.setInt(4, produto_para_editar.getId());


        int linhas_afetadas = stmt.executeUpdate();
  
        if (linhas_afetadas > 0) {
            conexao.commit(); 
            return true;
        } else {
            conexao.rollback();
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Erro ao atualizar registro: " + e.getMessage());
        e.printStackTrace();
        return false;
    }

    }
}
