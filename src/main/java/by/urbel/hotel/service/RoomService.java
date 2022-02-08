package by.urbel.hotel.service;

import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.RoomStatus;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.List;

public interface RoomService {

    List<Room> readAllRooms() throws ServiceException;
    boolean addRoom(int roomNumber, String categoryName, RoomStatus status) throws ServiceException;
    boolean removeRoom(String roomNumber) throws ServiceException;
    void blockRoom(int roomNumber) throws ServiceException;
    void unblockRoom(int roomNumber) throws ServiceException;
}
