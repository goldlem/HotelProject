package by.urbel.hotel.dao;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.entity.Reservation;
import by.urbel.hotel.entity.User;

import java.util.List;

public interface ReservationDao extends Dao<Reservation, Integer> {
    List<Reservation> readReservationsByUser(User user) throws DaoException;
}
