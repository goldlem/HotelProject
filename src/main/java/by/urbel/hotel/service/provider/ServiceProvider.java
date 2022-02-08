package by.urbel.hotel.service.provider;

import by.urbel.hotel.service.*;
import by.urbel.hotel.service.impl.*;

public final class ServiceProvider {

    private static final ServiceProvider instance = new ServiceProvider();

    private ServiceProvider() {
    }

    private final UserService userService = new UserServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();
    private final CategoryService categoryService = new CategoryServiceImpl();
    private final ReservationService reservationService = new ReservationServiceImpl();
    private final PhotoService photoService = new PhotoServiceImpl();

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public PhotoService getPhotoService() {
        return photoService;
    }
}
