package by.urbel.hotel.service;

import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface CategoryService {
    /**
     * Adds category to database.
     *
     * @param roomCategory category of room for adding.
     * @return true - if category is added, false - if category isn't added.
     * @throws ServiceException if an SQL error occurs.
     */
    boolean createRoomCategory(RoomCategory roomCategory) throws ServiceException;

    /**
     * Finds all categories from database.
     *
     * @return List of found categories.
     * @throws ServiceException if an SQL error occurs.
     */
    List<RoomCategory> readAllRoomCategories() throws ServiceException;

    /**
     * Updates category.
     *
     * @param roomCategory category for updating with new data.
     * @throws ServiceException if an SQL error occurs.
     */
    void updateRoomCategory(RoomCategory roomCategory) throws ServiceException;

    /**
     * Removes category from database.
     *
     * @param categoryName name of category for removing.
     * @return true - if category is successfully removed, false - if category isn't removed.
     * @throws ServiceException if an SQL error occurs.
     */
    boolean deleteRoomCategory(String categoryName) throws ServiceException;

    /**
     * Finds free categories on selected dates from database.
     *
     * @param bedsNumber number of places for sleep.
     * @param checkIn    date for check into hotel.
     * @param checkOut   date for check out of hotel.
     * @return List of found categories.
     * @throws ServiceException if an SQL error occurs.
     */
    List<RoomCategory> readFreeCategoriesByBedsNumber(int bedsNumber, Date checkIn, Date checkOut) throws ServiceException;
}
