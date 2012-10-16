package org.openblend.fejstbuk.ui;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
@Path("/post")
@Stateless

public class RestPostAPI implements Serializable {
    @Inject
    @Current
    protected User user;


    @POST
    @Path("/add-comment")
    @Produces(MediaType.APPLICATION_JSON)
    public String addComment(@QueryParam("post-id") long postId, @QueryParam("comment") String commentText) {
        System.out.println("postId = " + postId);
        System.out.println("commentText = " + commentText);
        return "{success:true}";
    }

}
