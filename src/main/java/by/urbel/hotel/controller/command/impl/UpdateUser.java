package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import by.urbel.hotel.service.provider.ServiceProvider;
import by.urbel.hotel.service.UserService;
import by.urbel.hotel.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateUser implements Command {
    private static final Logger LOGGER = LogManager.getLogger(UpdateUser.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter(RequestParameters.USER_NAME).trim();
        String surname = request.getParameter(RequestParameters.USER_SURNAME).trim();
        String email = request.getParameter(RequestParameters.EMAIL).trim();
        String phoneNumber = request.getParameter(RequestParameters.PHONE_NUMBER).trim();
        String password = request.getParameter(RequestParameters.PASSWORD).trim();

        User user = new User();
        User userFromSession = (User) request.getSession().getAttribute(RequestAttributes.USER);
        user.setName(name);
        user.setSurname(surname);
        user.setUserId(userFromSession.getUserId());
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRole(UserType.CLIENT);

        UserService userService = ServiceProvider.getInstance().getUserService();
        try {
            userService.updateUser(user);
            LOGGER.info("User {} has updated",user.getUserId());
            response.sendRedirect(request.getContextPath() + PageURL.LOGOUT);
        } catch (ServiceException e) {
            LOGGER.error("A server error while updating user", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
