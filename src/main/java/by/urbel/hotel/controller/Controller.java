package by.urbel.hotel.controller;

import by.urbel.hotel.controller.command.Command;
import by.urbel.hotel.controller.command.provider.CommandProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100)
@WebServlet("/controller")
public class Controller extends HttpServlet{
    private static final String COMMAND = "command";

    private final CommandProvider provider = new CommandProvider();

    public Controller() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String name = request.getParameter(COMMAND);
        Command command = provider.takeCommand(name);
        try {
            command.execute(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
