/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Segmento;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.SegmentoDAO;
import com.builtcode.paginacao.SegmentoPaginacao;
import java.sql.SQLException;
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
@Path("segmento")
public class SegmentoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of SegmentoResource
     */
    public SegmentoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.SegmentoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public SegmentoPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            SegmentoDAO dao = new SegmentoDAO();
            SegmentoPaginacao a =  new SegmentoPaginacao(dao.listar(pagina, tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/(float)tamanho) ), null);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new SegmentoPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
      
    }
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Segmento leitura(@PathParam("identificador") String id) {
        try {
            SegmentoDAO dao = new SegmentoDAO();
            Segmento a =  dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Segmento m) {

        try {
            SegmentoDAO dao = new SegmentoDAO();
            
            Response a =  Response.ok().entity(dao.novo(m.getDescricao())).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }
    
    @PUT
    @Consumes("application/json")
    @Produces("application/json")
    public Response alterar(Segmento m) {
        try {
            SegmentoDAO dao = new SegmentoDAO();
            
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
            SegmentoDAO dao = new SegmentoDAO();
            Response a = Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     


}
