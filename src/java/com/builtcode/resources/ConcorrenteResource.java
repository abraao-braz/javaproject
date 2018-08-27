/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Concorrente;
import com.builtcode.beans.Concorrente;
import com.builtcode.classes.Mensagem;
import com.builtcode.dao.ConcorrenteDAO;
import com.builtcode.dao.ConcorrenteDAO;
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
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Fabio
 */
@Path("concorrente")
public class ConcorrenteResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ConcorrenteResource
     */
    public ConcorrenteResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.ConcorrenteResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<Concorrente> listar() throws SQLException {

        ConcorrenteDAO dao = new ConcorrenteDAO();
        List<Concorrente> a = dao.listar();
        dao.fecharconexao();
        return a;

    }
    
    @Path("produto/{identificador}")
    @GET
    @Produces("application/json")
    public List<Concorrente> listarPorProduto(@PathParam("identificador") String id) {
        try {
            ConcorrenteDAO dao = new ConcorrenteDAO();
            List<Concorrente> a = dao.listarPorProduto(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }    
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Concorrente leitura(@PathParam("identificador") String id) {
        try {
            ConcorrenteDAO dao = new ConcorrenteDAO();
            Concorrente a =  dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Concorrente m) {

        try {
            ConcorrenteDAO dao = new ConcorrenteDAO();

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
    public Response alterar(Concorrente m) {
        try {
            ConcorrenteDAO dao = new ConcorrenteDAO();
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
            ConcorrenteDAO dao = new ConcorrenteDAO();
            Response a =  Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     


}
