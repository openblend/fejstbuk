package org.openblend.fejstbuk.ui;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
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
@Stateless
@Named
public class NewsAction {
    @PersistenceContext
    private EntityManager em;


    @Inject
    @Current
    @LoggedIn
    private User user;


    @Produces
    @Named
    public List<Status> getNewsFeed() {
        System.out.println("userid: " + user.getId());
        return em.createQuery("select a from Status a where a.user.id = :userId", Status.class)
                .setParameter("userId", user.getId())
                .getResultList();

    }

    public Date getTimeDiff(Date date) {
        if (date == null) { return null; }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        long postTime = c.getTimeInMillis();
        long diff = System.currentTimeMillis() - postTime;
        c.setTimeInMillis(diff);

        return c.getTime();
    }
}
