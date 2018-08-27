/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.teste;

import com.builtcode.dao.ModeloDAO;
import com.builtcode.dao.MontadoraDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabio
 */
public class Teste {
    public static void main(String[] args){
        try {
            ModeloDAO dao = new ModeloDAO();
            System.out.println("Modelo"+Math.ceil(dao.total()/20.0));

        } catch (SQLException ex) {
            Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
