package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "profileServlet", urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");

        // TODO: replace with filter
        if (userService.isAuthed(req, resp)) {
            req.getRequestDispatcher("profile.ftl").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
