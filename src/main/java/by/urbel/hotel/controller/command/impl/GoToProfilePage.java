package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.entity.Reservation;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import by.urbel.hotel.service.ReservationService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToProfilePage implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToProfilePage.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if(!user.getRole().equals(UserType.CLIENT)){
            LOGGER.error("Not client wanted to go to profile page");
            response.sendRedirect(request.getContextPath());
            return;
        }
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        try {
            List<Reservation> reservations = reservationService.readReservationsByUser(user);

            request.getSession().setAttribute(RequestAttributes.RESERVATIONS,reservations);
            response.sendRedirect(request.getContextPath()+ PageURL.PROFILE_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("A server error while reading reservations for client", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
