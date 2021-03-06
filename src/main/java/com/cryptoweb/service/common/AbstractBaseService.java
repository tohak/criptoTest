package com.cryptoweb.service.common;


import com.cryptoweb.domain.BaseEntity;
import com.cryptoweb.repos.cammon.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Основные действия с бд. достать всех, удалить и т.д. для любой ентети.
 */
@Service
public abstract class AbstractBaseService<E extends BaseEntity> implements BaseService<E> {
    //получение название типа класса и записываем в строку
    protected final String entityName = ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();

    protected abstract BaseRepository<E, Long> getRepository();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Optional<E> getById(Long id) {
        checkArgument(id != null, "Received incoming parameter null!");  //гугл ультилита- проверка на null
        Optional<E> entity = getRepository().findById(id);
        if (!entity.isPresent()) {
            throw new EntityNotFoundException(entityName.getClass() + "  nety  takogo");
        }
        return entity;
    }

    @Override
    public boolean exists(Long id) {
        checkArgument(id != null, "Received incoming parameter null!");
        return getRepository().existsById(id);
    }



    @Override
    public E create(E entity) {
        logger.info("create");
        checkArgument(entity != null, "Not null " + entityName + " is expected!");
        return getRepository().save(entity);
    }

    @Override
    public long count() {
        return getRepository().count();
    }

    @Override
    public E update(E entity) {
        logger.info("update");
        checkArgument(entity != null, "Not null " + entityName + " is expected!");
        checkExists(entity.getId());
        return getRepository().save(entity);
    }

    @Override
    public void delete(E entity) {
        logger.info("delete ");
        checkArgument(entity != null, "cannot delete!");
        checkExists(entity.getId());
        getRepository().delete(entity);
    }

    @Override
    public void delete(Long id) {
        checkArgument(id != null, "can not delete!");
        checkExists(id);
        getRepository().deleteById(id);
    }

    @Override
    public Iterable<E> getAll(Iterable<Long> ids) {
        logger.info("findAll ");
        return getRepository().findAllById(ids);
    }

    @Override
    public Iterable<E> getAll() {
        logger.info("findAll ");
        return getRepository().findAll();
    }

    private void checkExists(long id) {
        final boolean exists = getRepository().existsById(id);
        if (!exists) {
            logger.error("ID can not be null.");
            throw new EntityNotFoundException(entityName.getClass() + " with id " + id + " not exists!");
        }
    }

}

