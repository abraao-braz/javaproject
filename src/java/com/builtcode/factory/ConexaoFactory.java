/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.factory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoFactory {

    private static Connection con = null;

    public static Connection getConnection() throws SQLException {

	//String url = "jdbc:mysql://localhost/pirelli_pneus";
        //String url = "jdbc:mysql://179.124.40.115/pirelli_pneus";
        String url = "jdbc:mysql://glassfishhmlg.caqvwqhdn1uk.us-east-1.rds.amazonaws.com/glassfish";
        String usuario = "glassfish";
        String senha = "glassfishS0u123";
        //String senha = "";

        try {
            FileReader arquivo = new FileReader(System.getProperty("user.dir") + "/pirelli.txt");
            BufferedReader dados = new BufferedReader(arquivo);
            url = dados.readLine();
            usuario = dados.readLine();
            senha = dados.readLine();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConexaoFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConexaoFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*
        if (ConexaoFactory.con == null || ConexaoFactory.con.isClosed()) {
            con = DriverManager.getConnection(url, usuario, senha);
        }
        */
        
        return DriverManager.getConnection(url, usuario, senha);
    }
}
