package by.urbel.hotel.controller.command.impl.admin;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.entity.Room;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import by.urbel.hotel.service.RoomService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class FindAllRooms implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindAllRooms.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to find all rooms");
            response.sendRedirect(request.getContextPath());
            return;
        }
        RoomService roomService = ServiceProvider.getInstance().getRoomService();
        try {
            List<Room> rooms = roomService.readAllRooms();
            if (rooms.isEmpty()) {
                LOGGER.info("There aren't rooms in database");
                request.getRequestDispatcher(PageURL.GO_TO_ADD_ROOM_PAGE).forward(request, response);
                return;
            }
            request.getSession().setAttribute(RequestAttributes.ROOMS,rooms);
            response.sendRedirect(request.getContextPath()+PageURL.ROOMS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("A server error while finding all rooms", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
