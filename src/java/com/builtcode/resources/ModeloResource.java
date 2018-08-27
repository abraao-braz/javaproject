/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Modelo;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.ModeloDAO;
import com.builtcode.paginacao.ModeloPaginacao;
import com.builtcode.paginacao.UsuarioPaginacao;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("modelo")
public class ModeloResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ModeloResource
     */
    public ModeloResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.ModeloResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public ModeloPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            ModeloDAO dao = new ModeloDAO();
            ModeloPaginacao a =  new ModeloPaginacao(dao.listar(pagina, tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/(float)tamanho) ), null);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new ModeloPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
      
    }
    
    @Path("montadora/{identificador}")
    @GET
    @Produces("application/json")
    public ModeloPaginacao listarPorMontadora(@PathParam("identificador") String id,@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            ModeloDAO dao = new ModeloDAO();
            ModeloPaginacao a =  new ModeloPaginacao(dao.listarPorMontadora(id,pagina,tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/tamanho) ), null);            
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new ModeloPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
      
    }    
    
    
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Modelo leitura(@PathParam("identificador") String id) {
        try {
            ModeloDAO dao = new ModeloDAO();
            Modelo a = dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Modelo m) {

        try {
            ModeloDAO dao = new ModeloDAO();

            Response a =  Response.ok().entity(dao.novo(m)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response alterar(Modelo m) {
        try {
            ModeloDAO dao = new ModeloDAO();
            Response a = Response.ok().entity(dao.editar(m)).build();
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
            ModeloDAO dao = new ModeloDAO();
            Response a =  Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     


}
