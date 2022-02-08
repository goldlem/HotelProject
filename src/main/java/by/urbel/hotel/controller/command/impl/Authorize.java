package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import by.urbel.hotel.service.UserService;
import by.urbel.hotel.service.exception.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Authorize implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Authorize.class.getName());

    private static final String USER_KEY = "userKey";
    private static final String USER_EMAIL = "userEmail";
    private static final int ONE_MONTH_PERIOD = 2_678_400;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute(RequestAttributes.USER) != null) {
            LOGGER.info("User exists in session");
            response.sendRedirect(request.getContextPath());
            return;
        }
        LOGGER.info("User wants to sign in");
        String email = request.getParameter(RequestParameters.EMAIL);
        String password = request.getParameter(RequestParameters.PASSWORD);
        String rememberUserStr = request.getParameter(RequestParameters.REMEMBER_USER);

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        try {
            User user = userService.authorizationByEmailAndPassword(email, password);
            LOGGER.info("User exist");
            HttpSession session = request.getSession();
            session.setAttribute(RequestAttributes.USER, user);
            if (rememberUserStr != null) {
                Cookie rememberUserCookie = new Cookie(USER_KEY, userService.updateUserCookie(user.getUserId()));
                rememberUserCookie.setMaxAge(ONE_MONTH_PERIOD);
                response.addCookie(rememberUserCookie);
                rememberUserCookie = new Cookie(USER_EMAIL, user.getEmail());
                rememberUserCookie.setMaxAge(ONE_MONTH_PERIOD);
                response.addCookie(rememberUserCookie);
            }
            response.sendRedirect(request.getContextPath());

        } catch (UserException e) {
            LOGGER.info("There isn't user or wrong password");
            request.getSession().setAttribute(RequestAttributes.ERROR, "Wrong email or password");
            response.sendRedirect(request.getContextPath()+PageURL.AUTHORIZATION_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("A server error while sign in", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
