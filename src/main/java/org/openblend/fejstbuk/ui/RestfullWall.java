package org.openblend.fejstbuk.ui;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.openblend.fejstbuk.domain.Owned;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Path("/wall")
@Local
public interface RestfullWall {
    @POST
    @Path("/activity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    List<Owned> activity() throws IOException;
}
