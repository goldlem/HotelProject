package by.urbel.hotel.service;

import by.urbel.hotel.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.Collection;
import java.util.List;

public interface PhotoService {
    /**
     * Uploads photos from parts to server.
     *
     * @param parts            Collection of {@link Part} objects
     * @param uploadFolderPath name of folder for uploading
     * @throws ServiceException if IOExceptions occurs.
     */
    List<String> uploadPhotos(Collection<Part> parts, String uploadFolderPath) throws ServiceException;

    /**
     * Removes photos from server.
     *
     * @param paths List of paths from server root to photos
     * @throws ServiceException if some photos didn't get deleted.
     */
    void deletePhotos(List<String> paths) throws ServiceException;
}
