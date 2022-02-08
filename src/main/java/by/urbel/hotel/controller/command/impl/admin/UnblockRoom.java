package by.urbel.hotel.controller.command.impl.admin;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
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

public class UnblockRoom implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UnblockRoom.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to block room");
            response.sendRedirect(request.getContextPath());
        } else {
            int roomNumber = Integer.parseInt(request.getParameter(RequestParameters.ROOM_NUMBER));
            RoomService roomService = ServiceProvider.getInstance().getRoomService();
            try {
                roomService.unblockRoom(roomNumber);
                LOGGER.info("Room number {} is unblocked", roomNumber);
                response.sendRedirect(request.getContextPath() + PageURL.FIND_ROOMS_PAGE);
            } catch (ServiceException e) {
                LOGGER.error("A server error while unblocking room", e);
                request.setAttribute(RequestAttributes.ERROR, e.getMessage());
                request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
            }
        }
    }
}
