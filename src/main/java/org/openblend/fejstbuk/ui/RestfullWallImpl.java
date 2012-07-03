package org.openblend.fejstbuk.ui;

import org.openblend.fejstbuk.domain.Owned;
import org.openblend.fejstbuk.domain.User;
import org.openblend.fejstbuk.qualifiers.Current;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@Stateless
public class RestfullWallImpl implements RestfullWall {
    private static final Comparator<Owned> COMPARATOR = new OwnedComparator();

    private EntityManager em;
    private User current;

    @SuppressWarnings("unchecked")
    public List<Owned> activity() throws IOException {
        List<Owned> list = new ArrayList<Owned>();
        // users to check
        Set<User> users = new HashSet<User>(current.getFriends());
        users.add(current);
        // user and his friend's posts
        Query query = em.createQuery("select p from Post p where p.user in (:users) order by p.timestamp desc");
        query.setMaxResults(Wall.SIZE);
        query.setParameter("users", users);
        list.addAll(query.getResultList());
        // user and his friend's likes
        query = em.createQuery("select l from Like l where l.user in (:users) order by l.timestamp desc");
        query.setMaxResults(Wall.SIZE);
        query.setParameter("users", users);
        list.addAll(query.getResultList());
        // sort by timestamp, desc
        Collections.sort(list, COMPARATOR);
        // just get the wall size max
        return list.subList(0, Math.min(list.size(), Wall.SIZE));
    }

    @Inject
    public void setCurrent(@Current User current) {
        this.current = current;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    private static class OwnedComparator implements Comparator<Owned> {
        public int compare(Owned o1, Owned o2) {
            return (int) (o2.getTimestamp().getTime() - o1.getTimestamp().getTime());
        }
    }
}
