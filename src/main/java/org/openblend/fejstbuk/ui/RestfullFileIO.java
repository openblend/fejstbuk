package org.openblend.fejstbuk.ui;

import java.io.IOException;
import java.io.InputStream;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author Matej Lazar
 */
@Path("/file")
@Local
public interface RestfullFileIO {

    @POST
    @Path("/upload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.APPLICATION_JSON)
    String upload(InputStream input) throws IOException;

    @GET
    @Path("/download")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    byte[] download(@QueryParam("id") long id) throws IOException;

}
