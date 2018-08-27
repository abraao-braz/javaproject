/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Usuario;
import com.builtcode.classes.Mensagem;
import com.builtcode.classes.Paginacao;
import com.builtcode.dao.UsuarioDAO;
import com.builtcode.paginacao.UsuarioPaginacao;
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

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * REST Web Service
 *
 * @author Fabio
 */
@Path("usuario")
public class UsuarioResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuarioResource
     */
    public UsuarioResource() {
    }

    /**
     * Retrieves representation of an instance of
     * com.builtcode.resources.UsuarioResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public UsuarioPaginacao listar(@QueryParam("pagina") int pagina,@QueryParam("tamanho") int tamanho) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            UsuarioPaginacao a =  new UsuarioPaginacao(dao.listar(pagina, tamanho),new Paginacao(pagina,(int) Math.ceil(dao.total()/tamanho) ), null);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return new UsuarioPaginacao(null,null, new Mensagem ("E",ex.getMessage()));
        }
      
    }
    
    @Path("{identificador}")
    @GET
    @Produces("application/json")
    public Usuario leitura(@PathParam("identificador") String id) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario a =  dao.buscar(id);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }    

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response novo(Usuario m) {

        try {
            UsuarioDAO dao = new UsuarioDAO();

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
    public Response alterar(Usuario m) {
        try {
            UsuarioDAO dao = new UsuarioDAO();
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
            UsuarioDAO dao = new UsuarioDAO();
            Response a =  Response.ok().entity(dao.excluir(id)).build();
            dao.fecharconexao();
            return a;

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }     
    
    @Path("login")
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response logar(Usuario m) {

        try {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario u = dao.buscarEmail(m.getEmail());
            
            if(u==null){
                Response a = Response.ok().entity(new Mensagem("E","Usuário não encontrado")).build();
                dao.fecharconexao();
                return a;
            }
            if(u.getSenha().compareTo(m.getSenha())!=0){
                Response a = Response.ok().entity(new Mensagem("E","Senha Incorreta")).build();
                dao.fecharconexao();
                return a;
            }
            else{
                Response a = Response.ok().entity(new Mensagem(u.getTipo(),"OK")).build();
                dao.fecharconexao();
                return a;
            }

        } catch (SQLException ex) {
            return Response.ok().entity(new Mensagem("E",ex.getMessage())).build();

        }
    }    
    
   


}
