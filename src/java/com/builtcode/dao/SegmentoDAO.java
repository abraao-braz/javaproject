/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Segmento;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SegmentoDAO {

    Connection conexao;

    public SegmentoDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM segmento ";

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
    

    public Mensagem novo(String descricao) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO segmento(id, descricao) ";
        sql += "VALUES (?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, descricao);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Carroceria Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Segmento m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update segmento set descricao=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getDescricao());
            st.setString(2, m.getId());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Carroceria Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from segmento where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Carroceria Excluída com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Segmento> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Segmento> dados = new ArrayList<Segmento>();

        String sql = "SELECT * FROM segmento order by descricao limit ?,? ";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);      
            
            rs = st.executeQuery();

            while (rs.next()) {
                Segmento m = new Segmento();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                dados.add(m);
            }
            //conexao.close();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public Segmento buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        

        String sql = "SELECT * FROM segmento where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Segmento m = new Segmento();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                //conexao.close();
                return m;
            }
            else{
                //conexao.close();
                return null;
            }
        } catch (SQLException e) {
            //conexao.close();
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
}
