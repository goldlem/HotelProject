package by.urbel.hotel.service;

import by.urbel.hotel.entity.Reservation;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    void createReservation(User user, Date checkInDate, Date checkOutDate, Date orderDate, String categoryName)
            throws ServiceException;
    List<Reservation> readReservationsByUser(User user) throws ServiceException;
    List<Reservation> readAllReservations() throws ServiceException;
    void delete(int reservationId) throws ServiceException;
}
