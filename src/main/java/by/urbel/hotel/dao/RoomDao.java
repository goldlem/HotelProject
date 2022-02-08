package by.urbel.hotel.dao;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.exception.ObjectAlreadyExistsException;
import by.urbel.hotel.dao.exception.ObjectIsUsedException;
import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.RoomStatus;

import java.util.Date;

public interface RoomDao extends Dao<Room, Integer> {
    Room readFreeRoomByCategoryName(Date checkIn, Date checkOut, String categoryName) throws DaoException;

    void updateRoomStatus(int roomNumber, RoomStatus status) throws DaoException;

    @Override
    void create(Room room) throws DaoException, ObjectAlreadyExistsException;

    @Override
    void delete(Integer id) throws DaoException, ObjectIsUsedException;
}
