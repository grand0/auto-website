package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import org.slf4j.LoggerFactory;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.UserDao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.EmailAlreadyRegisteredException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.LoginAlreadyTakenException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.UserSaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public User get(int id) {
        try {
            String sql = "SELECT * FROM Users WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getFromResultSet(resultSet);
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
                users.add(getFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) throws SaveException {
        try {
            String sql = "INSERT INTO Users(first_name, last_name, email, avatar_url, login, password) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getAvatarUrl());
            statement.setString(5, user.getLogin());
            statement.setString(6, user.getPassword());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getInt(1));
            }
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

    @Override
    public User getByLoginAndPasswordHash(String login, String passwordHash) {
        try {
            String sql = "SELECT * FROM Users WHERE login = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("password").equals(passwordHash)) {
                    return getFromResultSet(resultSet);
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
    public void update(int id, User user) throws SaveException {
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

    private User getFromResultSet(ResultSet set) throws SQLException {
        return new User(
                set.getInt("id"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("email"),
                set.getString("avatar_url"),
                set.getString("login"),
                set.getString("password")
        );
    }
}
