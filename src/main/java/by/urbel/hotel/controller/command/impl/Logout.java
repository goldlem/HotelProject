package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Logout implements Command {
    private static final Logger LOGGER = LogManager.getLogger(Logout.class.getName());

    private static final String USER_KEY = "userKey";
    private static final String USER_EMAIL = "userEmail";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(RequestAttributes.USER);
        if (user == null) {
            LOGGER.warn("Null user wanted to logout");
            response.sendRedirect(request.getContextPath());
            return;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(USER_KEY) || cookie.getName().equals(USER_EMAIL)) {
                    cookie.setMaxAge(0);
                    cookie.setValue(null);
                    response.addCookie(cookie);
                    try {
                        ServiceProvider.getInstance().getUserService().deleteUserToken(user.getUserId());
                    } catch (ServiceException e) {
                        LOGGER.warn("Cookie is not deleted in DB for user {}",user.getUserId(), e);
                    }
                }
            }
        }
        session.removeAttribute(RequestAttributes.USER);
        LOGGER.info("User session closed");

        response.sendRedirect(request.getContextPath() + PageURL.AUTHORIZATION_PAGE);
        LOGGER.info("User logged out");
    }
}
