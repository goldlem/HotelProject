package by.urbel.hotel.controller.command.impl.admin;

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

public class FindAllReservations implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindAllReservations.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to find all reservations");
            response.sendRedirect(request.getContextPath());
            return;
        }
        ReservationService reservationService = ServiceProvider.getInstance().getReservationService();
        try {
            List<Reservation> reservationList = reservationService.readAllReservations();
            request.getSession().setAttribute(RequestAttributes.RESERVATIONS, reservationList);
            response.sendRedirect(request.getContextPath() + PageURL.RESERVATIONS_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("A server error while finding all reservations", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
