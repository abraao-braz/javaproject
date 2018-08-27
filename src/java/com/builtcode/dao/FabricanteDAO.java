/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Fabricante;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FabricanteDAO {

    Connection conexao;

    public FabricanteDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM fabricante ";

        try {
            st = conexao.prepareStatement(sql);
            rs = st.executeQuery();
            rs.next();
            int tot = rs.getInt("tot");
            //conexao.close();
            return tot;
        } catch (SQLException e) {
            //conexao.close();
            return 0;
        } 
    }     

    public Mensagem novo(Fabricante p) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO fabricante(id, nome, imagem) ";
        sql += "VALUES (?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, p.getNome());
            st.setString(3, p.getImagem());
            
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Concorrente Gravado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Fabricante m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update fabricante set nome=?, imagem=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getNome());  
            st.setString(2, m.getImagem());               
            st.setString(3, m.getId());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Concorrente Gravado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from fabricante where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Concorrente Excluído com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Fabricante> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Fabricante> dados = new ArrayList<Fabricante>();
        MedidaDAO medidaDAO = new MedidaDAO();
        String sql = "SELECT * FROM fabricante order by nome limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);                
            
            rs = st.executeQuery();

            while (rs.next()) {
                Fabricante m = new Fabricante();
                m.setId(rs.getString("id"));
                m.setNome(rs.getString("nome"));
                m.setImagem(rs.getString("imagem"));
                dados.add(m);
            }
            //conexao.close();
            medidaDAO.fecharconexao();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            medidaDAO.fecharconexao();
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public Fabricante buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM fabricante where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Fabricante m = new Fabricante();
                m.setId(rs.getString("id"));
                m.setNome(rs.getString("nome"));
                m.setImagem(rs.getString("imagem"));
                
                //conexao.close();
                medidaDAO.fecharconexao();
                return m;
            }
            else{
                //conexao.close();
                medidaDAO.fecharconexao();
                return null;
            }
        } catch (SQLException e) {
            //conexao.close();
            medidaDAO.fecharconexao();
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    

    
    
}
