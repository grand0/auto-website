package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "authServlet", urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("logout")) {
            logoutAndRedirectToMainPage(req, resp);
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
            logoutAndRedirectToMainPage(req, resp);
            return;
        }

        resp.setContentType("application/json");

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        if (remember == null) {
            remember = "off";
        }
        if (login != null && password != null) {
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            User user = userService.getByLoginAndPassword(login, password);
            if (user != null) {
                userService.auth(user, remember.equalsIgnoreCase("on"), req, resp);
                resp.getWriter().write("{\"success\":1}");
                return;
            }
        }
        resp.getWriter().write("{\"success\":0,\"unauthorized\":1}");
    }

    private void logoutAndRedirectToMainPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        userService.logout(req, resp);
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
