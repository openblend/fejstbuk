package org.openblend.fejstbuk.ui;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@RequestScoped
@Named("profile")
public class Profile extends AbstractUI {
    public String addFriend(long friendId) {
        User friend = dao.find(User.class, friendId);
        dao.addFriend(current, friend);
        return "home.jsf";
    }

    public String removeFriend(long friendId) {
        User friend = dao.find(User.class, friendId);
        dao.removeFriend(current, friend);
        return "home.jsf";
    }
}
