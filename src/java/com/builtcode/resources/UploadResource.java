/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.builtcode.resources;


import com.builtcode.classes.RetornoUpload;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * REST Web Service
 *
 * @author Fabio
 */
@Path("upload")
public class UploadResource {

    private static final String UPLOAD_FOLDER = "c:/upload_pirelli/";
    //private static final String UPLOAD_FOLDER = "/var/upload_pirelli/";
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AplicacaoResource
     */
    public UploadResource() {
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {
        
        
        // check if all form parameters are provided
        if (uploadedInputStream == null || fileDetail == null) {
            return Response.status(400).entity("Invalid form data").build();
        }
        // create our destination folder, if it not exists
        try {
            createFolderIfNotExists(UPLOAD_FOLDER);
        } catch (SecurityException se) {
            return Response.status(500)
                    .entity("Can not create destination folder on server")
                    .build();
        }
        String nomedoarquivo = java.util.UUID.randomUUID().toString() + fileDetail.getFileName().substring(fileDetail.getFileName().lastIndexOf("."));
        String uploadedFileLocation = UPLOAD_FOLDER + nomedoarquivo;
        try {
            saveToFile(uploadedInputStream, uploadedFileLocation);
        } catch (IOException e) {
            return Response.status(500).entity("Can not save file").build();
        }
        return Response.status(200).entity(new RetornoUpload(nomedoarquivo)).build();
    }
    
    
    @GET
    @Path("{identificador}")
    @Produces("image/png")
    public Response getFile(@PathParam("identificador") String arquivo) {
  
        File file;
        file = new File(UPLOAD_FOLDER+arquivo);
  
        ResponseBuilder response = Response.ok((Object) file);
        //response.header("Content-Disposition", "attachment; filename=\""+arquivo+"\"");
        return response.build();
    }
    
    

    /**
     * Utility method to save InputStream data to target location/file
     *
     * @param inStream - InputStream to be saved
     * @param target - full path to destination file
     */
    private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }

    /**
     * Creates a folder to desired location if it not already exists
     *
     * @param dirName - full path to the folder
     * @throws SecurityException - in case you don't have permission to create
     * the folder
     */
    private void createFolderIfNotExists(String dirName)
            throws SecurityException {
        File theDir = new File(dirName);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }

}
