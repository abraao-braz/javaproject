/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author Fabio
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);
        //resources.add(MultiPartResource.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.builtcode.filters.CORSFilter.class);
        resources.add(com.builtcode.resources.AplicacaoResource.class);
        resources.add(com.builtcode.resources.BuscaResource.class);
        resources.add(com.builtcode.resources.CarroceriaResource.class);
        resources.add(com.builtcode.resources.ConcorrenteResource.class);
        resources.add(com.builtcode.resources.FabricanteResource.class);
        resources.add(com.builtcode.resources.MedidaProdutoResource.class);
        resources.add(com.builtcode.resources.MedidaResource.class);
        resources.add(com.builtcode.resources.ModeloResource.class);
        resources.add(com.builtcode.resources.MontadoraResource.class);
        resources.add(com.builtcode.resources.ProdutoResource.class);
        resources.add(com.builtcode.resources.SegmentoResource.class);
        resources.add(com.builtcode.resources.UploadResource.class);
        resources.add(com.builtcode.resources.UsuarioResource.class);
        resources.add(com.builtcode.resources.VersaoResource.class);
        
    }
    
}
