package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.*;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao implements Dao<Car> {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public Car get(int id) {
        try {
            String sql = "SELECT * FROM cars WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getFromResultSet(set);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Car getByCar(Car car) {
        try {
            String sql = "SELECT * FROM cars WHERE (model_id, body, transmission, engine, drive, engine_volume, year, horsepower, left_wheel) = (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            setValuesOfStatement(statement, car);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return getFromResultSet(set);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> getAll() {
        try {
            String sql = "SELECT * FROM cars;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (set.next()) {
                cars.add(getFromResultSet(set));
            }
            return cars;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Car car) throws SaveException {
        try {
            String sql = "INSERT INTO cars (model_id, body, transmission, engine, drive, engine_volume, year, horsepower, left_wheel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            setValuesOfStatement(statement, car);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                car.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Car car) throws SaveException {
        try {
            String sql = "UPDATE cars SET (model_id, body, transmission, engine, drive, engine_volume, year, horsepower, left_wheel) = (?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            setValuesOfStatement(statement, car);
            statement.setInt(10, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Car getFromResultSet(ResultSet set) throws SQLException {
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

    private void setValuesOfStatement(PreparedStatement statement, Car car) throws SQLException {
        statement.setInt(1, car.getModelId());
        statement.setString(2, car.getBody().getBody());
        statement.setString(3, car.getTransmission().getTransmission());
        statement.setString(4, car.getEngine().getEngine());
        statement.setString(5, car.getDrive().getDrive());
        statement.setDouble(6, car.getEngineVolume());
        statement.setInt(7, car.getYear());
        statement.setInt(8, car.getHorsepower());
        statement.setBoolean(9, car.isLeftWheel());
    }
}
