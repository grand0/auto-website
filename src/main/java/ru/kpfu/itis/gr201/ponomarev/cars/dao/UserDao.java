package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;

import java.util.List;

public interface UserDao {
    User get(int id);
    List<User> getAll();
    void save(User user) throws SaveException;
    User getByLoginAndPasswordHash(String login, String passwordHash);
    void update(int id, User user) throws SaveException;
}
