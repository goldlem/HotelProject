package by.urbel.hotel.dao;

import by.urbel.hotel.dao.exception.ObjectAlreadyExistsException;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.exception.ObjectIsUsedException;
import by.urbel.hotel.entity.RoomCategory;

import java.util.Date;
import java.util.List;

public interface CategoryDao extends Dao<RoomCategory, String> {
    List<RoomCategory> readFreeCategoriesByDatesAndBedsNumber(int bedsNumber, Date checkIn, Date checkOut)
            throws DaoException;

    @Override
    void delete(String id) throws DaoException, ObjectIsUsedException;

    @Override
    void create(RoomCategory category) throws DaoException, ObjectAlreadyExistsException;
}
