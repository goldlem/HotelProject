package by.urbel.hotel.dao;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.entity.Entity;

import java.util.List;

public interface Dao<T extends Entity, K> {
    void create(T t) throws DaoException;

    T readEntityById(K id) throws DaoException;

    List<T> readAll() throws DaoException;

    void update(T t) throws DaoException;

    void delete(K id) throws DaoException;
}
