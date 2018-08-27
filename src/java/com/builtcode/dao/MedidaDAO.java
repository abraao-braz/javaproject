/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.dao;

import com.builtcode.beans.Aplicacao;
import com.builtcode.beans.Carga;
import com.builtcode.beans.Medida;
import com.builtcode.beans.MedidaProduto;
import com.builtcode.classes.Mensagem;
import com.builtcode.factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedidaDAO {

    Connection conexao;

    public MedidaDAO() throws SQLException {
        conexao = ConexaoFactory.getConnection();
    }
    
    public void fecharconexao() throws SQLException{
        conexao.close();
    }    
    
    public int total() throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;

        String sql = "SELECT count(*) as tot FROM medida ";

        try {
            st = conexao.prepareStatement(sql);
            rs = st.executeQuery();
            rs.next();
            int tot =  rs.getInt("tot");
            st.close();
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
        sql += "INSERT INTO medida(id, descricao) ";
        sql += "VALUES (?,?)";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, java.util.UUID.randomUUID().toString());
            st.setString(2, descricao);
            st.executeUpdate();
            //conexao.close();
            return new Mensagem("S", "Medida Gravada com Sucesso");
        } catch (SQLException e) {
            //conexao.close();
            return new Mensagem("E", e.getMessage());
        }
    }

    public Mensagem editar(Medida m) throws SQLException {
        PreparedStatement st = null;
        String sql = "";
        sql += "update medida set descricao=? where id=? ";
        try {
            st = conexao.prepareStatement(sql);
            st.setString(1, m.getDescricao());
            st.setString(2, m.getId());
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
        sql += "delete from medida where id=? ";
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

    public List<Medida> listar(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Medida> dados = new ArrayList<Medida>();

        String sql = "SELECT * FROM medida order by descricao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);                        
            
            rs = st.executeQuery();

            while (rs.next()) {
                Medida m = new Medida();
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
    
    
    public List<Aplicacao> listarModelo(String modelo, String versao, String ano) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Aplicacao> dados = new ArrayList<Aplicacao>();

        String sql = "SELECT distinct a.idMedida as id, me.descricao, a.homologado from aplicacao a join versao v on a.idMedida = v.idMedida and a.idModelo = v.idModelo " +
                    "join medida me on me.id = a.idMedida " +
                    "where a.idModelo = ? " ;
        
        if(versao.compareTo("undefined")!=0) sql+= "and v.descricao = ? ";
        if(ano.compareTo("undefined")!=0) sql+= "and v.ano = ? ";
        int i=2;
        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, modelo);                     
            
            if(versao.compareTo("undefined")!=0){
                st.setString(i, versao);
                i++;
            }
            
            if(ano.compareTo("undefined")!=0){
                st.setString(i, ano);
            }
            
            
            rs = st.executeQuery();

            while (rs.next()) {
                Medida m = new Medida();
                m.setId(rs.getString("id"));
                m.setDescricao(rs.getString("descricao"));
                
                Aplicacao a = new Aplicacao();
                a.setMedida(m);
                a.setHomologado(rs.getString("homologado"));
                dados.add(a);
            }
            //conexao.close();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            Medida m = new Medida();
            m.setDescricao(e.getMessage());
            Aplicacao a = new Aplicacao();
                a.setMedida(m);
            System.out.println("Erro ao listar");
            dados.add(a);
            return dados;
        }
    }    
    
    
    public List<Carga> listarCargas(String idMedida, String idModelo) throws SQLException{
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Carga> dados = new ArrayList<Carga>();

        String sql = "select distinct carga from medida_produto where idMedida=? ";
        
        
        if(idModelo.compareTo("")!=0&&idModelo.compareTo("null")!=0){
            sql+=" and idProduto in (select idProduto from aplicacao where idModelo = ? ) ";        
        }        
        
        try {

            st = conexao.prepareStatement(sql);
            
            st.setString(1, idMedida);
            
            if(idModelo.compareTo("")!=0&&idModelo.compareTo("null")!=0){                
                st.setString(2, idModelo);
            }
            
            
            
            rs = st.executeQuery();

            while (rs.next()) {
                Carga c = new Carga();
                c.setDescricao(rs.getString("carga"));
                dados.add(c);
            }
            //conexao.close();
            return dados;

        } catch (SQLException e) {
            //conexao.close();
            System.out.println("Erro ao listar:"+e.getMessage());
            return null;
        }
        
    }
    
    
    public List<Medida> listarVinculadas(int pagina, int tamanho) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Medida> dados = new ArrayList<Medida>();

        String sql = "select * from  medida where id in (select idMedida from medida_produto) order by descricao limit ?,?";

        try {

            st = conexao.prepareStatement(sql);
            
            st.setInt(1, (pagina-1)*tamanho);
            st.setInt(2, tamanho);               
            
            
            rs = st.executeQuery();

            while (rs.next()) {
                Medida m = new Medida();
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

    public Medida buscar(String id) throws SQLException {
        PreparedStatement st = null;
        ResultSet rs = null;
        

        String sql = "SELECT * FROM medida where id=?";

        try {

            st = conexao.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Medida m = new Medida();
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
