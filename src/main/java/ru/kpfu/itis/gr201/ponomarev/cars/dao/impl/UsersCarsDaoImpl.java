package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.UsersCarsDao;
import ru.kpfu.itis.gr201.ponomarev.cars.model.*;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersCarsDaoImpl implements UsersCarsDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public List<Car> getCarsOfUser(int userId) {
        try {
            String sql = "SELECT * FROM users_cars uc JOIN cars c ON uc.car_id = c.id WHERE uc.user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (set.next()) {
                cars.add(getCarFromResultSet(set));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsersOfCar(int carId) {
        try {
            String sql = "SELECT * FROM users_cars uc JOIN users u ON uc.car_id = u.id WHERE uc.car_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, carId);
            ResultSet set = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (set.next()) {
                users.add(getUserFromResultSet(set));
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAdvertisedCarsOfUser(int userId) {
        try {
            String sql = "SELECT * FROM users_cars uc JOIN cars c ON c.id = uc.car_id " +
                    "WHERE uc.user_id = ? AND uc.car_id IN (SELECT car_id FROM advertisements WHERE seller_id = ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet set = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (set.next()) {
                cars.add(getCarFromResultSet(set));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getNotAdvertisedCarsOfUser(int userId) {
        try {
            String sql = "SELECT * FROM users_cars uc JOIN cars c ON c.id = uc.car_id " +
                    "WHERE uc.user_id = ? AND uc.car_id NOT IN (SELECT car_id FROM advertisements WHERE seller_id = ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            ResultSet set = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (set.next()) {
                cars.add(getCarFromResultSet(set));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addCarToUser(int userId, int carId) {
        try {
            String sql = "INSERT INTO users_cars (user_id, car_id) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCarFromUser(int userId, int carId) {
        try {
            String sql = "DELETE FROM users_cars WHERE user_id = ? AND car_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, carId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUserFromResultSet(ResultSet set) throws SQLException {
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

    private Car getCarFromResultSet(ResultSet set) throws SQLException {
        return new Car(
                set.getInt("id"),
                set.getInt("model_id"),
                Body.getByName(set.getString("body")),
                Transmission.getByName(set.getString("transmission")),
                Engine.getByName(set.getString("engine")),
                Drive.getByName(set.getString("drive")),
                set.getFloat("engine_volume"),
                set.getInt("year"),
                set.getInt("horsepower"),
                set.getBoolean("left_wheel")
        );
    }
}
