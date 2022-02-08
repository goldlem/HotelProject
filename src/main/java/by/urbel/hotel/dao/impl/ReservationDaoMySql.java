package by.urbel.hotel.dao.impl;

import by.urbel.hotel.dao.*;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.impl.pool.ConnectionPool;
import by.urbel.hotel.dao.impl.pool.exception.ConnectionPoolException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.entity.Reservation;
import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoMySql implements ReservationDao {
    private static final Logger LOGGER = LogManager.getLogger(ReservationDaoMySql.class.getName());

    private static final String RESERVATION_ID_PARAMETER = "id";
    private static final String RESERVATION_COST_PARAMETER = "totalCost";
    private static final String BOOKING_DATE_PARAMETER = "bookingDate";
    private static final String CHECK_IN_DATE_PARAMETER = "checkInDate";
    private static final String CHECK_OUT_DATE_PARAMETER = "checkOutDate";
    private static final String ROOM_NUMBER_PARAMETER = "roomNumber";
    private static final String USER_ID_PARAMETER = "userId";

    private static final String READ_RESERVATIONS_BY_USER_ID =
            "SELECT id,userId,totalCost,bookingDate,checkInDate,checkOutDate,roomNumber FROM reservations " +
                    "WHERE userId=?";
    private static final String READ_ALL_RESERVATIONS =
            "SELECT id,userId,totalCost,bookingDate,checkInDate,checkOutDate,roomNumber FROM reservations ";
    private static final String CREATE_RESERVATION = "INSERT INTO reservations " +
            "(userId,totalCost,bookingDate,checkInDate,checkOutDate, roomNumber) VALUE (?,?,?,?,?,?)";

    private static final String DELETE_RESERVATION_BY_ID = "DELETE FROM reservations WHERE id=?";

    private static final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public void create(Reservation reservation) throws DaoException {

        Date checkInDate = new Date(reservation.getCheckInDate().getTime());
        Date checkOutDate = new Date(reservation.getCheckOutDate().getTime());
        Timestamp bookingDate = new Timestamp(reservation.getBookingDate().getTime());

        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION)) {

            LOGGER.debug("booking date = " + bookingDate);
            LOGGER.debug(reservation.getBookingDate().toString());

            preparedStatement.setInt(1, reservation.getUser().getUserId());
            preparedStatement.setBigDecimal(2, reservation.getTotalCost());
            preparedStatement.setTimestamp(3, bookingDate);
            preparedStatement.setDate(4, checkInDate);
            preparedStatement.setDate(5, checkOutDate);
            preparedStatement.setInt(6, reservation.getRoom().getRoomNumber());
            preparedStatement.executeUpdate();

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public Reservation readEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public List<Reservation> readAll() throws DaoException {
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation;
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_RESERVATIONS);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()){
                reservation = this.resultSetToReservation(resultSet);
                reservationList.add(reservation);
            }
            return reservationList;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Reservation reservation) throws DaoException {
    }

    @Override
    public void delete(Integer id) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Reservation> readReservationsByUser(User user) throws DaoException {
        List<Reservation> reservationList = new ArrayList<>();
        Reservation reservation;

        try (Connection connection = pool.getConnection()) {
            ResultSet rs = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(READ_RESERVATIONS_BY_USER_ID)) {
                preparedStatement.setInt(1, user.getUserId());
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    reservation = this.resultSetToReservation(rs, user);
                    reservationList.add(reservation);
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return reservationList;
    }

    private Reservation resultSetToReservation(ResultSet resultSet, User user) throws SQLException, DaoException {
        RoomDao roomDao = DaoProvider.getInstance().getRoomDao();
        Room room = roomDao.readEntityById(resultSet.getInt(ROOM_NUMBER_PARAMETER));
        return new Reservation(
                resultSet.getInt(RESERVATION_ID_PARAMETER),
                user,
                resultSet.getBigDecimal(RESERVATION_COST_PARAMETER),
                resultSet.getDate(BOOKING_DATE_PARAMETER),
                resultSet.getDate(CHECK_IN_DATE_PARAMETER),
                resultSet.getDate(CHECK_OUT_DATE_PARAMETER),
                room
        );
    }

    private Reservation resultSetToReservation(ResultSet resultSet) throws SQLException, DaoException {
        RoomDao roomDao = DaoProvider.getInstance().getRoomDao();
        UserDao userDao = DaoProvider.getInstance().getUserDao();
        Room room = roomDao.readEntityById(resultSet.getInt(ROOM_NUMBER_PARAMETER));
        User user = userDao.readEntityById(resultSet.getInt(USER_ID_PARAMETER));
        return new Reservation(
                resultSet.getInt(RESERVATION_ID_PARAMETER),
                user,
                resultSet.getBigDecimal(RESERVATION_COST_PARAMETER),
                resultSet.getDate(BOOKING_DATE_PARAMETER),
                resultSet.getDate(CHECK_IN_DATE_PARAMETER),
                resultSet.getDate(CHECK_OUT_DATE_PARAMETER),
                room
        );
    }
}
