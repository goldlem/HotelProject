package by.urbel.hotel.service;

import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.RoomStatus;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.List;

public interface RoomService {
    /**
     * Finds all rooms from database.
     *
     * @return List of found rooms.
     * @throws ServiceException if an SQL error occurs.
     */
    List<Room> readAllRooms() throws ServiceException;

    /**
     * Adds new room to database.
     *
     * @param roomNumber   the number of room.
     * @param categoryName the name of room's category.
     * @param status       {@link RoomStatus} the status of room: AVAILABLE or BLOCKED.
     * @return true - if room is added, false - if room isn't added.
     * @throws ServiceException if an SQL error occurs.
     */
    boolean addRoom(int roomNumber, String categoryName, RoomStatus status) throws ServiceException;

    /**
     * Removes room from database.
     *
     * @param roomNumber the number of room.
     * @return true - if room is deleted, false - if room isn't deleted.
     * @throws ServiceException if an SQL error occurs.
     */
    boolean removeRoom(String roomNumber) throws ServiceException;

    /**
     * Blocks room. After room can't be booked.
     *
     * @param roomNumber the number of room.
     * @throws ServiceException if an SQL error occurs.
     */
    void blockRoom(int roomNumber) throws ServiceException;

    /**
     * Unblocks room. After room can be booked.
     *
     * @param roomNumber the number of room.
     * @throws ServiceException if an SQL error occurs.
     */
    void unblockRoom(int roomNumber) throws ServiceException;
}
