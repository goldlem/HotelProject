package by.urbel.hotel.dao.provider;

import by.urbel.hotel.dao.*;
import by.urbel.hotel.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();

    private final UserDao userDao = new UserDaoMySql();
    private final RoomDao roomDao = new RoomDaoMySql();
    private final CategoryDao categoryDao = new CategoryDaoMySql();
    private final ReservationDao reservationDao = new ReservationDaoMySql();
    private final PhotoDao photoDao = new PhotoDaoImpl();


    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    public PhotoDao getPhotoDao() {
        return photoDao;
    }
}
