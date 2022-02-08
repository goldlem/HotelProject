package by.urbel.hotel.dao.impl;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.PhotoDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PhotoDaoImpl implements PhotoDao {
    private static final Logger LOGGER = LogManager.getLogger(PhotoDaoImpl.class.getName());

    private static final String SERVLET_PATH =
            "C:\\Users\\Nikita\\IdeaProjects\\hotelProject\\target\\hotelProject-1.0-SNAPSHOT\\";

    @Override
    public String uploadPhoto(Part photoPart, String uploadFolderPath) throws DaoException {
        File uploadDir = new File(uploadFolderPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = UUID.randomUUID().toString();
        String fileType = photoPart.getContentType();
        fileType = "." + fileType.substring(fileType.indexOf('/') + 1);
        String path = uploadFolderPath + File.separator + fileName + fileType;
        try {
            photoPart.write(path);
            path = path.substring(path.indexOf("uploads"));
            return path;
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deletePhoto(String photoPath) throws DaoException {
        File photo = new File(SERVLET_PATH + photoPath);
        if (!photo.delete()) {
            LOGGER.error("photo {} is not deleted", photoPath);
            throw new DaoException("photo " + photoPath + " is not deleted");
        }
    }
}
