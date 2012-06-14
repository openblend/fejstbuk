package org.openblend.fejstbuk.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Entity
public abstract class Linked extends Post {
    private Set<Comment> comments;

    @ManyToMany
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
