/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import com.builtcode.beans.Aplicacao;
import com.builtcode.beans.Medida;
import com.builtcode.beans.MedidaProduto;
import com.builtcode.beans.Produto;
import com.builtcode.dao.MedidaDAO;
import com.builtcode.dao.ProdutoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author Fabio
 */
@Path("busca")
public class BuscaResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ProdutoResource
     */
    public BuscaResource() {
    }


    //Busca os produtos pelas medidas
    @Path("{identificador}/{identificador2}/{identificador3}")
    @GET
    @Produces("application/json")
    public List<Produto> listarPorMedida(@PathParam("identificador") String id, @PathParam("identificador2") String carga, @PathParam("identificador3") String modelo) {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            List<Produto> a =  dao.listarMedida(id,carga, modelo);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }      
    
    @Path("medida/{i1}/{i2}/{i3}")
    @GET
    @Produces("application/json")
    public List<Aplicacao> listarPorModelo(@PathParam("i1") String i1, @PathParam("i2") String i2, @PathParam("i3") String i3) {
        try {
            MedidaDAO dao = new MedidaDAO();
            List<Aplicacao> a =  dao.listarModelo(i1, i2, i3);
            dao.fecharconexao();
            return a;
        } catch (SQLException ex) {
            return null;
        }
    }       
    
    
    
    //Buscar os modelos pelas marcas
 


}
