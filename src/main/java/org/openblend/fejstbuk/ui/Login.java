package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.openblend.fejstbuk.dao.CustomDAO;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;
import org.openblend.fejstbuk.util.SecurityUtils;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@SessionScoped
@Named("login")
public class Login implements Serializable {
    private User current;
    private CustomDAO dao;

    @Produces @Current
    public User getCurrent() {
        return current;
    }

    public boolean isLogged() {
        return (getCurrent() != null);
    }

    public boolean login(String username, String password) {
        User user = dao.findUser(username);
        if (user != null) {
            String hashed = SecurityUtils.hash(username, password);
            if (hashed.equals(user.getPassword())) {
                current = user;
            }
        }
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
