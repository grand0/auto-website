package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.RegistrationException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.util.PasswordUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(
                u -> new UserDto(
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail(),
                        u.getAvatarUrl()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public UserDto get(int id) {
        User user = userDao.get(id);
        return new UserDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatarUrl()
        );
    }

    @Override
    public void save(User user) throws RegistrationException {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void auth(User user, boolean writeCookie, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute(
                "user",
                new UserDto(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getAvatarUrl()
                )
        );

        if (writeCookie) {
            Cookie loginCookie = new Cookie("userLogin", user.getLogin());
            Cookie passwordCookie = new Cookie("userPassword", user.getPassword());
            loginCookie.setMaxAge(60 * 60 * 24 * 14); // 14 days
            passwordCookie.setMaxAge(60 * 60 * 24 * 14);
            resp.addCookie(loginCookie);
            resp.addCookie(passwordCookie);
        }
    }

    @Override
    public boolean isAuthed(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            return session.getAttribute("user") != null;
        }
        return false;
    }

    @Override
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();

        Cookie loginCookie = new Cookie("userLogin", null);
        Cookie passwordCookie = new Cookie("userPassword", null);
        loginCookie.setMaxAge(0);
        passwordCookie.setMaxAge(0);
        resp.addCookie(loginCookie);
        resp.addCookie(passwordCookie);
    }
}
