package org.openblend.fejstbuk.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.openblend.fejstbuk.util.IOUtils;

/**
 * @author Matej Lazar
 */
public class RestfullFileIOImpl implements RestfullFileIO {

    @Override
    public String upload(InputStream input) throws IOException {
        try {
            FileOutputStream out = new FileOutputStream(new File("/tmp/upload_" + System.currentTimeMillis()));
            IOUtils.copy(input, out);

            return "{success: true}";
        } catch (Exception e) {
            return "{success: false}";
        }
    }

    @Override
    public Response download(long id) throws IOException {
        File f = new File("/tmp/upload_" + id);

        if (!f.exists()) {
            return Response.status(Status.NOT_FOUND).entity("File not  found.").build();
        }

        final FileInputStream fis = new FileInputStream(f);

        StreamingOutput responseEntity = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                IOUtils.copy(fis, output);
            }
        };

        //return Response.status(Status.OK).header("content-type", "image/png").entity(responseEntity).build();
        return Response.status(Status.OK).entity(responseEntity).build();
    }

}
