package org.openblend.fejstbuk.domain;

import java.sql.Blob;

import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Entity
public class Image extends Linked {
    private Blob image;

    @Lob
    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
