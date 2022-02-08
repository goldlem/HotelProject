package by.urbel.hotel.controller.command.impl.admin;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import by.urbel.hotel.service.CategoryService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAddRoomPage implements Command {
    private static final Logger LOGGER = LogManager.getLogger(GoToAddRoomPage.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to go to adding room page");
            response.sendRedirect(request.getContextPath());
            return;
        }
        CategoryService categoryService = ServiceProvider.getInstance().getCategoryService();
        try {
            List<RoomCategory> categories = categoryService.readAllRoomCategories();
            request.getSession().setAttribute(RequestAttributes.CATEGORIES, categories);
            response.sendRedirect(request.getContextPath()+ PageURL.ADD_ROOM_PAGE);
        } catch (ServiceException e) {
            LOGGER.error("A server error while reading all categories", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }

    }
}
