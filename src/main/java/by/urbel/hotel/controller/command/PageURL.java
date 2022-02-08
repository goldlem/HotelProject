package by.urbel.hotel.controller.command;

public final class PageURL {
    public static final String FIND_CATEGORIES_PAGE = "/controller?command=find_all_categories";
    public static final String FIND_RESERVATIONS_PAGE = "/controller?command=find_all_reservations";
    public static final String FIND_ROOMS_PAGE = "/controller?command=find_all_rooms";
    public static final String RESERVATIONS_PAGE = "/jsp/protected/reservations.jsp";
    public static final String CATEGORIES_PAGE = "/jsp/protected/categories.jsp";
    public static final String ROOMS_PAGE = "/jsp/protected/rooms.jsp";
    public static final String ADD_CATEGORY_PAGE = "/jsp/admin/addNewCategory.jsp";
    public static final String GO_TO_ADD_ROOM_PAGE = "/controller?command=go_to_add_room_page";
    public static final String ADD_ROOM_PAGE = "/jsp/admin/addNewRoom.jsp";
    public static final String AUTHORIZATION_PAGE = "/jsp/authorization.jsp";
    public static final String GO_TO_PROFILE_PAGE = "/controller?command=go_to_profile_page";
    public static final String PROFILE_PAGE = "/jsp/protected/profile.jsp";
    public static final String LOGOUT = "/controller?command=logout";
    public static final String BOOKING_PAGE = "/jsp/protected/booking.jsp";
    public static final String ERROR_PAGE = "/jsp/error/error.jsp";


    private PageURL() {
    }
}
