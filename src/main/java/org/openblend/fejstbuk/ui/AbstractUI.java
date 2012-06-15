package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.inject.Inject;

import org.openblend.fejstbuk.dao.CustomDAO;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class AbstractUI implements Serializable {
    protected User current;
    protected CustomDAO dao;

    @Inject
    public void setCurrent(@Current User current) {
        this.current = current;
    }

    @Inject
    public void setDao(CustomDAO dao) {
        this.dao = dao;
    }
}
