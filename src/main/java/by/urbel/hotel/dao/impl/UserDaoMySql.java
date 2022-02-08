package by.urbel.hotel.dao.impl;


import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.UserDao;
import by.urbel.hotel.dao.impl.pool.ConnectionPool;
import by.urbel.hotel.dao.impl.pool.exception.ConnectionPoolException;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoMySql implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoMySql.class.getName());

    private static final String FIND_ALL_USERS = "SELECT `id`,`email`,`name`,`surname`,`phoneNumber`," +
            "`password`,`role` FROM `users`";
    private static final String FIND_USER_BY_EMAIL = "SELECT `id`,`email`,`name`,`surname`,`phoneNumber`," +
            "`password`,`role` FROM `users` WHERE email=?";
    private static final String FIND_USER_BY_ID = "SELECT `id`,`email`,`name`,`surname`,`phoneNumber`,`password`,`role`" +
            " FROM `users` WHERE `id`=?";
    private static final String FIND_USER_COOKIE_BY_ID = "SELECT `logInCookie` FROM `users` WHERE `id`=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM `users` WHERE `id`=?";
    private static final String INSERT_USER = "INSERT INTO `users` " +
            "(email, name, surname, phoneNumber, password, role) VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_USER = "UPDATE users " +
            "SET  email=?, name=?, surname=?, phoneNumber=?, password=?, role=? WHERE id=?";
    private static final String UPDATE_USER_COOKIE = "UPDATE `users` SET logInCookie=? WHERE id=?";

    private static final String USER_ID_ATTRIBUTE = "id";
    private static final String USER_EMAIL_ATTRIBUTE = "email";
    private static final String USER_NAME_ATTRIBUTE = "name";
    private static final String USER_SURNAME_ATTRIBUTE = "surname";
    private static final String USER_PHONE_NUMBER_ATTRIBUTE = "phoneNumber";
    private static final String USER_PASSWORD_ATTRIBUTE = "password";
    private static final String LOGIN_COOKIE_ATTRIBUTE = "logInCookie";
    private static final String USER_ROLE_ATTRIBUTE = "role";

    private static final ConnectionPool pool = ConnectionPool.getInstance();

    @Override
    public List<User> readAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                User user = resultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return users;
    }

    @Override
    public User readEntityById(Integer id) throws DaoException {
        User user = null;
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = this.resultSetToUser(rs);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public void delete(Integer id) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void create(User user) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER);
            userToSql(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER);
            userToSql(preparedStatement, user);
            preparedStatement.setInt(7, user.getUserId());
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateUserCookie(int userId, String UserCookie) throws DaoException {
        try (Connection connection = pool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_COOKIE);
            preparedStatement.setString(1, UserCookie);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User readByEmail(String email) throws DaoException {
        User user = null;
        try (Connection connection = pool.getConnection()) {
            ResultSet resultSet = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
                preparedStatement.setString(1, email);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    user = this.resultSetToUser(resultSet);
                }
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        } catch (SQLException |
                ConnectionPoolException e) {
            throw new DaoException(e);
        }
        return user;
    }

    @Override
    public User readByEmailWithCookie(String email) throws DaoException {
        User user = this.readByEmail(email);
        try (Connection connection = pool.getConnection()) {
            ResultSet resultSet = null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_COOKIE_BY_ID)) {
                preparedStatement.setInt(1, user.getUserId());
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    user.setUserCookie(resultSet.getString(LOGIN_COOKIE_ATTRIBUTE));
                }
                return user;
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException(e);
        }
    }

    private User resultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt(USER_ID_ATTRIBUTE));
        user.setEmail(rs.getString(USER_EMAIL_ATTRIBUTE));
        user.setName(rs.getString(USER_NAME_ATTRIBUTE));
        user.setSurname(rs.getString(USER_SURNAME_ATTRIBUTE));
        user.setPhoneNumber(rs.getString(USER_PHONE_NUMBER_ATTRIBUTE));
        user.setPassword(rs.getString(USER_PASSWORD_ATTRIBUTE));
        if (rs.getInt(USER_ROLE_ATTRIBUTE) == 0) {
            user.setRole(UserType.ADMIN);
        } else {
            user.setRole(UserType.CLIENT);
        }
        return user;
    }

    private void userToSql(PreparedStatement ps, User user) throws SQLException {
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getName());
        ps.setString(3, user.getSurname());
        ps.setString(4, user.getPhoneNumber());
        ps.setString(5, user.getPassword());
        switch (user.getRole()) {
            case ADMIN:
                ps.setInt(6, 0);
            case CLIENT:
                ps.setInt(6, 1);
            default:
                ps.setInt(6, 1);
        }
    }
}
