package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserNotAuthenticatedException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Car;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserService {
    List<UserDto> getAll();
    UserDto get(int id);
    UserDto toUserDto(User user);
    void save(User user) throws SaveException;
    void auth(User user, boolean writeCookie, HttpServletRequest req, HttpServletResponse resp);
    UserDto getAuthedUserDto(HttpServletRequest req, HttpServletResponse resp);
    boolean isAuthed(HttpServletRequest req, HttpServletResponse resp);
    void logout(HttpServletRequest req, HttpServletResponse resp);
    void changeUserDetails(User newUser, HttpServletRequest req, HttpServletResponse resp) throws UserNotAuthenticatedException, SaveException;
    User getByLoginAndPassword(String login, String password);
    void addCarToCurrentUser(Car car, HttpServletRequest req, HttpServletResponse resp) throws UserNotAuthenticatedException;
}
