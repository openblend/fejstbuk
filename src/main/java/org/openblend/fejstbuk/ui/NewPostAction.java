package org.openblend.fejstbuk.ui;

import java.util.Date;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.openblend.fejstbuk.domain.Status;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;
import org.openblend.fejstbuk.qualifiers.LoggedIn;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
@RequestScoped
@Named
@Stateful
public class NewPostAction {
    @PersistenceContext
    EntityManager em;


    @LoggedIn
    @Current
    @Inject
    private User user;

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void post() {
        Status s = new Status();
        s.setTimestamp(new Date());
        s.setStatus(text);
        s.setUser(em.find(User.class, user.getId()));
        em.persist(s);
    }
}
