package by.urbel.hotel.controller.filter;

import by.urbel.hotel.controller.command.RequestAttributes;
import by.urbel.hotel.entity.User;
import by.urbel.hotel.entity.UserType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/jsp/admin/*"},
        initParams = {@WebInitParam(name = "indexPath", value = "/index.jsp")})
public class AdminJspSecurityFilter implements Filter {
    private String indexPath;

    private static final String INDEX_PATH_PARAMETER = "indexPath";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter(INDEX_PATH_PARAMETER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        User user = (User) httpRequest.getSession().getAttribute(RequestAttributes.USER);
        if (user == null || !user.getRole().equals(UserType.ADMIN)) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        } else {
            chain.doFilter(request, response);
        }
    }
    @Override
    public void destroy() {
        indexPath = null;
    }
}
