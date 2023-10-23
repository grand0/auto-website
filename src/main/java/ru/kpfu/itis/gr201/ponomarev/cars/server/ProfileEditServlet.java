package ru.kpfu.itis.gr201.ponomarev.cars.server;

import org.json.JSONObject;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserNotAuthenticatedException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.util.CloudinaryUtil;
import ru.kpfu.itis.gr201.ponomarev.cars.util.PasswordUtil;
import ru.kpfu.itis.gr201.ponomarev.cars.util.ValidateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet(name="profileEditServlet", urlPatterns="/profile_edit")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 10 * 1024 * 1024)
public class ProfileEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("email", user.getEmail());
        req.getRequestDispatcher("profile_edit.ftl").forward(req, resp);
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

        User oldUser = (User) req.getSession().getAttribute("user");

        String email = req.getParameter("email");
        if (!ValidateUtil.validateEmail(email)) {
            jsonResponse.put("email_invalid", true);
            formValid = false;
        }

        String oldPassword = req.getParameter("oldPassword");

        String newPassword = req.getParameter("newPassword");
        if (newPassword != null && !newPassword.isEmpty() && !ValidateUtil.validatePassword(newPassword)) {
            jsonResponse.put("password_invalid", true);
            formValid = false;
        }

        String confirmPassword = req.getParameter("confirmPassword");

        if (!formValid) {
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
            return;
        }

        User newUser = new User(
                oldUser.getFirstName(),
                oldUser.getLastName(),
                oldUser.getEmail(),
                oldUser.getAvatarUrl(),
                oldUser.getLogin(),
                null
        );

        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            if (!oldUser.getPassword().equals(PasswordUtil.encrypt(oldPassword))) {
                jsonResponse.put("old_password_wrong", true);
                jsonResponse.put("success", false);
                resp.getWriter().write(jsonResponse.toString());
                return;
            } else if (!newPassword.equals(confirmPassword)) {
                jsonResponse.put("password_not_confirmed", true);
                jsonResponse.put("success", false);
                resp.getWriter().write(jsonResponse.toString());
                return;
            } else {
                newUser.setPassword(newPassword);
            }
        }

        if (avatarPart != null) {
            String newAvatarUrl = CloudinaryUtil.uploadPart(avatarPart);
            newUser.setAvatarUrl(newAvatarUrl);
        }

        newUser.setEmail(email);

        try {
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            userService.changeUserDetails(newUser, req, resp);
            jsonResponse.put("success", true);
            resp.getWriter().write(jsonResponse.toString());
        } catch (UserNotAuthenticatedException e) {
            jsonResponse.put("unauthorized", true);
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
        } catch (SaveException e) {
            if (e instanceof EmailAlreadyRegisteredException) {
                jsonResponse.put("email_not_unique", ((EmailAlreadyRegisteredException) e).getEmail());
            } else {
                jsonResponse.put("unknown_error", true);
            }
            jsonResponse.put("success", false);
            resp.getWriter().write(jsonResponse.toString());
        }
    }
}
