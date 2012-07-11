package org.openblend.fejstbuk.ui;

import java.util.ArrayList;
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
    @LoggedIn
    private User user;


    @Produces
    @Named
    public List<Status> getNewsFeed() {
        List<Status> list = new ArrayList<Status>();
        list.add(new Status("hello",getRandomTime(), 1));
        list.add(new Status("yello",getRandomTime(), 2));
        list.add(new Status("danes je lep dan",getRandomTime(), 3));
        list.add(new Status("Zvečer bo toča... menda.",getRandomTime(), 4));
        return list;
    }
    private Date getRandomTime(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR,-(int)(Math.random()*60));
        c.add(Calendar.MINUTE, (int)(Math.random()*60));
        c.add(Calendar.SECOND, (int)(Math.random()*60));

        return c.getTime();
    }
    public Date getTimeDiff(Date date){
        Calendar c =  Calendar.getInstance();
        c.setTime(date);
        long postTime = c.getTimeInMillis();
        long diff = System.currentTimeMillis() - postTime;
        c.setTimeInMillis(diff);

        return c.getTime();
    }
}
