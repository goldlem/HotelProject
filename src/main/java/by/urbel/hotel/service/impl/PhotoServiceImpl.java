package by.urbel.hotel.service.impl;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.dao.PhotoDao;
import by.urbel.hotel.service.PhotoService;
import by.urbel.hotel.service.exception.ServiceException;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PhotoServiceImpl implements PhotoService {
    private final PhotoDao photoDao = DaoProvider.getInstance().getPhotoDao();

    private static final String ROOM_PHOTOS_PART = "categoryPhotos";

    @Override
    public List<String> uploadPhotos(Collection<Part> parts, String uploadFolderPath) throws ServiceException {
        List<String> photoPaths = new ArrayList<>();
        for (Part part : parts) {
            if (Objects.equals(part.getName(), ROOM_PHOTOS_PART)) {
                try {
                    photoPaths.add(photoDao.uploadPhoto(part, uploadFolderPath));
                } catch (DaoException e) {
                    throw new ServiceException(e);
                }
            }
        }
        return photoPaths;
    }

    @Override
    public void deletePhotos(List<String> paths) throws ServiceException {
        try {
            for (String photoPath : paths) {
                photoDao.deletePhoto(photoPath);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
