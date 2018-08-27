/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Produto;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    Connection conexao;

    public ProdutoDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM produto ";

        try {
            st = conexao.prepareStatement(sql);
            rs = st.executeQuery();
            rs.next();
            int tot =  rs.getInt("tot");
            ////conexao.close();
            return tot;
        } catch (SQLException e) {
            ////conexao.close();
            return 0;
        } 
    }     

    public Mensagem novo(Produto p) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "INSERT INTO produto(id, descricao,  caracteristica1,caracteristica2,caracteristica3,caracteristica4, imagem, cat1) ";
        sql += "VALUES (?,?,?,?,?,?,?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, p.getDescricao());
            st.setString(3, p.getCaracteristica1());
            st.setString(4, p.getCaracteristica2());
            st.setString(5, p.getCaracteristica3());
            st.setString(6, p.getCaracteristica4());
            st.setString(7, p.getImagem());
            st.setString(8, p.getCat1());
            
            st.executeUpdate();
            ////conexao.close();
            return new Mensagem("S", "Produto Gravado com Sucesso");
        } catch (SQLException e) {
            ////conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Produto m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update produto set descricao=?, caracteristica1=?, caracteristica2=?, caracteristica3=?,caracteristica4=?, imagem=?, cat1=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getDescricao());
            st.setString(2, m.getCaracteristica1());  
            st.setString(3, m.getCaracteristica2()); 
            st.setString(4, m.getCaracteristica3()); 
            st.setString(5, m.getCaracteristica4());   
            st.setString(6, m.getImagem());   
            st.setString(7, m.getCat1()); 
            
            st.setString(8, m.getId());
            st.executeUpdate();
            ////conexao.close();
            return new Mensagem("S", "Produto Gravado com Sucesso");
        } catch (SQLException e) {
            ////conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem excluir(String s) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "delete from produto where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
            ////conexao.close();
            return new Mensagem("S", "Produto Excluído com Sucesso");
        } catch (SQLException e) {
            ////conexao.close();
            return new Mensagem("E", "Deu erro");
        }
    }

    public List<Produto> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produto> dados = new ArrayList<Produto>();
        MedidaDAO medidaDAO = new MedidaDAO();
        String sql = "SELECT * FROM produto order by descricao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);                
            
            rs = st.executeQuery();

            while (rs.next()) {
                Produto m = new Produto();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCaracteristica1(rs.getString("caracteristica1"));
                m.setCaracteristica2(rs.getString("caracteristica2"));
                m.setCaracteristica3(rs.getString("caracteristica3"));
                m.setCaracteristica4(rs.getString("caracteristica4"));
                m.setImagem(rs.getString("imagem"));
                m.setCat1(rs.getString("cat1"));
                dados.add(m);
            }
            ////conexao.close();
            medidaDAO.fecharconexao();
            return dados;

        } catch (SQLException e) {
            ////conexao.close();
            medidaDAO.fecharconexao();
            System.out.println("Erro ao listar");
            return null;
        }
    }

    public Produto buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        MedidaDAO medidaDAO = new MedidaDAO();

        String sql = "SELECT * FROM produto where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Produto m = new Produto();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCaracteristica1(rs.getString("caracteristica1"));
                m.setCaracteristica2(rs.getString("caracteristica2"));
                m.setCaracteristica3(rs.getString("caracteristica3"));
                m.setCaracteristica4(rs.getString("caracteristica4"));
                m.setImagem(rs.getString("imagem"));
                m.setCat1(rs.getString("cat1"));
                
                ////conexao.close();
                medidaDAO.fecharconexao();
                return m;
            }
            else{
                ////conexao.close();
                medidaDAO.fecharconexao();
                return null;
            }
        } catch (SQLException e) {
            ////conexao.close();
            medidaDAO.fecharconexao();
            System.out.println("Erro ao listar");
            return null;
        }
    }    
    
    
    public List<Produto> listarMedida(String id, String carga, String modelo) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Produto> dados = new ArrayList<Produto>();
        MedidaDAO medidaDAO = new MedidaDAO();
        
        
        String trecho = "";
        if(carga.compareTo("null")!=0) trecho = " and carga = ? "; 
        String sql = "select * ";
        
        if(modelo.compareTo("")!=0&&modelo.compareTo("null")!=0){
            sql+=",(select max(homologado) from aplicacao where idMedida=? and idModelo=? and idProduto=produto.id ) as homologado ";
        }
        else{
            sql+=",'NÃO' as homologado ";
        }
        
        sql+= " from produto where id in (select idProduto from medida_produto where idMedida = ? "+trecho+" ) ";
        
        if(modelo.compareTo("")!=0&&modelo.compareTo("null")!=0){
            sql+=" and id in (select idProduto from aplicacao where idModelo=?) ";
        }        
        
        
        sql+=" order by descricao";
        
        try {

            st = conexao.prepareStatement(sql);
            if(modelo.compareTo("")!=0&&modelo.compareTo("null")!=0){
                if(carga.compareTo("")!=0&&carga.compareTo("null")!=0){
                    st.setString(1, id);
                    st.setString(2, modelo);
                    st.setString(3, id);
                    st.setString(4, carga);  
                    st.setString(5, modelo);
                }
                else{
                    st.setString(1, id);
                    st.setString(2, modelo);
                    st.setString(3, id);
                    st.setString(4, modelo);
                    
                }
            }
            else{
                st.setString(1, id);   
                if(carga.compareTo("null")!=0) st.setString(2, carga);
            }
            
            
          
            
            rs = st.executeQuery();

            while (rs.next()) {
                Produto m = new Produto();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCaracteristica1(rs.getString("caracteristica1"));
                m.setCaracteristica2(rs.getString("caracteristica2"));
                m.setCaracteristica3(rs.getString("caracteristica3"));
                m.setCaracteristica4(rs.getString("caracteristica4"));
                m.setImagem(rs.getString("imagem"));
                m.setCat1(rs.getString("cat1"));
                m.setHomologado(rs.getString("homologado"));
                dados.add(m);
            }
            ////conexao.close();
            medidaDAO.fecharconexao();
            return dados;

            
        } catch (SQLException e) {
            ////conexao.close();
            medidaDAO.fecharconexao();
            System.out.println("Erro ao listar: "+e.getMessage());
            return null;
        }
    }    
    
    public List<Produto> listarVeiculo(String idMarca, String idModelo, String versao, String ano) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        int param = 3;
        List<Produto> dados = new ArrayList<Produto>();
        String sql = "select distinct p.* from aplicacao a\n" +
                     "join produto p on a.idProduto = p.id\n" +
                     "join versao v on a.idVersao = v.id\n" +
                     "join modelo m on v.idModelo = m.id\n" +
                     "join montadora mo on m.idMontadora = mo.id";
        sql+= " where mo.id=? and m.id=?";
        
        if(versao.compareTo("undefined")!=0) sql+=" and v.id=?";
        if(ano.compareTo("undefined")!=0) sql+=" and a.ano=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, idMarca);
            st.setString(2, idModelo);
            
            if(versao.compareTo("undefined")!=0){
                st.setString(param, versao);
                param++;
            }
            
            if(ano.compareTo("undefined")!=0){
                st.setString(param, ano);
                param++;
            }                       

            rs = st.executeQuery();

            while (rs.next()) {
                Produto m = new Produto();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCaracteristica1(rs.getString("caracteristica1"));
                m.setCaracteristica2(rs.getString("caracteristica2"));
                m.setCaracteristica3(rs.getString("caracteristica3"));
                m.setCaracteristica4(rs.getString("caracteristica4"));
                m.setImagem(rs.getString("imagem"));
                m.setCat1(rs.getString("cat1"));
                dados.add(m);
            }
            ////conexao.close();
            return dados;

        } catch (SQLException e) {
            System.out.println("Erro ao listar");
            ////conexao.close();
            return null;
        }
    }       
    
    public Produto buscarMedida(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "select * from produto where id in (select idProduto from medida_produto where idMedida = ?)";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Produto m = new Produto();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                m.setCaracteristica1(rs.getString("caracteristica1"));
                m.setCaracteristica2(rs.getString("caracteristica2"));
                m.setCaracteristica3(rs.getString("caracteristica3"));
                m.setCaracteristica4(rs.getString("caracteristica4"));
                m.setImagem(rs.getString("imagem"));
                m.setCat1(rs.getString("cat1"));
                ////conexao.close();
                return m;
            }
            else{
                ////conexao.close();
                return null;
            }
        } catch (SQLException e) {
            ////conexao.close();
            System.out.println("Erro ao listar");
            return null;
        }
    }        
    
}
