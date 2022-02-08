package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.dao.exception.DaoException;
import by.urbel.hotel.dao.provider.DaoProvider;
import by.urbel.hotel.dao.ReservationDao;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteReservation implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteReservation.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (user == null) {
            LOGGER.warn("Null user wanted to delete reservation");
            response.sendRedirect(request.getContextPath() + PageURL.AUTHORIZATION_PAGE);
        } else {
            int reservationId = Integer.parseInt(request.getParameter(RequestParameters.RESERVATION_ID));

            ReservationDao reservationDao = DaoProvider.getInstance().getReservationDao();
            try {
                reservationDao.delete(reservationId);
                LOGGER.info("Reservation with id {} successfully is deleted", reservationId);
                if (user.getRole().equals(UserType.CLIENT)) {
                    response.sendRedirect(request.getContextPath() + PageURL.GO_TO_PROFILE_PAGE);
                } else if (user.getRole().equals(UserType.ADMIN)) {
                    response.sendRedirect(request.getContextPath() + PageURL.FIND_RESERVATIONS_PAGE);
                } else {
                    response.sendRedirect(request.getContextPath());
                }
            } catch (DaoException e) {
                LOGGER.error("A server error while deleting reservation", e);
                request.setAttribute(RequestAttributes.ERROR, e.getMessage());
                request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
            }
        }
    }
}
