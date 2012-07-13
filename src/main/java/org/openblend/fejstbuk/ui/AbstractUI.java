package org.openblend.fejstbuk.ui;

import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.openblend.fejstbuk.dao.FacebookDAO;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class AbstractUI implements Serializable {
    protected User current;

    @Inject
    protected FacebookDAO dao;

    @Inject
    protected EntityManager em;

    @Inject
    public void setCurrent(@Current User current) {
        this.current = current;
    }


}
