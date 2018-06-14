package ir.oddrun.score.model.repository;



import ir.oddrun.score.data.hibernate.HibernateUtil;
import ir.oddrun.score.data.hibernate.QueryBuilder;
import ir.oddrun.score.model.entity.BaseEntity;

import javax.persistence.PersistenceException;
import java.lang.reflect.ParameterizedType;

public class GenericRepository<T extends BaseEntity> {

    @SuppressWarnings("unchecked")
    private Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private T entity;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    /**
     * @param id The internal ID of the entity
     */
    public T load(long id) throws PersistenceException {
        return load(id, false);
    }

    public T load(long id, boolean externalId) throws PersistenceException {
        try {
            setEntity(type.cast(HibernateUtil.getSession().get(type, id)));
            return getEntity();
        } catch (Exception e) {
            setEntity(null);
            throw new PersistenceException("", e);
        }
    }

    public void save() throws PersistenceException {
        save(false);
    }

    public void save(boolean commit) throws PersistenceException {
        if (getEntity() != null) {
            try {
                if (commit)
                    HibernateUtil.beginTransaction();

                HibernateUtil.save(getEntity());

                if (commit)
                    HibernateUtil.commitTransaction();
            } catch (Exception e) {
                if (commit)
                    HibernateUtil.rollbackTransaction();

                throw new PersistenceException("", e);
            }
        }
    }

    public void update() throws PersistenceException {
        update(false);
    }

    public void update(boolean commit) throws PersistenceException {
        if (getEntity() != null) {
            try {
                if (commit)
                    HibernateUtil.beginTransaction();

                HibernateUtil.getSession().update(getEntity());

                if (commit)
                    HibernateUtil.commitTransaction();
            } catch (Exception e) {
                if (commit)
                    HibernateUtil.rollbackTransaction();

                throw new PersistenceException("", e);
            }
        }
    }


    public void saveOrUpdate(boolean commit) throws PersistenceException {
        if (getEntity() != null) {
            try {
                if (commit)
                    HibernateUtil.beginTransaction();

                HibernateUtil.getSession().saveOrUpdate(getEntity());

                if (commit)
                    HibernateUtil.commitTransaction();
            } catch (Exception e) {
                if (commit)
                    HibernateUtil.rollbackTransaction();

                throw new PersistenceException("", e);
            }
        }
    }

    public T findBy(String field, Object value) {
        QueryBuilder queryBuilder = new QueryBuilder(
                String.format("FROM %s a WHERE a.%s = ?1", type.getSimpleName(), field));

        T t = queryBuilder.object(value);
        setEntity(t);
        return t;
    }

    public T findNull(String field) {
        QueryBuilder queryBuilder = new QueryBuilder(
                String.format("FROM %s a WHERE a.%s is null", type.getSimpleName(), field));

        T t = queryBuilder.object();
        setEntity(t);
        return t;
    }

    protected void verifyEntity() {
        if (getEntity() == null) {
            String cls = getClass().getSimpleName().replace("Entity", "");
            throw new NullPointerException(cls + " not found");
        }
    }
}
