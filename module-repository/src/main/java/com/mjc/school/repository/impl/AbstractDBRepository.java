package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.exception.EntityCreationConflictException;
import com.mjc.school.repository.model.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDBRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

    private final Class<T> entityClass;

    private EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    @Autowired
    protected AbstractDBRepository(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
        this.entityManager = entityManagerFactory.createEntityManager();
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
    }


    @Override
    public List<T> readAll(int page, int size, String sortBy){
        String[] splitted = sortBy.split("::");

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        if (splitted.length == 2 && "DESC".equals(splitted[1].toUpperCase())){
            query.orderBy(builder.desc(root.get(splitted[0])));
        }else {
            query.orderBy(builder.asc(root.get(splitted[0])));
        }
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<T> readById(K id){
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    @Override
    public T create(T createRequest){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(createRequest);
            entityManager.getTransaction().commit();
        }catch (PersistenceException | ConstraintViolationException e){
            throw new EntityCreationConflictException(e.getMessage());
        }
        return createRequest;
    }

    @Override
    public T update(T updateRequest){
        entityManager.getTransaction().begin();
        var updated = entityManager.merge(updateRequest);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return updated;
    }

    @Override
    public boolean deleteById(K id){
        T entityRef = getReference(id);
        if (entityRef == null){
            return false;
        }
        entityManager.getTransaction().begin();
        entityManager.remove(entityRef);
        entityManager.getTransaction().commit();
        return true;
    }

    public boolean existById(K id){
        return entityManager.find(entityClass, id) != null;
    }

    public T getReference(K id){
        return entityManager.getReference(this.entityClass, id);
    }
}
