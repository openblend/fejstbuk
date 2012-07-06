package org.openblend.fejstbuk.ui;

import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.openblend.fejstbuk.domain.Post;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.LoggedIn;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a>
 */
@Stateless
public class NewsAction {
    @PersistenceContext
    private EntityManager em;


    @Inject
    @LoggedIn
    private User user;


    @Produces
    @Named
    public List<Post> getNewsFeed() {
        TypedQuery<Post> q = em.createQuery("select p from Post p where p.user = :user", Post.class);
        q.setParameter("user", user);
        return q.getResultList();
    }

}
