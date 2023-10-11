package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.LoginAlreadyTakenException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserSaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.UserServiceImpl;
import ru.kpfu.itis.gr201.ponomarev.cars.util.CloudinaryUtil;
import ru.kpfu.itis.gr201.ponomarev.cars.util.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name = "registerServlet", urlPatterns = "/register")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final UserDao userDao = new UserDao();
        final UserService userService = new UserServiceImpl(userDao);

        boolean formValid = true;

        Part avatarPart = null;
        try {
            avatarPart = req.getPart("avatar");
        } catch (IllegalStateException e) {
            req.setAttribute("avatar_too_big", true);
            formValid = false;
        }

        if (avatarPart != null) {
            if (avatarPart.getContentType().equals("application/octet-stream")) {
                avatarPart = null;
            } else if (!avatarPart.getContentType().startsWith("image/")) {
                req.setAttribute("avatar_unsupported_format", true);
                formValid = false;
            }
        }

        String firstName = req.getParameter("firstName");
        if (ValidateUtil.validateName(firstName)) {
            req.setAttribute("first_name_invalid", true);
            formValid = false;
        }

        String lastName = req.getParameter("lastName");
        if (ValidateUtil.validateName(lastName)) {
            req.setAttribute("last_name_invalid", true);
            formValid = false;
        }

        String email = req.getParameter("email");
        if (ValidateUtil.validateEmail(email)) {
            req.setAttribute("email_invalid", true);
            formValid = false;
        }

        String login = req.getParameter("login");
        if (ValidateUtil.validateLogin(login)) {
            req.setAttribute("login_invalid", true);
            formValid = false;
        }

        String password = req.getParameter("password");
        if (ValidateUtil.validatePassword(password)) {
            req.setAttribute("password_invalid", true);
            formValid = false;
        }

        String confirmPassword = req.getParameter("confirmPassword");

        String remember = req.getParameter("remember");
        if (remember == null) {
            remember = "off";
        }

        if (!formValid) {
            showPage(req, resp);
        } else if (password.equals(confirmPassword)) {
            String avatarUrl = null;
            if (avatarPart != null) {
                avatarUrl = CloudinaryUtil.uploadPart(avatarPart);
            }
            User user = new User(
                firstName,
                lastName,
                email,
                avatarUrl,
                login,
                password
            );
            try {
                userService.save(user);
                userService.auth(user, remember.equalsIgnoreCase("on"), req, resp);
                resp.sendRedirect(req.getContextPath() + "/");
            } catch (UserSaveException e) {
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
                showPage(req, resp);
            }
        } else {
            req.setAttribute("password_not_confirmed", true);
            showPage(req, resp);
        }
    }

    private void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("past_first_name", req.getParameter("firstName"));
        req.setAttribute("past_last_name", req.getParameter("lastName"));
        req.setAttribute("past_email", req.getParameter("email"));
        req.setAttribute("past_login", req.getParameter("login"));
        req.getRequestDispatcher("register.ftl").forward(req, resp);
    }
}
