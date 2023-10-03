package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "authServlet", urlPatterns = "/auth")
public class AuthServlet extends HttpServlet {
    public static final String LOGIN = "root";
    public static final String PASSWORD = "toor";

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null && action.equalsIgnoreCase("logout")) {
            req.getSession().invalidate();
            resp.sendRedirect("/");
        } else if (req.getSession(false) != null) {
            resp.sendRedirect("/");
        } else {
            req.getRequestDispatcher("auth.ftl").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login.equals(LOGIN) && password.equals(PASSWORD)) {
            LOGGER.info("User {} logged in", login);

            HttpSession session = req.getSession();
            session.setAttribute("username", login);
            session.setMaxInactiveInterval(60 * 60);

            resp.sendRedirect("/");
        } else {
            req.setAttribute("unauthorized", true);
            req.getRequestDispatcher("auth.ftl").forward(req, resp);
        }
    }
}
