package org.openblend.fejstbuk.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Generic entity.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private Long id;

    public AbstractEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
