package by.urbel.hotel.service.impl;

import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.dao.RoomDao;
import by.urbel.hotel.dao.exception.ObjectAlreadyExistsException;
import by.urbel.hotel.dao.exception.ObjectIsUsedException;
import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.entity.RoomStatus;
import by.urbel.hotel.service.RoomService;
import by.urbel.hotel.service.exception.ServiceException;

import java.util.List;

public class RoomServiceImpl implements RoomService {
    private final RoomDao roomDao = DaoProvider.getInstance().getRoomDao();

    @Override
    public boolean addRoom(int roomNumber, String categoryName, RoomStatus status) throws ServiceException {
        try {
            RoomCategory category = DaoProvider.getInstance().getCategoryDao().readEntityById(categoryName);
            Room room = new Room(roomNumber, category, status);
            roomDao.create(room);
            return true;
        }catch (ObjectAlreadyExistsException e){
            return false;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> readAllRooms() throws ServiceException {
        List<Room> rooms;
        try {
            rooms = roomDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return rooms;
    }

    @Override
    public boolean removeRoom(String roomNumber) throws ServiceException {
        try {
            roomDao.delete(Integer.parseInt(roomNumber));
            return true;
        }catch (ObjectIsUsedException e){
            return false;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void blockRoom(int roomNumber) throws ServiceException {
        try {
            roomDao.updateRoomStatus(roomNumber, RoomStatus.BLOCKED);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void unblockRoom(int roomNumber) throws ServiceException {
        try {
            roomDao.updateRoomStatus(roomNumber,RoomStatus.AVAILABLE);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
