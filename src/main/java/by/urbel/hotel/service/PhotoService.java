package by.urbel.hotel.service;

import by.urbel.hotel.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.Collection;
import java.util.List;

public interface PhotoService {
    List<String> uploadPhotos(Collection<Part> parts, String uploadFolderPath) throws ServiceException;
    void deletePhotos(List<String> paths) throws ServiceException;
}
