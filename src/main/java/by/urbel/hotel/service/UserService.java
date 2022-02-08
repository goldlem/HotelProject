package by.urbel.hotel.service;

import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;

public interface UserService {
    User authorizationByEmailAndPassword(String email, String password) throws ServiceException;
    User authorizationByCookieAndEmail(String userCookieStr, String email) throws ServiceException;
    void registration(User user) throws ServiceException;
    void updateUser(User user) throws ServiceException;
    String updateUserCookie(int userId) throws ServiceException;
    void deleteUserCookie(int userId) throws ServiceException;
}
