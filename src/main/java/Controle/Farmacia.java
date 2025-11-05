/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;

import DAO.Conexao_farmacia;
import Visao.vPrincipal;
import java.sql.Connection;

/**
 *
 * @author Gabriel
 */
public class Farmacia {//oi
    public static void main(String[] args) {
        System.out.println("davimol");
        java.awt.EventQueue.invokeLater(() -> {
            new vPrincipal().setVisible(true);
        });
        Connection conect = Conexao_farmacia.getConnection();
        
        if (conect != null){
            System.out.println("Conectado");
        }else{
            System.out.println("deu n");
        }
        
        
        
        
    }
    
}
