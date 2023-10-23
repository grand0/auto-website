package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Make;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MakeDao implements Dao<Make> {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public Make get(int id) {
        try {
            String sql = "SELECT * FROM makes WHERE id = ?;";
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

    public Make getByName(String name) {
        try {
            String sql = "SELECT * FROM makes WHERE make = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
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
    public List<Make> getAll() {
        try {
            String sql = "SELECT * FROM makes;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            List<Make> makes = new ArrayList<>();
            while (set.next()) {
                makes.add(getFromResultSet(set));
            }
            return makes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Make make) throws SaveException {
        try {
            String sql = "INSERT INTO makes values (DEFAULT, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, make.getMake());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Make make) throws SaveException {
        try {
            String sql = "UPDATE makes SET make = ? WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, make.getMake());
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Make getFromResultSet(ResultSet set) throws SQLException {
        return new Make(
                set.getInt("id"),
                set.getString("make")
        );
    }
}
