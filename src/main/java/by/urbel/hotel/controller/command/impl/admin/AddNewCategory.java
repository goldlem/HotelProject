package by.urbel.hotel.controller.command.impl.admin;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;
import by.urbel.hotel.service.CategoryService;
import by.urbel.hotel.service.PhotoService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class AddNewCategory implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddNewCategory.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(RequestAttributes.USER);
        if (!user.getRole().equals(UserType.ADMIN)) {
            LOGGER.warn("Not admin wanted to add new category");
            response.sendRedirect(request.getContextPath());
            return;
        }
        String categoryName = request.getParameter(RequestParameters.CATEGORY_NAME);
        BigDecimal roomPrice = new BigDecimal(request.getParameter(RequestParameters.ROOM_PRICE));
        int bedsNumber = Integer.parseInt(request.getParameter(RequestParameters.NUMBER_OF_BEDS));
        Collection<Part> partList = request.getParts();
        String uploadFolderPath = request.getRealPath("/uploads");

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        CategoryService categoryService = serviceProvider.getCategoryService();
        PhotoService photoService = serviceProvider.getPhotoService();
        try {
            List<String> photoPaths = photoService.uploadPhotos(partList, uploadFolderPath);
            boolean isCreated = categoryService.createRoomCategory(new RoomCategory(categoryName, roomPrice, bedsNumber, photoPaths));
            if (isCreated) {
                LOGGER.info("Category {} is added", categoryName);
                response.sendRedirect(request.getContextPath() + PageURL.FIND_CATEGORIES_PAGE);
            } else {
                photoService.deletePhotos(photoPaths);
                request.getSession().setAttribute(RequestAttributes.ERROR, "Category with this name is already exists");
                response.sendRedirect(request.getContextPath() + PageURL.ADD_CATEGORY_PAGE);
            }
        } catch (ServiceException e) {
            LOGGER.error("A server error while adding category {}", categoryName, e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
