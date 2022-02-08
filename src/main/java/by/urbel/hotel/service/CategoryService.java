package by.urbel.hotel.service;

import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface CategoryService {
    boolean createRoomCategory(RoomCategory roomCategory) throws ServiceException;
    List<RoomCategory> readAllRoomCategories() throws ServiceException;
    void updateRoomCategory(RoomCategory roomCategory) throws ServiceException;
    boolean deleteRoomCategory(String categoryName) throws ServiceException;
    List<RoomCategory> readFreeCategoriesByBedsNumber(int bedsNumber, Date checkIn, Date checkOut) throws ServiceException;
}
