package by.urbel.hotel.dao;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.entity.User;

public interface UserDao extends Dao<User, Integer> {
    void updateUserCookie(int id, String UserCookie) throws DaoException;
    User readByEmail(String email) throws DaoException;
    User readByEmailWithCookie(String email) throws DaoException;
}
