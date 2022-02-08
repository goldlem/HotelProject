package by.urbel.hotel.dao;

import by.urbel.hotel.dao.exception.DaoException;

import javax.servlet.http.Part;

public interface PhotoDao {
    String uploadPhoto(Part photoPart, String uploadFolderPath) throws DaoException;

    void deletePhoto(String photoPath) throws DaoException;
}
