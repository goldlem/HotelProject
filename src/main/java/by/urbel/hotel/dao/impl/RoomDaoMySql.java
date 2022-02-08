package by.urbel.hotel.dao.impl;

import by.urbel.hotel.dao.CategoryDao;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.dao.RoomDao;
import by.urbel.hotel.dao.exception.ObjectAlreadyExistsException;
import by.urbel.hotel.dao.exception.ObjectIsUsedException;
import by.urbel.hotel.dao.impl.pool.ConnectionPool;
import by.urbel.hotel.dao.impl.pool.exception.ConnectionPoolException;
import by.urbel.hotel.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class RoomDaoMySql implements RoomDao {
    private static final Logger LOGGER = LogManager.getLogger(RoomDaoMySql.class.getName());

    private static final String FIND_FREE_ROOM_BY_DATES_AND_CATEGORY_NAME =
            "CALL find_free_room_by_dates_and_category(?,?,?);";

    private static final String FIND_ALL_ROOMS = "SELECT roomNumber, categoryName, roomStatus FROM rooms";

    private static final String FIND_ROOM_BY_ID =
            "SELECT roomNumber, categoryName, roomStatus FROM rooms WHERE roomNumber=?";

    public static final String DELETE_ROOM_BY_ID = "DELETE FROM `rooms` WHERE `roomNumber`=?";

    private static final String UPDATE_ROOM = "UPDATE rooms SET categoryName =?,roomStatus =? WHERE roomNumber=?";

    private static final String UPDATE_ROOM_STATUS = "UPDATE rooms SET roomStatus =? WHERE roomNumber=?";

    private static final String CREATE_ROOM = "INSERT INTO rooms (roomNumber, categoryName, roomStatus) VALUE (?, ?,?)";

    private static final String ROOM_NUMBER_ATTRIBUTE = "roomNumber";
    private static final String ROOM_STATUS_ATTRIBUTE = "roomStatus";
    private static final String CATEGORY_NAME_ATTRIBUTE = "categoryName";

    private static final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<Room> readAll() throws DaoException {
        List<Room> rooms = new ArrayList<>();
        Room room;
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_ROOMS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                room = this.resultSetToRoom(rs);
                rooms.add(room);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return rooms;
    }

    @Override
    public Room readEntityById(Integer id) throws DaoException {
        Room room = null;
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ROOM_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                room = this.resultSetToRoom(rs);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return room;
    }

    @Override
    public void delete(Integer id) throws DaoException, ObjectIsUsedException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROOM_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            throw new ObjectIsUsedException();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(Room room) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ROOM);
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getCategory().getCategoryName());
            preparedStatement.setString(3, room.getStatus().name());
            preparedStatement.executeUpdate();
        }catch (SQLIntegrityConstraintViolationException e){
            throw new ObjectAlreadyExistsException(e);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Room room) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROOM);
            preparedStatement.setString(1, room.getCategory().getCategoryName());
            preparedStatement.setString(2, room.getStatus().name());
            preparedStatement.setInt(3, room.getRoomNumber());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Room readFreeRoomByCategoryName(Date checkIn, Date checkOut, String categoryName) throws DaoException {
        Room room = null;
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_FREE_ROOM_BY_DATES_AND_CATEGORY_NAME)) {

            preparedStatement.setObject(1, checkIn);
            preparedStatement.setObject(2, checkOut);
            preparedStatement.setString(3, categoryName);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    room = this.resultSetToRoom(rs);
                }
                return room;
            }
        } catch (SQLException |
                ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateRoomStatus(int roomNumber, RoomStatus status) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ROOM_STATUS);
            preparedStatement.setString(1, status.name().toLowerCase());
            preparedStatement.setInt(2, roomNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private Room resultSetToRoom(ResultSet rs) throws SQLException {
        CategoryDao categoryDao = DaoProvider.getInstance().getCategoryDao();
        try {
            RoomCategory roomCategory = categoryDao.readEntityById(rs.getString(CATEGORY_NAME_ATTRIBUTE));
            int roomNumber = rs.getInt(ROOM_NUMBER_ATTRIBUTE);
            String roomStatus = rs.getString(ROOM_STATUS_ATTRIBUTE);

            return new Room(roomNumber, roomCategory, RoomStatus.valueOf(roomStatus.toUpperCase()));
        } catch (DaoException e) {
            throw new SQLException(e);
        }
    }
}
