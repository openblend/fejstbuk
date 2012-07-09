package org.openblend.fejstbuk.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Generic entity.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    private static long serialVersionUID = 3l;
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

    @Transient
    public String getInfo() {
        return getClass().getSimpleName() + "#" + getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!getClass().equals(obj.getClass()))
            return false;

        AbstractEntity other = (AbstractEntity) obj;
        return safeGet(id) == safeGet(other.getId());
    }

    public String toString() {
        return getInfo();
    }

    @Override
    public int hashCode() {
        return new Long(safeGet(id)).intValue();
    }

    protected static long safeGet(Long x) {
        return x == null ? 0 : x;
    }
}
