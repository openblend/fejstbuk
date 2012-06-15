package org.openblend.fejstbuk.dao;

import java.util.Collection;
import java.util.List;

import org.hibernate.StatelessSession;
import org.openblend.fejstbuk.domain.AbstractEntity;

/**
 * Generic DAO.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public interface GenericDAO {

    <T> T find(Class<T> clazz, Long id);

    <T> List<T> findAll(Class<T> clazz);

    <T extends AbstractEntity> T merge(T entity);

    <T extends AbstractEntity> void save(T entity);

    <T extends AbstractEntity> void delete(T entity);

    <T extends AbstractEntity> int delete(Class<T> clazz, Long id);

    <T extends AbstractEntity> void initialize(T entity);

    <T extends Collection> void initializeCollection(T collection);

    StatelessSession statelessView();
}
