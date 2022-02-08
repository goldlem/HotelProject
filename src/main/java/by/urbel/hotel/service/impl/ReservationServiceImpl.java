package by.urbel.hotel.service.impl;

import by.urbel.hotel.dao.*;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.entity.Reservation;
import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.ReservationService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.validation.ReservationValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ReservationServiceImpl implements ReservationService {
    private static final Logger LOGGER = LogManager.getLogger(ReservationServiceImpl.class.getName());

    private final ReservationDao reservationDao = DaoProvider.getInstance().getReservationDao();

    @Override
    public void createReservation(User user, Date checkInDate, Date checkOutDate, Date orderDate, String categoryName)
            throws ServiceException {
        if (!ReservationValidator.isValidOrderDates(checkInDate, checkOutDate)) {
            throw new ServiceException("CheckInDate is after checkOutDate");
        } else {
            LOGGER.debug("Validation is working");
            RoomDao roomDao = DaoProvider.getInstance().getRoomDao();
            try {
                Room freeRoom = roomDao.readFreeRoomByCategoryName(checkInDate, checkOutDate, categoryName);
                Reservation reservation = new Reservation(
                        user,
                        this.calculateTotalCost(freeRoom, checkInDate, checkOutDate),
                        orderDate,
                        checkInDate,
                        checkOutDate,
                        freeRoom);
                reservationDao.create(reservation);
            } catch (DaoException e) {
                LOGGER.error("Error while creating reservation", e);
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public List<Reservation> readReservationsByUser(User user) throws ServiceException {
        try {
            return reservationDao.readReservationsByUser(user);
        } catch (DaoException e) {
            LOGGER.error("Error while reading reservations for user {}", user.getUserId(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> readAllReservations() throws ServiceException {
        ReservationDao reservationDao = DaoProvider.getInstance().getReservationDao();
        try {
            return reservationDao.readAll();
        } catch (DaoException e) {
            LOGGER.error("Error while reading all reservations", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(int reservationId) throws ServiceException {
        try {
            reservationDao.delete(reservationId);
        } catch (DaoException e) {
            LOGGER.error("Error while deleting reservation {}", reservationId, e);
            throw new ServiceException(e);
        }
    }

    private BigDecimal calculateTotalCost(Room room, Date checkIn, Date checkOut) {
        long countOfNights = (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
        if (countOfNights == 0) {
            countOfNights = 1;
        }
        LOGGER.debug("count of nights = " + countOfNights);
        BigDecimal roomPrice = room.getCategory().getRoomPrice();
        BigDecimal totalCost = roomPrice.multiply(BigDecimal.valueOf(countOfNights));
        LOGGER.debug("totalCost = " + totalCost);
        return totalCost;
    }
}
