package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.ReservationService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class ReserveRoom implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ReserveRoom.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (user == null) {
            LOGGER.warn("Null user wanted to reserve room");
            response.sendRedirect(request.getContextPath() + PageURL.AUTHORIZATION_PAGE);
            return;
        }
        String categoryName = request.getParameter(RequestParameters.CATEGORY_NAME);
        Date checkIn = (Date) request.getSession().getAttribute(RequestParameters.CHECK_IN_DATE);
        Date checkOut = (Date) request.getSession().getAttribute(RequestParameters.CHECK_OUT_DATE);
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        try {
            reservationService.createReservation(user,checkIn,checkOut, new Date(),categoryName);
            LOGGER.info("User {} successfully has reserved room with category {}",user.getUserId(),categoryName);
            response.sendRedirect(request.getContextPath()+PageURL.GO_TO_PROFILE_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("A server error while reserving room", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
