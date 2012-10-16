package org.openblend.fejstbuk.ui;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.openblend.fejstbuk.domain.User;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
@RequestScoped
@Named
public class ProfileFriend {
    @Inject
    private EntityManager em;

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Named
    @Produces
    public User getFriendProfile() {
        return em.find(User.class, id);
    }

}
