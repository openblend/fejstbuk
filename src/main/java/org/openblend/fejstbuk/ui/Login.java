package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.openblend.fejstbuk.dao.CustomDAO;
import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@SessionScoped
@Named("login")
public class Login implements Serializable {
    private User current;
    private CustomDAO dao;

    public User getCurrent() {
        return current;
    }

    public boolean login(String username, String password) {
        current = dao.findUser(username, password);
        return (current != null);
    }

    public void logout() {
        current = null;
    }

    @Inject
    public void setDao(CustomDAO dao) {
        this.dao = dao;
    }
}
