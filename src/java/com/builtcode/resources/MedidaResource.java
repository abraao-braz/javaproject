/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Carga;
import com.builtcode.beans.Medida;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.MedidaDAO;
import com.builtcode.paginacao.CarroceriaPaginacao;
import com.builtcode.paginacao.MedidaPaginacao;
import com.builtcode.paginacao.ModeloPaginacao;
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
@Path("medida")
public class MedidaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MedidaResource
     */
    public MedidaResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.MedidaResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public MedidaPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            MedidaDAO dao = new MedidaDAO();
            MedidaPaginacao a = new MedidaPaginacao(dao.listar(pagina, tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/(float)tamanho) ), null);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new MedidaPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
    }
    
    @Path("/vinculadas")
    @GET
    @Produces("application/json")
    public MedidaPaginacao listarVinculadas(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            MedidaDAO dao = new MedidaDAO();
            MedidaPaginacao a =  new MedidaPaginacao(dao.listarVinculadas(pagina, tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/(float)tamanho) ), null);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new MedidaPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
    }    
    
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Medida leitura(@PathParam("identificador") String id) {
        try {
            MedidaDAO dao = new MedidaDAO();
            Medida a =  dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @Path("cargas/{identificador}")
    @GET
    @Produces("application/json")
    public List<Carga> leituraCargas(@PathParam("identificador") String id) {
        try {
            MedidaDAO dao = new MedidaDAO();
            List<Carga> a =  dao.listarCargas(id, "");
            dao.fecharconexao();
            return a;
            //return "{'Fabio':'true'}";
        } catch (SQLException ex) {
            return null;
        }      
    }       
    
    @Path("cargasmodelo/{identificador}/{identificador2}")
    @GET
    @Produces("application/json")
    public List<Carga> leituraCargasModelo(@PathParam("identificador") String id, @PathParam("identificador2") String modelo) {
        try {
            MedidaDAO dao = new MedidaDAO();
            List<Carga> a =  dao.listarCargas(id, modelo);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }      
    }    
    
    
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Medida m) {

        try {
            MedidaDAO dao = new MedidaDAO();

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
    public Response alterar(Medida m) {
        try {
            MedidaDAO dao = new MedidaDAO();
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
            MedidaDAO dao = new MedidaDAO();
            Response a = Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     


}
