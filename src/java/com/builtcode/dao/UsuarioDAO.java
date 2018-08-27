/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Usuario;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    Connection conexao;

    public UsuarioDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    

    public Mensagem novo(Usuario u) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO usuario(id, email, senha, nome, tipo) ";
        sql += "VALUES (?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, u.getEmail());
            st.setString(3, u.getSenha());
            st.setString(4, u.getNome());
            st.setString(5, u.getTipo());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Usuário Gravado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Usuario u) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update usuario set email=?, senha=?, nome=?, tipo=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, u.getEmail());
            st.setString(2, u.getSenha());
            st.setString(3, u.getNome());
            st.setString(4, u.getTipo());
            
            st.setString(5, u.getId());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Usuário Gravado com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from usuario where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Usuário Excluído com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM usuario";

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

    public List<Usuario> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Usuario> dados = new ArrayList<Usuario>();

        String sql = "SELECT * FROM usuario limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);
            
            rs = st.executeQuery();

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getString("id"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setNome(rs.getString("nome"));
                u.setTipo(rs.getString("tipo"));
                dados.add(u);
            }
            //conexao.close();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public Usuario buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        

        String sql = "SELECT * FROM usuario where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getString("id"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setNome(rs.getString("nome"));
                u.setTipo(rs.getString("tipo"));
                //conexao.close();
                return u;
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
    
    public Usuario buscarEmail(String email) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        

        String sql = "SELECT * FROM usuario where email=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, email);
            rs = st.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getString("id"));
                u.setEmail(rs.getString("email"));
                u.setSenha(rs.getString("senha"));
                u.setNome(rs.getString("nome"));
                u.setTipo(rs.getString("tipo"));
                //conexao.close();
                return u;
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
