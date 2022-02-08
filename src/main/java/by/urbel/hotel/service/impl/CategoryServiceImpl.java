package by.urbel.hotel.service.impl;

import by.urbel.hotel.dao.CategoryDao;
import by.urbel.hotel.dao.exception.ObjectAlreadyExistsException;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.dao.PhotoDao;
import by.urbel.hotel.dao.exception.ObjectIsUsedException;
import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.service.CategoryService;
import by.urbel.hotel.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private static final Logger LOGGER = LogManager.getLogger(CategoryServiceImpl.class.getName());

    private final CategoryDao categoryDao = DaoProvider.getInstance().getCategoryDao();

    @Override
    public boolean createRoomCategory(RoomCategory roomCategory) throws ServiceException {
        try {
            categoryDao.create(roomCategory);
            return true;
        }catch (ObjectAlreadyExistsException e){
            LOGGER.error("Category {} already exists",roomCategory.getCategoryName(),e);
            return false;
        } catch (DaoException e) {
            LOGGER.error("Error while creating category", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RoomCategory> readAllRoomCategories() throws ServiceException {
        try {
            return categoryDao.readAll();
        } catch (DaoException e) {
            LOGGER.error("Error while reading all categories", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateRoomCategory(RoomCategory roomCategory) throws ServiceException {
        try {
            categoryDao.update(roomCategory);
        } catch (DaoException e) {
            LOGGER.error("Error while updating category {}",roomCategory.getCategoryName(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteRoomCategory(String categoryName) throws ServiceException {
        try {
            PhotoDao photoDao = DaoProvider.getInstance().getPhotoDao();
            RoomCategory category = categoryDao.readEntityById(categoryName);
            categoryDao.delete(categoryName);
            for (String path : category.getPhotoPaths()) {
                photoDao.deletePhoto(path);
            }
            return true;
        }catch (ObjectIsUsedException e){
            LOGGER.error("Category {} is used",categoryName, e);
            return false;
        } catch (DaoException e) {
            LOGGER.error("Error while deleting category {}", categoryName, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<RoomCategory> readFreeCategoriesByBedsNumber(int bedsNumber, Date checkIn, Date checkOut)
            throws ServiceException {
        try {
            List<RoomCategory> categories =
                    categoryDao.readFreeCategoriesByDatesAndBedsNumber(bedsNumber, checkIn, checkOut);
            for (RoomCategory category : categories) {
                for (String path : category.getPhotoPaths()) {
                   path = path.substring(path.indexOf("uploads"));
                }
            }
            return categories;
        } catch (DaoException e) {
            LOGGER.error("Error while finding free categories", e);
            throw new ServiceException(e);
        }
    }
}
