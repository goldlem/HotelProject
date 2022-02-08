package by.urbel.hotel.controller.command.impl.admin;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.RoomStatus;
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

public class AddNewRoom implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddNewRoom.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to add new room");
            response.sendRedirect(request.getContextPath());
            return;
        }
        int roomNumber = Integer.parseInt(request.getParameter(RequestParameters.ROOM_NUMBER));
        String categoryName = request.getParameter(RequestParameters.CATEGORY_NAME);
        RoomStatus roomStatus = RoomStatus.valueOf(request.getParameter(RequestParameters.ROOM_STATUS).toUpperCase());

        RoomService roomService = ServiceProvider.getInstance().getRoomService();
        try {
            boolean isCreated = roomService.addRoom(roomNumber, categoryName, roomStatus);
            if (isCreated) {
                LOGGER.info("room with number {} is added",roomNumber);
                response.sendRedirect(request.getContextPath() + PageURL.FIND_ROOMS_PAGE);
            } else {
                request.getSession().setAttribute(RequestAttributes.ERROR, "This room number is already exists");
                response.sendRedirect(request.getContextPath() + PageURL.GO_TO_ADD_ROOM_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("A server error while adding room with number {}", roomNumber, e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
