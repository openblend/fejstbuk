package org.openblend.fejstbuk.ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;
import org.openblend.fejstbuk.util.IOUtils;

/**
 * @author Matej Lazar
 */
public class RestfullAvatarFileIOImpl implements RestfullAvatarFileIO {

    @Inject
    @Current
    User user;

    @Inject
    protected EntityManager em;

    @Override
    public String upload(InputStream input) throws IOException {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            IOUtils.copy(input, out);
            user.setImage(out.toByteArray());
            return "{success: true}";
        } catch (Exception e) {
            return "{success: false}";
        }
    }

    @Override
    public Response download(long uid) throws IOException {

//        if (user == null) {
//            return Response.status(Status.NOT_FOUND).entity("User is not logged in.").build();
//        }

        em.find(User.class, uid);

        byte[] image = user.getImage();

        if (image == null || image.length < 1) {
            return Response.status(Status.NOT_FOUND).entity("User has no image.").build();
        }

        final ByteArrayInputStream in = new ByteArrayInputStream(image);

        StreamingOutput responseEntity = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                IOUtils.copy(in, output);
            }
        };

        //return Response.status(Status.OK).header("content-type", "image/png").entity(responseEntity).build();
        return Response.status(Status.OK).entity(responseEntity).build();
    }

}
