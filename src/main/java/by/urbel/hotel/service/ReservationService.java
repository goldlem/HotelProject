package by.urbel.hotel.service;

import by.urbel.hotel.entity.Reservation;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface ReservationService {
    /**
     * Adds new reservation to database.
     *
     * @param user         person who wants to book room.
     * @param checkInDate  date for check into hotel.
     * @param checkOutDate date for check out of the hotel.
     * @param orderDate    date when user booked the room.
     * @param categoryName the name of room's category.
     * @throws ServiceException if an SQL error occurs.
     */
    void createReservation(User user, Date checkInDate, Date checkOutDate, Date orderDate, String categoryName)
            throws ServiceException;

    /**
     * Reads all reservations of user.
     *
     * @param user owner reservations.
     * @return true - List of found reservations.
     * @throws ServiceException if an SQL error occurs.
     */
    List<Reservation> readReservationsByUser(User user) throws ServiceException;

    /**
     * Reads all reservations.
     *
     * @return true - List of found reservations.
     * @throws ServiceException if an SQL error occurs.
     */
    List<Reservation> readAllReservations() throws ServiceException;

    /**
     * Removes reservation from database.
     *
     * @param reservationId id of reservation for deleting.
     * @throws ServiceException if an SQL error occurs.
     */
    void delete(int reservationId) throws ServiceException;
}
