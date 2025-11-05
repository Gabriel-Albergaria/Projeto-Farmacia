package DAO;

import Modelo.Registro_fornecedor;
import Modelo.Registro_produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Modelo.Registro_usuario;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BuscarDAO {
    
    public boolean loginFunc(String email, String senha, String pessoa){
        String sql = "";
        String senha_selecionar = "";
        
        if(pessoa.equals("Funcionário")){
            sql = "SELECT senha_func FROM funcionario WHERE email_func = ?";
            senha_selecionar = "senha_func";
        } else if (pessoa.equals("Cliente")){
            sql = "SELECT senha_cliente FROM cliente WHERE email_cliente = ?";
            senha_selecionar = "senha_cliente";
        } else {
            System.err.println("pessoa inválido: " + pessoa);
            return false; // <-- CORREÇÃO 3 (Adicionado 'return')
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
    
    public ArrayList<Registro_usuario> Pesquisa_usuario(String pesquisa, String tipo_selecionado, String metodo_pesquisa){
        String sql = "";
        Registro_usuario usuario_encontrado = null;
        String tabela = "";
        String col_nome = "", col_cpf = "", col_email = "", col_end = "", col_tel = "", col_data = "";

      
        if (tipo_selecionado.equals("Cliente")) {
            tabela = "cliente";
            col_nome = "nome_cliente";
            col_cpf = "cpf_cliente";
            col_email = "email_cliente";
            col_end = "endereco_cliente";
            col_tel = "telefone_cliente";
            col_data = "data_nascimento";

        } else if (tipo_selecionado.equals("Funcionário")) {
            tabela = "funcionario";
            col_nome = "nome_func";
            col_cpf = "cpf_func";
            col_email = "email_func";
            col_end = "endereco_func";
            col_tel = "telefone_func";
            col_data = "data_nascimento_func";

        } else {
            System.err.println("Tipo de pesquisa não suportado: " + tipo_selecionado);
            return null;
        }

        String coluna_busca = "";
        if (metodo_pesquisa.equals("CPF")) {
            coluna_busca = col_cpf;
        } else if (metodo_pesquisa.equals("NOME")) {
            coluna_busca = col_nome;
        } else {
            System.err.println("Método de pesquisa não suportado: " + metodo_pesquisa);
            return null;
        }

        // Monta a query final de forma dinâmica
        // Ex: "SELECT * FROM cliente WHERE cpf_cliente = ?"
        sql = "SELECT * FROM " + tabela + " WHERE " + coluna_busca + " like '%" + pesquisa + "%'";
        ArrayList<Registro_usuario> users = new ArrayList<>();

        try (Connection conexao = Conexao_farmacia.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            //stmt.setString(1, pesquisa);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                usuario_encontrado = new Registro_usuario();

                usuario_encontrado.setNome(resultado.getString(col_nome));
                usuario_encontrado.setCpf(resultado.getString(col_cpf));
                usuario_encontrado.setEmail(resultado.getString(col_email));
                usuario_encontrado.setEndereco(resultado.getString(col_end));
                usuario_encontrado.setTelefone(resultado.getString(col_tel));
                usuario_encontrado.setNascimento(resultado.getDate(col_data));
                users.add(usuario_encontrado);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao executar pesquisa: " + e.getMessage());
            e.printStackTrace();
        }

        return users;
}


    public Registro_produto Pesquisa_produto(String pesquisa, String tipo_selecionado){
        String sql = "";
        Registro_produto produto_encontrado = null;
        String coluna_busca = "";

        // 1. Define em qual coluna do banco de dados vamos pesquisar
        if (tipo_selecionado.equals("NOME")) {
            coluna_busca = "nome_produto";
        } else {
            System.err.println("Método de pesquisa ('" + tipo_selecionado + "') não suportado para Produto.");
            return null; // Retorna nulo se o método não for 'NOME', 'ID', etc.
        }

        // 2. Monta a query dinamicamente
        sql = String.format("SELECT * FROM produto WHERE %s = ?", coluna_busca);

        // 3. Executa a busca no banco
        try (Connection conexao = Conexao_farmacia.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pesquisa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto_encontrado = new Registro_produto();
                produto_encontrado.setNome_produto(rs.getString("nome_produto"));
                produto_encontrado.setPreco_produto(rs.getDouble("preco_produto"));
                produto_encontrado.setQuantidade_produto(rs.getInt("quantidade_produto"));

            }
        } catch (SQLException e) {
             System.err.println("Erro ao buscar produto: " + e.getMessage());
             e.printStackTrace();
        }

        // 5. Retorna o objeto (nulo ou preenchido)
        return produto_encontrado;
    }

    public Registro_fornecedor Pesquisa_fornecedor(String pesquisa, String tipo_selecionado){
        String sql = "";
        Registro_fornecedor fornecedor_encontrado = null;
        String coluna_busca = "";

        // 1. Define em qual coluna do banco de dados vamos pesquisar
        if (tipo_selecionado.equals("NOME")) {
            coluna_busca = "nome_fornecedor";
        } else if(tipo_selecionado.equals("CNPJ")){
            coluna_busca = "cnpj_fornecedor";
        }else if(tipo_selecionado.equals("EMAIL")){
            coluna_busca = "email_fornecedor";
        } else {
            System.err.println("Método de pesquisa ('" + tipo_selecionado + "') não suportado para Fornecedor.");
            return null;
        }

        sql = String.format("SELECT * FROM fornecedor WHERE %s = ?", coluna_busca);

     
        try (Connection conexao = Conexao_farmacia.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pesquisa);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                fornecedor_encontrado = new Registro_fornecedor();
                fornecedor_encontrado.setNome(rs.getString("nome_fornecedor"));
                fornecedor_encontrado.setCnpj(rs.getString("cnpj_fornecedor"));
                fornecedor_encontrado.setEmail(rs.getString("email_fornecedor"));

            }
        } catch (SQLException e) {
             System.err.println("Erro ao buscar fornecedor: " + e.getMessage());
             e.printStackTrace();
        }

        return fornecedor_encontrado;
    }
}


