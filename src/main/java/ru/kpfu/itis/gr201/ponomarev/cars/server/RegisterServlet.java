package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.LoginAlreadyTakenException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.RegistrationException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "registerServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final UserDao userDao = new UserDao();
        final UserService userService = new UserServiceImpl(userDao);

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String remember = req.getParameter("remember");
        if (remember == null) {
            remember = "off";
        }

        User user = new User(
                firstName,
                lastName,
                email,
                null, // TODO: avatar url
                login,
                password
        );
        if (password.equals(confirmPassword)) {
            try {
                userService.save(user);
                userService.auth(user, remember.equalsIgnoreCase("on"), req, resp);
                resp.sendRedirect(req.getContextPath() + "/");
            } catch (RegistrationException e) {
                if (e instanceof EmailAlreadyRegisteredException) {
                    req.setAttribute(
                            "email_not_unique",
                            ((EmailAlreadyRegisteredException) e).getEmail()
                    );
                } else if (e instanceof LoginAlreadyTakenException) {
                    req.setAttribute(
                            "login_not_unique",
                            ((LoginAlreadyTakenException) e).getLogin()
                    );
                } else {
                    req.setAttribute("unknown_error", true);
                }
                req.setAttribute("past_first_name", user.getFirstName());
                req.setAttribute("past_last_name", user.getLastName());
                req.setAttribute("past_email", user.getEmail());
                req.setAttribute("past_login", user.getLogin());
                req.getRequestDispatcher("register.ftl").forward(req, resp);
            }
        } else {
            req.setAttribute("password_not_confirmed", true);
            req.setAttribute("past_first_name", user.getFirstName());
            req.setAttribute("past_last_name", user.getLastName());
            req.setAttribute("past_email", user.getEmail());
            req.setAttribute("past_login", user.getLogin());
            req.getRequestDispatcher("register.ftl").forward(req, resp);
        }
    }
}
