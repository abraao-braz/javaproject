/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Produto;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.ProdutoDAO;
import com.builtcode.paginacao.ProdutoPaginacao;
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
@Path("produto")
public class ProdutoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProdutoResource
     */
    public ProdutoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.ProdutoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public ProdutoPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            ProdutoPaginacao a =  new ProdutoPaginacao(dao.listar(pagina, tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/(float)tamanho) ), null);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new ProdutoPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
      
    }
    
    @Path("produto/buscamedida/{identificador}/{carga}")
    @GET
    @Produces("application/json")
    public List<Produto> listarPorMedida(@PathParam("identificador") String id,@PathParam("carga") String carga) {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            List<Produto> a =  dao.listarMedida(id, carga, "");
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }      
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Produto leitura(@PathParam("identificador") String id) {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            Produto a = dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Produto m) {
        try {
            ProdutoDAO dao = new ProdutoDAO();

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
    public Response alterar(Produto m) {
        try {
            ProdutoDAO dao = new ProdutoDAO();
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
            ProdutoDAO dao = new ProdutoDAO();
            Response a = Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     


}
