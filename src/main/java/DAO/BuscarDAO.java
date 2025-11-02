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
    
    public Registro_usuario Pesquisa(String pesquisa, String tipo_selecionado, String metodo_pesquisa){
        String sql = "";
        String pesquisa_selecionar = "";
        
        if(tipo_selecionado.equals("Cliente") && metodo_pesquisa.equals("CPF")){
            sql = "SELECT  * FROM cliente WHERE cpf_cliente = ?";
            Registro_usuario cliente_encontrado = null;
            
            try (Connection conexao = Conexao_farmacia.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pesquisa);

            ResultSet resultado = stmt.executeQuery();

            // 5. Verifica se o banco encontrou alguma linha
            if (resultado.next()) {
                // ENCONTROU! Agora vamos "hidratar" (preencher) o objeto
            
                cliente_encontrado = new Registro_usuario();
            
                // 6. Pega os dados de cada coluna do ResultSet (rs)
                //    e usa os "setters" do seu objeto para preenchê-lo.
                //    (Os nomes dos seus setters podem ser um pouco diferentes!)
            
                // Use os nomes EXATOS das colunas do seu banco
                cliente_encontrado.setNome(resultado.getString("nome_cliente"));
                cliente_encontrado.setCpf(resultado.getString("cpf_cliente"));
                cliente_encontrado.setEmail(resultado.getString("email_cliente"));
                cliente_encontrado.setEndereco(resultado.getString("endereco_cliente"));
                cliente_encontrado.setTelefone(resultado.getString("telefone_cliente"));
                cliente_encontrado.setNascimento(resultado.getDate("data_nascimento"));
                // (Adicione qualquer outro dado que você queira carregar)
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF: " + e.getMessage());
            e.printStackTrace();
        }

        // 7. Retorna o objeto.
        //    Se encontrou, o objeto estará preenchido.
        //    Se não encontrou (ou deu erro), ele retornará 'null'.
        return cliente_encontrado;

        }
        return null;
    }
}



