package by.urbel.hotel.dao.impl;

import by.urbel.hotel.dao.CategoryDao;
import by.urbel.hotel.dao.exception.ObjectAlreadyExistsException;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.exception.ObjectIsUsedException;
import by.urbel.hotel.dao.impl.pool.ConnectionPool;
import by.urbel.hotel.dao.impl.pool.exception.ConnectionPoolException;
import by.urbel.hotel.entity.RoomCategory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryDaoMySql implements CategoryDao {
    private static final Logger LOGGER = LogManager.getLogger(CategoryDaoMySql.class.getName());
    private static final ConnectionPool pool = ConnectionPool.getInstance();

    private static final String FIND_ALL_CATEGORIES =
            "SELECT `categoryName`, `roomPrice`, `bedsCount` FROM categories";
    private static final String FIND_ROOM_CATEGORY_BY_CATEGORY_NAME =
            "SELECT `categoryName`, `roomPrice`, `bedsCount` FROM categories WHERE categoryName = ?";
    private static final String FIND_PHOTO_PATHS = "SELECT `photoPath` FROM `category_photos` WHERE categoryName = ?";
    private static final String DELETE_ROOM_CATEGORY_BY_NAME = "DELETE FROM `categories` WHERE categoryName = ?";
    private static final String DELETE_PHOTO_PATHS_BY_NAME = "DELETE FROM `category_photos` WHERE categoryName = ?";
    private static final String CREATE_ROOM_CATEGORY =
            "INSERT INTO `categories`(`categoryName`, `roomPrice`, `bedsCount`) VALUE (?,?,?)";
    private static final String CREATE_PHOTO_PATHS =
            "INSERT INTO `category_photos`(`categoryName`, `photoPath`) VALUE (?,?)";
    private static final String UPDATE_ROOM_CATEGORY =
            "UPDATE `categories` SET `roomPrice` = ?, `bedsCount` = ? WHERE `categoryName`=?";
    private static final String UPDATE_CATEGORY_PHOTOS =
            "UPDATE `category_photos` SET `photoPath` = ? WHERE `categoryName`=?";
    private static final String FIND_FREE_CATEGORIES_BY_DATES_AND_BEDS_NUMBER =
            "CALL `find_free_categories_by_dates_and_beds`(?,?,?)";

    private static final String CATEGORY_NAME_ATTRIBUTE = "categoryName";
    private static final String ROOM_PRICE_ATTRIBUTE = "roomPrice";
    private static final String COUNT_OF_BEDS_ATTRIBUTE = "bedsCount";
    private static final String PHOTO_PATH_ATTRIBUTE = "photoPath";

    @Override
    public void create(RoomCategory roomCategory) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(CREATE_ROOM_CATEGORY);
                connection.setAutoCommit(false);
                preparedStatement.setString(1, roomCategory.getCategoryName());
                preparedStatement.setBigDecimal(2, roomCategory.getRoomPrice());
                preparedStatement.setInt(3, roomCategory.getBedsCount());
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(CREATE_PHOTO_PATHS);
                preparedStatement.setString(1, roomCategory.getCategoryName());
                for (String path : roomCategory.getPhotoPaths()) {
                    preparedStatement.setString(2, path);
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            }catch (SQLIntegrityConstraintViolationException e){
                connection.rollback();
                throw new ObjectAlreadyExistsException(e);
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            } finally {
                connection.setAutoCommit(true);
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public RoomCategory readEntityById(String categoryName) throws DaoException {
        RoomCategory roomCategory = null;
        try (Connection connection = pool.getConnection()) {
            ResultSet resultSet = null;
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(FIND_ROOM_CATEGORY_BY_CATEGORY_NAME);
                preparedStatement.setString(1, categoryName);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    roomCategory = this.resultSetToCategory(resultSet, connection);
                }
                assert roomCategory != null;
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            } finally {
                connection.setAutoCommit(true);
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
        return roomCategory;
    }

    @Override
    public List<RoomCategory> readAll() throws DaoException {
        List<RoomCategory> roomCategories = new ArrayList<>();

        try (Connection connection = pool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_CATEGORIES);
                 ResultSet rs = preparedStatement.executeQuery()) {

                while (rs.next()) {
                    roomCategories.add(this.resultSetToCategory(rs, connection));
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            } finally {
                connection.setAutoCommit(true);
            }
            return roomCategories;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(RoomCategory roomCategory) throws DaoException {

        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(UPDATE_ROOM_CATEGORY);
                preparedStatement.setBigDecimal(1, roomCategory.getRoomPrice());
                preparedStatement.setInt(2, roomCategory.getBedsCount());
                preparedStatement.setString(3, roomCategory.getCategoryName());
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(UPDATE_CATEGORY_PHOTOS);
                preparedStatement.setString(2, roomCategory.getCategoryName());
                for (String path : roomCategory.getPhotoPaths()) {
                    preparedStatement.setString(1, path);
                    preparedStatement.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            } finally {
                connection.setAutoCommit(true);
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }

    }

    @Override
    public void delete(String categoryName) throws DaoException, ObjectIsUsedException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = null;
            try {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(DELETE_PHOTO_PATHS_BY_NAME);
                preparedStatement.setString(1, categoryName);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(DELETE_ROOM_CATEGORY_BY_NAME);
                preparedStatement.setString(1, categoryName);
                preparedStatement.executeUpdate();
                connection.commit();
            }catch (SQLIntegrityConstraintViolationException e){
                connection.rollback();
                throw new ObjectIsUsedException(e);
            }catch (SQLException e){
                connection.rollback();
                throw new DaoException(e);
            }finally {
                connection.setAutoCommit(true);
                if (preparedStatement!=null){
                    preparedStatement.close();
                }
            }
            } catch (SQLException | ConnectionPoolException e) {
                throw new DaoException(e);
            }
        }

        @Override
        public List<RoomCategory> readFreeCategoriesByDatesAndBedsNumber ( int bedsNumber, Date checkIn, Date checkOut)
            throws DaoException {
            List<RoomCategory> categoryList = new ArrayList<>();
            try (Connection connection = pool.getConnection()) {
                PreparedStatement preparedStatement = null;
                try {
                    connection.setAutoCommit(false);
                    preparedStatement = connection.prepareStatement(FIND_FREE_CATEGORIES_BY_DATES_AND_BEDS_NUMBER);
                    preparedStatement.setObject(1, checkIn);
                    preparedStatement.setObject(2, checkOut);
                    preparedStatement.setInt(3, bedsNumber);

                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        while (rs.next()) {
                            categoryList.add(this.resultSetToCategory(rs, connection));
                        }
                    }catch (SQLException e){
                        throw new DaoException(e);
                    }
                    connection.commit();
                } catch (SQLException e) {
                    connection.rollback();
                    throw new DaoException(e);
                } finally {
                    connection.setAutoCommit(true);
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                }
            } catch (SQLException | ConnectionPoolException e) {
                throw new DaoException(e);
            }
            return categoryList;
        }

        private List<String> readPhotoPaths (Connection connection, String categoryName) throws SQLException {

            List<String> photoPaths = new ArrayList<>();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try {
                preparedStatement = connection.prepareStatement(FIND_PHOTO_PATHS);
                preparedStatement.setString(1, categoryName);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    photoPaths.add(resultSet.getString(PHOTO_PATH_ATTRIBUTE));
                }
                return photoPaths;
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        }

        private RoomCategory resultSetToCategory (ResultSet rs, Connection connection) throws SQLException {
            String categoryName = rs.getString(CATEGORY_NAME_ATTRIBUTE);
            List<String> photoPaths = this.readPhotoPaths(connection, categoryName);
            return new RoomCategory(
                    categoryName,
                    rs.getBigDecimal(ROOM_PRICE_ATTRIBUTE),
                    rs.getInt(COUNT_OF_BEDS_ATTRIBUTE),
                    photoPaths
            );
        }
    }