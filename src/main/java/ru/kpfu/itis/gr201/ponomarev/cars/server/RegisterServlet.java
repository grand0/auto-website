package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.LoginAlreadyTakenException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserSaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
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
        resp.setContentType("application/json");

        boolean formValid = true;
        JSONObject jsonResponse = new JSONObject();

        Part avatarPart = null;
        try {
            avatarPart = req.getPart("avatar");
        } catch (IllegalStateException e) {
            jsonResponse.put("avatar_too_big", true);
            formValid = false;
        }

        if (avatarPart != null) {
            if (avatarPart.getContentType() == null || avatarPart.getContentType().equals("application/octet-stream")) {
                avatarPart = null;
            } else if (!avatarPart.getContentType().startsWith("image/")) {
                jsonResponse.put("avatar_unsupported_format", true);
                formValid = false;
            }
        }

        String firstName = req.getParameter("firstName");
        if (!ValidateUtil.validateName(firstName)) {
            jsonResponse.put("first_name_invalid", true);
            formValid = false;
        }

        String lastName = req.getParameter("lastName");
        if (!ValidateUtil.validateName(lastName)) {
            jsonResponse.put("last_name_invalid", true);
            formValid = false;
        }

        String email = req.getParameter("email");
        if (!ValidateUtil.validateEmail(email)) {
            jsonResponse.put("email_invalid", true);
            formValid = false;
        }

        String login = req.getParameter("login");
        if (!ValidateUtil.validateLogin(login)) {
            jsonResponse.put("login_invalid", true);
            formValid = false;
        }

        String password = req.getParameter("password");
        if (!ValidateUtil.validatePassword(password)) {
            jsonResponse.put("password_invalid", true);
            formValid = false;
        }

        String confirmPassword = req.getParameter("confirmPassword");

        String remember = req.getParameter("remember");
        if (remember == null) {
            remember = "off";
        }

        if (!formValid) {
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
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
                UserService userService = (UserService) getServletContext().getAttribute("userService");
                userService.save(user);
                userService.auth(user, remember.equalsIgnoreCase("on"), req, resp);
                jsonResponse.put("success", true);
                resp.getWriter().write(jsonResponse.toString());
            } catch (UserSaveException e) {
                if (e instanceof EmailAlreadyRegisteredException) {
                    jsonResponse.put("email_not_unique", ((EmailAlreadyRegisteredException) e).getEmail());
                } else if (e instanceof LoginAlreadyTakenException) {
                    jsonResponse.put("login_not_unique", ((LoginAlreadyTakenException) e).getLogin());
                } else {
                    jsonResponse.put("unknown_error", true);
                }
                jsonResponse.put("success", false);
                resp.getWriter().write(jsonResponse.toString());
            }
        } else {
            jsonResponse.put("password_not_confirmed", true);
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
        }
    }
}
