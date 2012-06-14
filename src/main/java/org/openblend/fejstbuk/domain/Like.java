package org.openblend.fejstbuk.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Entity
public class Like extends Owned {
    private Post post;

    @ManyToOne
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
