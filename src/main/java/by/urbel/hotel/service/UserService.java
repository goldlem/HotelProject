package by.urbel.hotel.service;

import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;

public interface UserService {
    /**
     * Authorization user by email and password.
     *
     * @param email    email of user.
     * @param password password of user.
     * @return User if the user was successfully authorization.
     * @throws ServiceException if an SQL error occurs or wrong password or email.
     */
    User authorizationByEmailAndPassword(String email, String password) throws ServiceException;

    /**
     * Authorization user by email and password.
     *
     * @param userToken UID from cookies for sign in.
     * @param email     email of user.
     * @return User if the user was successfully authorization.
     * @throws ServiceException if an SQL error occurs or userToken from db not equals with token from cookies.
     */
    User authorizationByCookieAndEmail(String userToken, String email) throws ServiceException;

    /**
     * Registration user.
     *
     * @param user user for registration.
     * @throws ServiceException if SQL or connection errors occur.
     */
    void registration(User user) throws ServiceException;

    /**
     * Updating user.
     *
     * @param user user for updating with new data.
     * @throws ServiceException if an SQL error occurs.
     */
    void updateUser(User user) throws ServiceException;

    /**
     * Updating userToken from db.
     *
     * @param userId id of user for which the token is being updated.
     * @throws ServiceException if an SQL error occurs.
     */
    String updateUserToken(int userId) throws ServiceException;

    /**
     * Deleting userToken from db.
     *
     * @param userId id of user for which the token is being updated.
     * @throws ServiceException if an SQL error occurs.
     */
    void deleteUserToken(int userId) throws ServiceException;
}
