package by.urbel.hotel.controller.command.impl;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.PageURL;
import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.controller.command.RequestParameters;
import by.urbel.hotel.entity.RoomCategory;
import by.urbel.hotel.service.CategoryService;
import by.urbel.hotel.service.exception.ServiceException;
import by.urbel.hotel.service.provider.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FindFreeCategoriesByBedsNumber implements Command {
    private static final Logger LOGGER = LogManager.getLogger(FindFreeCategoriesByBedsNumber.class.getName());

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategoryService categoryService = ServiceProvider.getInstance().getCategoryService();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date checkIn = dateFormat.parse(request.getParameter(RequestParameters.CHECK_IN_DATE));
            Date checkOut = dateFormat.parse(request.getParameter(RequestParameters.CHECK_OUT_DATE));
            int bedsNumber = Integer.parseInt(request.getParameter(RequestParameters.NUMBER_OF_BEDS).trim());

            List<RoomCategory> categories = categoryService.readFreeCategoriesByBedsNumber(bedsNumber, checkIn, checkOut);
            LOGGER.debug(categories);

            request.getSession().setAttribute(RequestAttributes.CHECK_IN_DATE,checkIn);
            request.getSession().setAttribute(RequestAttributes.CHECK_OUT_DATE,checkOut);
            request.getSession().setAttribute(RequestAttributes.CATEGORIES, categories);
            response.sendRedirect(request.getContextPath()+PageURL.BOOKING_PAGE);
        } catch (ServiceException | ParseException e) {
            LOGGER.error("A server error while finding categories for booking", e);
            request.setAttribute(RequestAttributes.ERROR, e.getMessage());
            request.getRequestDispatcher(PageURL.ERROR_PAGE).forward(request, response);
        }
    }
}
