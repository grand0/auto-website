package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "authServlet", urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();
    private final UserService userService = new UserServiceImpl(userDao);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("logout")) {
            userService.logout(req, resp);
            resp.sendRedirect(req.getContextPath() + "/");
        } else if (req.getSession(false) != null) {
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.getRequestDispatcher("auth.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("logout")) {
            userService.logout(req, resp);
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        if (remember == null) {
            remember = "off";
        }
        if (login != null && password != null) {
            User user = userDao.getByLoginAndPassword(login, password);
            if (user != null) {
                userService.auth(user, remember.equalsIgnoreCase("on"), req, resp);
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
        }
        req.setAttribute("unauthorized", true);
        req.getRequestDispatcher("auth.ftl").forward(req, resp);
    }
}
