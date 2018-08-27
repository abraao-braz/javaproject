/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Aplicacao;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.AplicacaoDAO;
import com.builtcode.paginacao.AplicacaoPaginacao;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Fabio
 */
@Path("aplicacao")
public class AplicacaoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AplicacaoResource
     */
    public AplicacaoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.AplicacaoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public AplicacaoPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) throws SQLException {

        AplicacaoDAO dao = new AplicacaoDAO();
        AplicacaoPaginacao a = new AplicacaoPaginacao(dao.listar(pagina, tamanho), new Paginacao(pagina,(int)Math.ceil(dao.total("")/(float)tamanho)) ,null);        
        dao.fecharconexao();
        return a; 

    }
    
    @Path("produto/{identificador}")
    @GET
    @Produces("application/json")
    public AplicacaoPaginacao listarPorProduto(@PathParam("identificador") String id, @QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            AplicacaoPaginacao a = new AplicacaoPaginacao(dao.listarPorProduto(id, pagina, tamanho), new Paginacao(pagina,(int)Math.ceil(dao.total(id)/(float)tamanho)) ,null);        
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new AplicacaoPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
    }    
    
    @Path("anos/{modelo}/{versao}")
    @GET
    @Produces("application/json")
    public List<Aplicacao> listarAnosPorVersao(@PathParam("modelo") String modelo, @PathParam("versao") String versao) {
        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            List<Aplicacao> a = dao.listarAnosPorVersao(modelo, versao);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }      
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Aplicacao leitura(@PathParam("identificador") String id) {
        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            Aplicacao a = dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Aplicacao m) {

        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            Response a = Response.ok().entity(dao.novo(m)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response alterar(Aplicacao m) {
        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            Response a =  Response.ok().entity(dao.editar(m)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }    
    
    @Path("{identificador}")
    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public Response excluir(@PathParam("identificador") String id) {
        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            Response a  =  Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     
    
    @Path("homologado/{produto}/{medida}/{modelo}")
    @GET
    @Produces("application/json")
    public String getHomologado(@PathParam("produto") String produto, @QueryParam("medida") String medida, @QueryParam("modelo") String modelo) {
        try {
            AplicacaoDAO dao = new AplicacaoDAO();
            String a = dao.getHomologado(produto, medida, modelo); 
            dao.fecharconexao();
            return a;            
        } catch (SQLException ex) {
            return "ERRO";
        }
    }     
    
   


}
