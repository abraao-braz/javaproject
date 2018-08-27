/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Carroceria;
import com.builtcode.beans.Modelo;
import com.builtcode.beans.Segmento;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloDAO {

    Connection conexao;

    public ModeloDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM modelo ";

        try {
            st = conexao.prepareStatement(sql);
            rs = st.executeQuery();
            rs.next();
            int tot =  rs.getInt("tot");
            //conexao.close();
            return tot;
        } catch (SQLException e) {
            //conexao.close();
            return 0;
        } 
    }    

    public Mensagem novo(Modelo m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO modelo(id, descricao, idMontadora, idSegmento, cat1, cat2, cat3) ";
        sql += "VALUES (?,?,?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, m.getDescricao());
            st.setString(3, m.getMontadora().getId());
            st.setString(4, m.getSegmento().getId());
            st.setString(5, m.getCat1());
            st.setString(6, m.getCat2());
            st.setString(7, m.getCat3());
            
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Modelo Gravado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Modelo m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update modelo set descricao=?, idMontadora=?, idSegmento=?, idCarroceria=?, cat1=?, cat2=?, cat3=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getDescricao());
            st.setString(2, m.getMontadora().getId());
            st.setString(3, m.getSegmento().getId());
            st.setString(4, m.getCarroceria().getId());            
            st.setString(5, m.getCat1());
            st.setString(6, m.getCat2());
            st.setString(7, m.getCat3());  
            
            st.setString(8, m.getId());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Modelo Atualizado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from modelo where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Modelo Excluído com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Modelo> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Modelo> dados = new ArrayList<Modelo>();
        MontadoraDAO montadoraDAO = new MontadoraDAO();

        String sql = "SELECT * FROM modelo order by descricao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);
            
            rs = st.executeQuery();

            while (rs.next()) {
                Modelo m = new Modelo();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setMontadora(montadoraDAO.buscar(rs.getString("idMontadora")));
                m.setCat1(rs.getString("cat1"));
                m.setCat2(rs.getString("cat2"));
                m.setCat3(rs.getString("cat3"));
                dados.add(m);
            }
            //conexao.close();
            montadoraDAO.fecharconexao();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            montadoraDAO.fecharconexao();
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public Modelo buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        MontadoraDAO montadoraDAO = new MontadoraDAO();

        String sql = "SELECT * FROM modelo where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Modelo m = new Modelo();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setMontadora(montadoraDAO.buscar(rs.getString("idMontadora")));
                m.setSegmento(new Segmento(rs.getString("idSegmento"),null));
                m.setCarroceria(new Carroceria(rs.getString("idCarroceria"),null));
                m.setCat1(rs.getString("cat1"));
                m.setCat2(rs.getString("cat2"));
                m.setCat3(rs.getString("cat3"));                
                //conexao.close();
                montadoraDAO.fecharconexao();
                return m;
            }
            else{
                //conexao.close();
                montadoraDAO.fecharconexao();
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    public List<Modelo> listarPorMontadora(String idMontadora,int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Modelo> dados = new ArrayList<Modelo>();
        MontadoraDAO montadoraDAO = new MontadoraDAO();

        //String sql = "SELECT * FROM modelo where idMontadora = ? and (select count(*) from versao where versao.idModelo=modelo.id)>0 order by descricao limit ?,?";
        String sql = "SELECT * FROM modelo where idMontadora = ? order by descricao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idMontadora);
            st.setInt(2, (pagina-1)*tamanho);
            st.setInt(3, tamanho);            
            rs = st.executeQuery();

            while (rs.next()) {
                Modelo m = new Modelo();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setMontadora(montadoraDAO.buscar(rs.getString("idMontadora")));
                m.setCat1(rs.getString("cat1"));
                m.setCat2(rs.getString("cat2"));
                m.setCat3(rs.getString("cat3"));                
                dados.add(m);
            }
            //conexao.close();
            montadoraDAO.fecharconexao();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            montadoraDAO.fecharconexao();
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    
}
