/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Aplicacao;
import com.builtcode.beans.Medida;
import com.builtcode.beans.Modelo;
import com.builtcode.beans.Montadora;
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

public class AplicacaoDAO {

    Connection conexao;

    public AplicacaoDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    
    
    public int total(String idProduto) throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM aplicacao ";
        
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
            int tot = rs.getInt("tot");
            //conexao.close();
            return tot;
        } catch (SQLException e) {
            //conexao.close();
            return 0;
        } 
    }     

    public Mensagem novo(Aplicacao a) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO aplicacao(id, idProduto, idModelo, idMedida, carga, ip7, homologado, observacoes) ";
        sql += "VALUES (?,?,?,?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, a.getProduto().getId());
            st.setString(3, a.getModelo().getId());
            st.setString(4, a.getMedida().getId());
            st.setString(5, a.getCarga());
            st.setString(6, a.getIp7());
            st.setString(7, a.getHomologado());
            st.setString(8, a.getObservacoes());
            
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Aplicação Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Aplicacao a) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update aplicacao set idProduto=?, idModelo=?, idMedida=?, carga=?, ip7=?, homologado=?, observacoes=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            
            st.setString(1, a.getProduto().getId());
            st.setString(2, a.getModelo().getId());
            st.setString(3, a.getMedida().getId());
            st.setString(4, a.getCarga());
            st.setString(5, a.getIp7());
            st.setString(6, a.getHomologado());
            st.setString(7, a.getObservacoes());
            st.setString(8, a.getId());
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Aplicação Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from aplicacao where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Aplicação Excluída com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Aplicacao> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Aplicacao> dados = new ArrayList<Aplicacao>();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        VersaoDAO versaoDAO = new VersaoDAO();
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM aplicacao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);            
            
            rs = st.executeQuery();

            while (rs.next()) {
                Aplicacao m = new Aplicacao();
                m.setId(rs.getString("id"));
                m.setAno(rs.getInt("ano"));
                m.setHomologado(rs.getString("homologado"));
                m.setLocalizacao(rs.getString("localizacao"));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                m.setVersao(versaoDAO.buscar(rs.getString("idVersao")));
                m.setModelo(modeloDAO.buscar(rs.getString("idModelo")));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                m.setObservacoes(rs.getString("observacoes"));
                m.setIp7(rs.getString("ip7"));
                m.setCarga(rs.getString("carga"));                
                m.setVelocidade(rs.getString("velocidade"));
                dados.add(m);
            }
            //conexao.close();
            
            produtoDAO.fecharconexao();
            versaoDAO.fecharconexao();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();           
            
            return dados;

        } catch (SQLException e) {
            produtoDAO.fecharconexao();
            versaoDAO.fecharconexao();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();  
            
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public List<Aplicacao> listarPorProduto(String idProduto,int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Aplicacao> dados = new ArrayList<Aplicacao>();
        
        
        
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        VersaoDAO versaoDAO = new VersaoDAO();
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();
        

        String sql = "SELECT a.*, m.descricao as descricao_modelo, mon.descricao as descricao_montadora FROM aplicacao a join modelo m on a.idModelo = m.id join montadora mon on mon.id = m.idMontadora where idProduto=? limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idProduto);
            st.setInt(2, (pagina-1)*tamanho);
            st.setInt(3, tamanho);  
            
            rs = st.executeQuery();

            while (rs.next()) {
                Aplicacao m = new Aplicacao();
                m.setId(rs.getString("id"));
                m.setAno(rs.getInt("ano"));
                m.setHomologado(rs.getString("homologado"));
                m.setLocalizacao(rs.getString("localizacao"));
                //m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                //m.setVersao(versaoDAO.buscar(rs.getString("idVersao")));
                
                Modelo modelo = new Modelo();
                modelo.setId(rs.getString("idModelo"));
                modelo.setDescricao(rs.getString("descricao_modelo"));
                
                Montadora montadora = new Montadora();
                montadora.setDescricao(rs.getString("descricao_montadora"));
                
                modelo.setMontadora(montadora);
                
                m.setModelo(modelo);
                
                
                //m.setModelo(modeloDAO.buscar(rs.getString("idModelo")));
                
                
                Medida medida = new Medida();
                medida.setId(rs.getString("idMedida"));                
                m.setMedida(medida);
                
                m.setObservacoes(rs.getString("observacoes")); //OB
                m.setIp7(rs.getString("ip7"));  //Obrigatório
                m.setCarga(rs.getString("carga"));                
                m.setVelocidade(rs.getString("velocidade"));
                dados.add(m);
            }
            //conexao.close();
            produtoDAO.fecharconexao();
            versaoDAO.fecharconexao();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            versaoDAO.fecharconexao();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    public List<Aplicacao> listarAnosPorVersao(String idModelo, String versao) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Aplicacao> dados = new ArrayList<Aplicacao>();     

        String sql = "select distinct ano from versao where idModelo = ? and descricao = ?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idModelo);
            st.setString(2, versao);
            rs = st.executeQuery();

            while (rs.next()) {
                Aplicacao m = new Aplicacao();
                m.setAno(rs.getInt("ano"));
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
    
    public String getHomologado(String produto, String medida, String modelo) throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM aplicacao where idProduto=? and idmedida=? and idModelo=? and homologado = 'SIM' ";  
        
        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, produto);
            st.setString(2, medida);
            st.setString(3, modelo);
            
            rs = st.executeQuery();

            if (rs.next()) {
                //conexao.close();
                return "SIM";
            }
            else{
                //conexao.close();
                return "NÃO";
            }
        } catch (SQLException e) {
            //conexao.close();
            return "ERRO";
        }        
    }
    
    
    public Aplicacao buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        ProdutoDAO produtoDAO = new ProdutoDAO();
        VersaoDAO versaoDAO = new VersaoDAO();
        ModeloDAO modeloDAO = new ModeloDAO();
        MedidaDAO medidaDAO = new MedidaDAO();
        

        String sql = "SELECT * FROM aplicacao where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Aplicacao m = new Aplicacao();
                m.setId(rs.getString("id"));
                m.setAno(rs.getInt("ano"));
                m.setHomologado(rs.getString("homologado"));
                m.setLocalizacao(rs.getString("localizacao"));
                m.setProduto(produtoDAO.buscar(rs.getString("idProduto")));
                m.setVersao(versaoDAO.buscar(rs.getString("idVersao")));
                m.setModelo(modeloDAO.buscar(rs.getString("idModelo")));
                m.setMedida(medidaDAO.buscar(rs.getString("idMedida")));
                m.setObservacoes(rs.getString("observacoes"));
                m.setIp7(rs.getString("ip7"));
                m.setCarga(rs.getString("carga"));                
                m.setVelocidade(rs.getString("velocidade"));
                //conexao.close();
                
                produtoDAO.fecharconexao();
                versaoDAO.fecharconexao();
                modeloDAO.fecharconexao();
                medidaDAO.fecharconexao();  
            
                return m;
            }
            else return null;
        } catch (SQLException e) {
            //conexao.close();
            produtoDAO.fecharconexao();
            versaoDAO.fecharconexao();
            modeloDAO.fecharconexao();
            medidaDAO.fecharconexao();              
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
}
