package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.UsersCarsDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserNotAuthenticatedException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Car;
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
    private final UsersCarsDao usersCarsDao;

    public UserServiceImpl(UserDao userDao, UsersCarsDao usersCarsDao) {
        this.userDao = userDao;
        this.usersCarsDao = usersCarsDao;
    }

    @Override
    public List<UserDto> getAll() {
        return userDao.getAll().stream().map(this::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto get(int id) {
        User user = userDao.get(id);
        return toUserDto(user);
    }

    @Override
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAvatarUrl()
        );
    }

    @Override
    public void save(User user) throws SaveException {
        user.setPassword(PasswordUtil.encrypt(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void auth(User user, boolean writeCookie, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", user);

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
    public UserDto getAuthedUserDto(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            return toUserDto((User) session.getAttribute("user"));
        }
        return null;
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

    @Override
    public void changeUserDetails(User newUser, HttpServletRequest req, HttpServletResponse resp) throws UserNotAuthenticatedException, SaveException {
        if (!isAuthed(req, resp)) {
            throw new UserNotAuthenticatedException();
        }

        if (newUser.getPassword() != null) {
            newUser.setPassword(PasswordUtil.encrypt(newUser.getPassword()));
        }

        User oldUser = (User) req.getSession().getAttribute("user");
        userDao.update(oldUser.getId(), newUser);
        User updatedUser = userDao.get(oldUser.getId());
        auth(updatedUser, req.getCookies() != null, req, resp);
    }

    @Override
    public User getByLoginAndPassword(String login, String password) {
        String passwordHash = PasswordUtil.encrypt(password);
        return userDao.getByLoginAndPasswordHash(login, passwordHash);
    }

    @Override
    public void addCarToCurrentUser(Car car, HttpServletRequest req, HttpServletResponse resp) throws UserNotAuthenticatedException {
        if (!isAuthed(req, resp)) {
            throw new UserNotAuthenticatedException();
        }
        UserDto user = getAuthedUserDto(req, resp);
        usersCarsDao.addCarToUser(user.getId(), car.getId());
    }
}
