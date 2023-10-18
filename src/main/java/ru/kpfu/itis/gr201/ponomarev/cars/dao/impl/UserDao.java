package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import org.slf4j.LoggerFactory;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.LoginAlreadyTakenException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserSaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public User get(int id) {
        try {
            String sql = "SELECT * FROM Users WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("avatar_url"),
                        resultSet.getString("login"),
                        resultSet.getString("password")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            String sql = "SELECT * FROM Users;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(
                        new User(
                                resultSet.getInt("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"),
                                resultSet.getString("email"),
                                resultSet.getString("avatar_url"),
                                resultSet.getString("login"),
                                resultSet.getString("password")
                        )
                );
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) throws UserSaveException {
        try {
            String sql = "INSERT INTO Users(first_name, last_name, email, avatar_url, login, password) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAvatarUrl());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("email_unique")) {
                throw new EmailAlreadyRegisteredException(user.getEmail());
            } else if (e.getMessage().contains("login_unique")) {
                throw new LoginAlreadyTakenException(user.getLogin());
            } else {
                LoggerFactory.getLogger(getClass()).error(e.toString());
                throw new UserSaveException(e);
            }
        }
    }

    public User getByLoginAndPasswordHash(String login, String passwordHash) {
        try {
            String sql = "SELECT * FROM Users WHERE login = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(passwordHash)) {
                    return new User(
                            resultSet.getInt("id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email"),
                            resultSet.getString("avatar_url"),
                            resultSet.getString("login"),
                            resultSet.getString("password")
                    );
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, User user) throws UserSaveException {
        try {
            PreparedStatement statement;
            if (user.getPassword() == null) {
                String sql = "UPDATE Users SET (first_name, last_name, email, avatar_url) = (?, ?, ?, ?) WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setInt(5, id);
            } else {
                String sql = "UPDATE Users SET (first_name, last_name, email, avatar_url, password) = (?, ?, ?, ?, ?) WHERE id = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(5, user.getPassword());
                statement.setInt(6, id);
            }
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAvatarUrl());
            statement.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().contains("email_unique")) {
                throw new EmailAlreadyRegisteredException(user.getEmail());
            } else if (e.getMessage().contains("login_unique")) {
                throw new LoginAlreadyTakenException(user.getLogin());
            } else {
                LoggerFactory.getLogger(getClass()).error(e.toString());
                throw new UserSaveException(e);
            }
        }
    }
}
