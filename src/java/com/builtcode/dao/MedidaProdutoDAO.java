/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.MedidaProduto;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedidaProdutoDAO {

    Connection conexao;

    public MedidaProdutoDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    

    public int total(String idProduto) throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM medida_produto ";
        
        if(idProduto.compareTo("")!=0){
            sql+=" where idProduto=?";
        }

        try {
            st = conexao.prepareStatement(sql);
            
            if(idProduto.compareTo("")!=0){
                st.setString(1, idProduto);
            }            
            
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
        
    
    public Mensagem novo(MedidaProduto a) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO medida_produto(id, idMedida, idProduto, ip7, carga) ";
        sql += "VALUES (?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());            
            st.setString(2, a.getMedida().getId());
            st.setString(3, a.getProduto().getId());
            st.setString(4, a.getIp7());
            st.setString(5, a.getCarga());

            
            st.executeUpdate();
            
            //conexao.close();
            return new Mensagem("S", "Medida Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(MedidaProduto a) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update medida_produto set idProduto=?, idMedida=?, ip7=?, carga=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            
            st.setString(1, a.getProduto().getId());
            st.setString(2, a.getMedida().getId());
            st.setString(3, a.getIp7());
            st.setString(4, a.getCarga());            
            st.setString(5, a.getId());
                        
            st.executeUpdate();
            
            //conexao.close();
            return new Mensagem("S", "Medida Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from medida_produto where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Medida Excluída com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<MedidaProduto> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<MedidaProduto> dados = new ArrayList<MedidaProduto>();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM medida_produto limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);                 
            
            rs = st.executeQuery();

            while (rs.next()) {
                MedidaProduto m = new MedidaProduto();
                m.setId(rs.getString("id"));
                m.setIp7(rs.getString("ip7"));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                m.setCarga(rs.getString("carga"));
                dados.add(m);
            }
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public List<MedidaProduto> listarPorProduto(String idProduto, int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<MedidaProduto> dados = new ArrayList<MedidaProduto>();
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        

        String sql = "SELECT distinct * FROM medida_produto where idProduto=? limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idProduto);
            st.setInt(2, (pagina-1)*tamanho);
            st.setInt(3, tamanho);     
            
            rs = st.executeQuery();

            while (rs.next()) {
                MedidaProduto m = new MedidaProduto();
                m.setId(rs.getString("id"));
                m.setIp7(rs.getString("ip7"));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                m.setCarga(rs.getString("carga"));
                dados.add(m);
            }
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    public List<MedidaProduto> listarUnicaPorProduto(String idProduto, int pagina, int tamanho ) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<MedidaProduto> dados = new ArrayList<MedidaProduto>();
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        

        String sql = "SELECT distinct idMedida, idProduto FROM medida_produto where idProduto=? limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idProduto);
            st.setInt(2, (pagina-1)*tamanho);
            st.setInt(3, tamanho);                
            rs = st.executeQuery();

            while (rs.next()) {
                MedidaProduto m = new MedidaProduto();

                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));

                dados.add(m);
            }
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }        
    
    
    public List<MedidaProduto> listarIp7PorProduto(String idProduto) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<MedidaProduto> dados = new ArrayList<MedidaProduto>();
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        

        String sql = "SELECT distinct ip7 FROM medida_produto where idProduto=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idProduto);
            rs = st.executeQuery();

            while (rs.next()) {
                MedidaProduto m = new MedidaProduto();
                m.setIp7(rs.getString("ip7"));
                dados.add(m);
            }
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }       
    
    
    public MedidaProduto buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        ProdutoDAO produtoDAO = new ProdutoDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM medida_produto where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                MedidaProduto m = new MedidaProduto();
                m.setId(rs.getString("id"));
                m.setIp7(rs.getString("ip7"));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                m.setCarga(rs.getString("carga"));
                //conexao.close();
                produtoDAO.fecharconexao();
                medidaDAO.fecharconexao();                
                return m;
            }
            else{
                //conexao.close();
                produtoDAO.fecharconexao();
                medidaDAO.fecharconexao();                
                return null;
            }
        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            medidaDAO.fecharconexao();            
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
}
