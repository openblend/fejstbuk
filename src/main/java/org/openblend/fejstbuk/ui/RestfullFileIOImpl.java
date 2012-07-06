package org.openblend.fejstbuk.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
    public byte[] download(long id) throws IOException {
        File f = new File("/tmp/upload_" + id);
        FileInputStream fis = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IOUtils.copy(fis, out);

        return out.toByteArray();
    }

}
