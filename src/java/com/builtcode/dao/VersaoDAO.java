/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Modelo;
import com.builtcode.beans.Versao;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersaoDAO {

    Connection conexao;

    public VersaoDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    

    public Mensagem novo(Versao v) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO versao(id, descricao, idModelo, ano , idMedida, carga) ";
        sql += "VALUES (?,?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, v.getDescricao());
            st.setString(3, v.getModelo().getId());
            st.setString(4, v.getAno());
            st.setString(5, v.getMedida().getId());
            st.setString(6, v.getCarga());
            
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Versão Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Versao m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update versao set descricao=?, idModelo=?, ano=?, idMedida=?,carga=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getDescricao());
            st.setString(2, m.getModelo().getId());
            st.setString(3, m.getAno());
            st.setString(4, m.getMedida().getId());
            st.setString(5, m.getCarga());            
            st.setString(6, m.getCodigo());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Versão Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from versao where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Versão Excluída com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Versao> listar() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Versao> dados = new ArrayList<Versao>();
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM versao ";
        
        try {

            st = conexao.prepareStatement(sql);
            
            rs = st.executeQuery();

            while (rs.next()) {
                Versao m = new Versao();
                m.setCodigo(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setModelo(modeloDAO.buscar(rs.getString("idModelo")));
                m.setAno(rs.getString("ano"));
                m.setCarga(rs.getString("carga"));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                dados.add(m);
            }
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();             
            e.printStackTrace();
            return null;
        }
    }

    public Versao buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM versao where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Versao m = new Versao();
                m.setCodigo(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setModelo(modeloDAO.buscar(rs.getString("idModelo")));
                m.setAno(rs.getString("ano"));
                m.setCarga(rs.getString("carga"));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                //conexao.close();
                modeloDAO.fecharconexao();
                medidaDAO.fecharconexao();                  
                return m;
            }
            else{
                //conexao.close();
                modeloDAO.fecharconexao();
                medidaDAO.fecharconexao();                  
                return null;
            }
        } catch (SQLException e) {
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            e.printStackTrace();
            return null;
        }
    }    
 
    
        public List<Versao> listarUnicaPorModelo(String idModelo) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Versao> dados = new ArrayList<Versao>();
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();
        
        
        String sql = "SELECT distinct descricao FROM versao where idModelo=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idModelo);
            
            rs = st.executeQuery();

            while (rs.next()) {
                Versao m = new Versao();
                m.setCodigo(rs.getString("descricao"));
                m.setDescricao(rs.getString("descricao"));

                dados.add(m);
            }
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            e.printStackTrace();
            return null;
        }
    }       
    

    
    public List<Versao> listarPorModelo(String idModelo) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Versao> dados = new ArrayList<Versao>();
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();
        
        
        String sql = "SELECT * FROM versao where idModelo=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idModelo);
            
            rs = st.executeQuery();

            while (rs.next()) {
                Versao m = new Versao();
                m.setCodigo(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setModelo(modeloDAO.buscar(rs.getString("idModelo")));
                m.setAno(rs.getString("ano"));
                m.setCarga(rs.getString("carga"));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));                

                dados.add(m);
            }
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            e.printStackTrace();
            return null;
        }
    }       
    
}
