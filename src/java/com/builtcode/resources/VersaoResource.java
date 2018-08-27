/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Versao;
import com.builtcode.classes.Mensagem;
import com.builtcode.dao.VersaoDAO;
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
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Fabio
 */
@Path("versao")
public class VersaoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of VersaoResource
     */
    public VersaoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.VersaoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<Versao> listar() {
        try {
            VersaoDAO dao = new VersaoDAO();
            List<Versao> a =  dao.listar();
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Versao leitura(@PathParam("identificador") String id) {
        try {
            VersaoDAO dao = new VersaoDAO();
            Versao a = dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    
    
    @Path("modelo/{identificador}")
    @GET
    @Produces("application/json")
    public List<Versao> getByModelo(@PathParam("identificador") String id) {
        try {
            VersaoDAO dao = new VersaoDAO();
            List<Versao> a =  dao.listarPorModelo(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }      
    }      
    
    
    @Path("modelounico/{identificador}")
    @GET
    @Produces("application/json")
    public List<Versao> getByModeloUnico(@PathParam("identificador") String id) {
        try {
            VersaoDAO dao = new VersaoDAO();
            List<Versao> a =  dao.listarUnicaPorModelo(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }      
    }        
     
    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Versao m) {

        try {
            VersaoDAO dao = new VersaoDAO();

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
    public Response alterar(Versao m) {
        try {
            VersaoDAO dao = new VersaoDAO();
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
            VersaoDAO dao = new VersaoDAO();
            Response a =  Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     
}
