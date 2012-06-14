package org.openblend.fejstbuk.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.openblend.fejstbuk.dao.GenericDAO;
import org.openblend.fejstbuk.domain.AbstractEntity;

/**
 * Abstract generic DAO.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class AbstractGenericDAO implements GenericDAO {
    protected abstract EntityManager getEM();

    protected <T> T getSingleResult(Query query) {
        List result = query.getResultList();
        return getSingleResult(result);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getSingleResult(Collection result) {
        return (result.isEmpty()) ? null : (T) result.iterator().next();
    }

    protected Long getSingleId(Query query) {
        List result = query.getResultList();
        return (result.isEmpty()) ? null : ((Number) result.get(0)).longValue();
    }

    protected String getSingleString(Query query) {
        List result = query.getResultList();
        return (result.isEmpty()) ? null : (result.get(0)).toString();
    }

    protected Long getCount(Query query) {
        Object result = query.getSingleResult();
        return ((Number) result).longValue();
    }

    public <T> T find(Class<T> clazz, Long id) {
        return getEM().find(clazz, id);
    }

    public <T> List<T> findAll(Class<T> clazz) {
        return getEM().createQuery(getEM().getCriteriaBuilder().createQuery(clazz)).getResultList();
    }

    public <T extends AbstractEntity> T merge(T entity) {
        return getEM().merge(entity);
    }

    public <T extends AbstractEntity> void save(T entity) {
        getEM().persist(entity);
    }

    public <T extends AbstractEntity> void delete(T entity) {
        getEM().remove(entity);
    }

    public <T extends AbstractEntity> int delete(Class<T> clazz, Long id) {
        Query query = getEM().createQuery("delete from " + clazz.getSimpleName() + " e where e.id = :eid");
        query.setParameter("eid", id);
        return query.executeUpdate();
    }

    public <T extends AbstractEntity> void initialize(T entity) {
        Hibernate.initialize(entity);
    }

    public StatelessSession statelessView() {
        Session session = (Session) getEM().getDelegate();
        return session.getSessionFactory().openStatelessSession();
    }
}
