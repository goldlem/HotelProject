package by.urbel.hotel.controller.command.impl.admin;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteRoom implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteRoom.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to delete room");
            response.sendRedirect(request.getContextPath());
        } else {
            try {
                String roomNumber = request.getParameter(RequestParameters.ROOM_NUMBER);
                boolean isDeleted = ServiceProvider.getInstance().getRoomService().removeRoom(roomNumber);
                if (!isDeleted) {
                    request.getSession().setAttribute(RequestAttributes.ERROR, "Error: This room is reserved");
                }
                response.sendRedirect(request.getContextPath() + PageURL.FIND_ROOMS_PAGE);
            } catch (ServiceException e) {
                LOGGER.error("A server error while deleting room", e);
                request.setAttribute(RequestAttributes.ERROR, e.getMessage());
                request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
            }
        }
    }
}
