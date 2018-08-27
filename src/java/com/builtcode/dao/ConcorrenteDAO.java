/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Aplicacao;
import com.builtcode.beans.Concorrente;
import com.builtcode.beans.Produto;
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

public class ConcorrenteDAO {

    Connection conexao;

    public ConcorrenteDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    

    public Mensagem novo(Concorrente a) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO concorrente(id, idProduto, idFabricante, produto, observacoes) ";
        sql += "VALUES (?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());            
            st.setString(2, a.getProduto().getId());
            st.setString(3, a.getFabricante().getId());
            st.setString(4, a.getProdutoconcorrente());
            st.setString(5, a.getObservacoes());

            
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Concorrente Gravado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Concorrente a) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update concorrente set idProduto=?, idFabricante=?, produto=?, observacoes=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            
            st.setString(1, a.getProduto().getId());
            st.setString(2, a.getFabricante().getId());
            st.setString(3, a.getProdutoconcorrente());
            st.setString(4, a.getObservacoes());
            st.setString(5, a.getId());
                        
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
        sql += "delete from concorrente where id=? ";
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

    public List<Concorrente> listar() throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Concorrente> dados = new ArrayList<Concorrente>();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        FabricanteDAO fabricanteDAO = new FabricanteDAO();

        String sql = "SELECT * FROM concorrente ";

        try {

            st = conexao.prepareStatement(sql);
            
            rs = st.executeQuery();

            while (rs.next()) {
                Concorrente m = new Concorrente();
                m.setFabricante(fabricanteDAO.buscar(rs.getString("idFabricante")));
                m.setId(rs.getString("id"));
                m.setObservacoes("observacoes");
                m.setProdutoconcorrente("produto");
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                dados.add(m);
            }
            //conexao.close();
            
            produtoDAO.fecharconexao();
            fabricanteDAO.fecharconexao();
        
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            fabricanteDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public List<Concorrente> listarPorProduto(String idProduto) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Concorrente> dados = new ArrayList<Concorrente>();
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        FabricanteDAO fabricanteDAO = new FabricanteDAO();
        

        String sql = "SELECT * FROM concorrente where idProduto=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idProduto);
            rs = st.executeQuery();

            while (rs.next()) {
                Concorrente m = new Concorrente();
                m.setFabricante(fabricanteDAO.buscar(rs.getString("idFabricante")));
                m.setId(rs.getString("id"));
                m.setObservacoes(rs.getString("observacoes"));
                m.setProdutoconcorrente(rs.getString("produto"));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                dados.add(m);
            }
            //conexao.close();
            produtoDAO.fecharconexao();
            fabricanteDAO.fecharconexao();            
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            fabricanteDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    
    public Concorrente buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        ProdutoDAO produtoDAO = new ProdutoDAO();
        FabricanteDAO fabricanteDAO = new FabricanteDAO();
        String sql = "SELECT * FROM concorrente where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Concorrente m = new Concorrente();
                m.setFabricante(fabricanteDAO.buscar(rs.getString("idFabricante")));
                m.setId(rs.getString("id"));
                m.setObservacoes(rs.getString("observacoes"));
                m.setProdutoconcorrente(rs.getString("produto"));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                //conexao.close();
                produtoDAO.fecharconexao();
                fabricanteDAO.fecharconexao();                
                return m;
            }
            else{
                //conexao.close();
                produtoDAO.fecharconexao();
                fabricanteDAO.fecharconexao();                
                return null;
            }
        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            fabricanteDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
}
