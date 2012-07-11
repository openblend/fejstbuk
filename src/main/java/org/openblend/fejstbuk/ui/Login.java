package org.openblend.fejstbuk.ui;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.openblend.fejstbuk.dao.CustomDAO;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;
import org.openblend.fejstbuk.qualifiers.LoggedIn;
import org.openblend.fejstbuk.util.SecurityUtils;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@SessionScoped
@Named("login")
public class Login implements Serializable {

    @Inject
    private Credentials credentials;

    private User current;
    private CustomDAO dao;

    @Produces
    @Current
    @LoggedIn
    @Named("currentUser")
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

    public String uiLogin() {
        boolean ret = login(credentials.getUsername(), credentials.getPassword());
        if (!ret) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Authentication failed!"));
            return null;
        }

        return "home";
    }

    public void logout() {
        current = null;
    }

    @Inject
    public void setDao(CustomDAO dao) {
        this.dao = dao;
    }
}
