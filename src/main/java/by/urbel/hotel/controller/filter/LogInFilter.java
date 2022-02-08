package by.urbel.hotel.controller.filter;

import by.urbel.hotel.entity.User;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class LogInFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(LogInFilter.class.getName());

    private static final String USER = "user";
    private static final String USER_KEY = "userKey";
    private static final String USER_EMAIL = "userEmail";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession().getAttribute(USER) == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                String userKey = null;
                String userEmail = null;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(USER_KEY)) {
                        userKey = cookie.getValue();
                    } else if (cookie.getName().equals(USER_EMAIL)) {
                        userEmail = cookie.getValue();
                    }
                }
                if (userKey != null && userEmail != null) {
                    try {
                        User user = ServiceProvider.getInstance().getUserService().authorizationByCookieAndEmail(userKey, userEmail);
                        request.getSession().setAttribute(USER, user);
                        logger.debug("user is added in session");
                    } catch (ServiceException e) {
                        logger.info("RememberToken is invalid", e);
                        for (Cookie cookie : cookies) {
                            if (cookie.getName().equals(USER_KEY) || cookie.getName().equals(USER_EMAIL)) {
                                cookie.setValue(null);
                                cookie.setMaxAge(0);
                                response.addCookie(cookie);
                            }
                        }

                    }
                }
            }
        }
        chain.doFilter(servletRequest, servletResponse);
    }
}
