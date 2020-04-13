package com.jeyblog.perisistence;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * The type Generic dao.
 *
 * @param <T> the type parameter
 */
@Log4j2
public class GenericDao <T> {
    private Class<T> type;

    /**
     * Instantiates a new Generic dao.
     *
     * @param type the type, for example Role
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }
    private Session getSession(){
        return SessionFactoryProvider.getSessionFactory().openSession();
    }

    /**
     * Gets all objects.
     *
     * @return the all
     */
    public List<T> getAll() {
        Session session =  getSession();
        CriteriaBuilder builder =  session.getCriteriaBuilder();
        CriteriaQuery<T> query =  builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> list =  session.createQuery(query).getResultList();
        log.debug("List of all entities: " + list);
        return list;
    }

    /**
     * Create Entity
     *
     * @param entity Entity to be inserted or updated
     * @return the int
     */
    public int create(T entity) {
        int id = 0;
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        id = (int)session.save(entity);
        transaction.commit();
        session.close();
        log.debug("A new Entity was Added with an Id: " + id);
        return id;
    }

    /**
     * Gets an entity by id
     *
     * @param <T> the type parameter
     * @param id  entity id to search by
     * @return entity by id
     */
    public <T> T getById(int id) {
        Session session = getSession();
        T entity = (T)session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Inserts or updates the entity.
     *
     * @param entity entity to be inserted/saved
     */
    public void saveOrUpdate(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Finds entity by title.
     * @param title the property name.
     * @param value the value by which to find.
     * @return
     */
    public T getPostByTitle(String title, Object value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(title),value));

        return session.createQuery(query).uniqueResult();
    }

    /**
     * Deletes the entity.
     * @param entity entity to be deleted
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(entity);
        transaction.commit();
        session.close();
    }


}
