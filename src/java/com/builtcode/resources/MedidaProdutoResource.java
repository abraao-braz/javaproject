/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.MedidaProduto;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.MedidaProdutoDAO;
import com.builtcode.paginacao.MedidaProdutoPaginacao;
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
@Path("medidaproduto")
public class MedidaProdutoResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MedidaProdutoResource
     */
    public MedidaProdutoResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.MedidaProdutoResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public MedidaProdutoPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) throws SQLException {

        MedidaProdutoDAO dao = new MedidaProdutoDAO();
        MedidaProdutoPaginacao a =  new MedidaProdutoPaginacao(dao.listar(pagina, tamanho), new Paginacao(pagina,(int)Math.ceil(dao.total("")/(float)tamanho)) ,null);        
        dao.fecharconexao();
        return a;

    }
    
    @Path("produto/{identificador}")
    @GET
    @Produces("application/json")
    public MedidaProdutoPaginacao listarPorProduto(@PathParam("identificador") String id, @QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            MedidaProdutoDAO dao = new MedidaProdutoDAO();
            MedidaProdutoPaginacao a =  new MedidaProdutoPaginacao(dao.listarPorProduto(id, pagina, tamanho), new Paginacao(pagina,(int)Math.ceil(dao.total(id)/(float)tamanho)) ,null);        
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new MedidaProdutoPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
    }    
    
    @Path("produtounica/{identificador}")
    @GET
    @Produces("application/json")
    public MedidaProdutoPaginacao listarUnicaPorProduto(@PathParam("identificador") String id,  @QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            MedidaProdutoDAO dao = new MedidaProdutoDAO();
            MedidaProdutoPaginacao a =  new MedidaProdutoPaginacao(dao.listarUnicaPorProduto(id, pagina, tamanho), new Paginacao(pagina,(int)Math.ceil(dao.total(id)/(float)tamanho)) ,null);        
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new MedidaProdutoPaginacao(null,null, new Mensagem("E",ex.getMessage()));
        }
    }      
    
    @Path("produtoip7/{identificador}")
    @GET
    @Produces("application/json")
    public List<MedidaProduto> listarIp7PorProduto(@PathParam("identificador") String id) {
        try {
            MedidaProdutoDAO dao = new MedidaProdutoDAO();
            List<MedidaProduto> a =  dao.listarIp7PorProduto(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }      
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public MedidaProduto leitura(@PathParam("identificador") String id) {
        try {
            MedidaProdutoDAO dao = new MedidaProdutoDAO();
            MedidaProduto a =  dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
      
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(MedidaProduto m) {

        try {
            MedidaProdutoDAO dao = new MedidaProdutoDAO();

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
    public Response alterar(MedidaProduto m) {
        try {
            MedidaProdutoDAO dao = new MedidaProdutoDAO();
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
            MedidaProdutoDAO dao = new MedidaProdutoDAO();
            Response a =  Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     


}
