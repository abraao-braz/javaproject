/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Montadora;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MontadoraDAO {

    Connection conexao;

    public MontadoraDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }

    public void fecharconexao() throws SQLException{
        conexao.close();
    }    
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM montadora ";

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
        sql += "INSERT INTO montadora(id, descricao) ";
        sql += "VALUES (?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, descricao);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Montadora Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Montadora m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update montadora set descricao=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getDescricao());
            st.setString(2, m.getId());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Montadora Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from montadora where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Montadora Excluída com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Montadora> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Montadora> dados = new ArrayList<Montadora>();

        String sql = "SELECT * FROM montadora order by descricao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);            
            
            rs = st.executeQuery();

            while (rs.next()) {
                Montadora m = new Montadora();
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

    public Montadora buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        

        String sql = "SELECT * FROM montadora where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Montadora m = new Montadora();
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
