package by.urbel.hotel.service.impl;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.dao.UserDao;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.UserService;
import by.urbel.hotel.service.exception.UserException;

import java.util.Objects;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private final UserDao userDAO = DaoProvider.getInstance().getUserDao();

    @Override
    public User authorizationByEmailAndPassword(String email, String password) throws ServiceException {
        User user;
        try {
            user = userDAO.readByEmail(email);
            if (user == null) {
                throw new UserException("There isn't the user");
            } else if (!Objects.equals(user.getPassword(), password)) {
                throw new UserException("The password is wrong");
            } else {
                user.setPassword(null);
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User authorizationByCookieAndEmail(String userCookieStr, String email) throws ServiceException {
        try {
            User user = userDAO.readByEmailWithCookie(email);
            if (!Objects.equals(user.getUserCookie(), userCookieStr)) {
                throw new UserException("UserTokens doesn't equals ");
            } else {
                return user;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public void registration(User user) throws ServiceException {
        try {
            userDAO.create(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUser(User user) throws ServiceException {
        try {
            userDAO.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String updateUserCookie(int userId) throws ServiceException {
        String userCookie = UUID.randomUUID().toString();
        try {
            userDAO.updateUserCookie(userId, userCookie);
            return userCookie;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUserCookie(int userId) throws ServiceException {
        try {
            userDAO.updateUserCookie(userId, null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
