package ru.kpfu.itis.gr201.ponomarev.cars.server;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserNotAuthenticatedException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserSaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.impl.UserServiceImpl;
import ru.kpfu.itis.gr201.ponomarev.cars.util.CloudinaryUtil;
import ru.kpfu.itis.gr201.ponomarev.cars.util.PasswordUtil;

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
    private void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        req.setAttribute("email", user.getEmail());
        req.getRequestDispatcher("profile_edit.ftl").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showPage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newAvatarUrl = null;
        Part avatarPart;
        try {
            avatarPart = req.getPart("avatar");
        } catch (IllegalStateException e) {
            req.setAttribute("avatar_too_big", true);
            showPage(req, resp);
            return;
        }

        if (avatarPart != null) {
            if (!avatarPart.getContentType().startsWith("image/")) {
                req.setAttribute("avatar_unsupported_format", true);
                showPage(req, resp);
                return;
            }
            newAvatarUrl = CloudinaryUtil.uploadPart(avatarPart);
        }

        User oldUser = (User) req.getSession().getAttribute("user");
        String email = req.getParameter("email");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        User newUser = new User(
                oldUser.getFirstName(),
                oldUser.getLastName(),
                oldUser.getEmail(),
                newAvatarUrl == null ? oldUser.getAvatarUrl() : newAvatarUrl,
                oldUser.getLogin(),
                null
        );

        if (oldPassword != null && !oldPassword.isEmpty() && newPassword != null && !newPassword.isEmpty()) {
            if (!oldUser.getPassword().equals(PasswordUtil.encrypt(oldPassword))) {
                req.setAttribute("old_password_wrong", true);
                showPage(req, resp);
                return;
            } else if (!newPassword.equals(confirmPassword)) {
                req.setAttribute("password_not_confirmed", true);
                showPage(req, resp);
                return;
            } else {
                newUser.setPassword(newPassword);
            }
        }
        newUser.setEmail(email);

        UserDao userDao = new UserDao();
        UserService userService = new UserServiceImpl(userDao);
        try {
            userService.changeUserDetails(newUser, req, resp);
            req.setAttribute("profile_edited", true);
            resp.sendRedirect(req.getContextPath() + "/profile");
        } catch (UserNotAuthenticatedException e) {
            resp.sendRedirect(req.getContextPath() + "/auth");
        } catch (UserSaveException e) {
            if (e instanceof EmailAlreadyRegisteredException) {
                req.setAttribute(
                        "email_not_unique",
                        ((EmailAlreadyRegisteredException) e).getEmail()
                );
            } else {
                req.setAttribute("unknown_error", true);
            }
            showPage(req, resp);
        }
    }
}
