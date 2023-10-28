package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Model;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDao implements Dao<Model> {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public Model get(int id) {
        try {
            String sql = "SELECT * FROM models WHERE id = ?;";
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

    public Model getByMakeIdAndName(int makeId, String name) {
        try {
            String sql = "SELECT * FROM models WHERE make_id = ? AND model = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, makeId);
            statement.setString(2, name);
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

    public List<Model> search(String make, String query) {
        try {
            String sql = "SELECT models.id, make_id, model FROM models JOIN makes on models.make_id = makes.id WHERE (? IS NULL OR make = ?) AND (model ILIKE (? || '%'));";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, make);
            statement.setString(2, make);
            statement.setString(3, query);
            ResultSet set = statement.executeQuery();
            List<Model> models = new ArrayList<>();
            while (set.next()) {
                models.add(getFromResultSet(set));
            }
            return models;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Model> getAll() {
        try {
            String sql = "SELECT * FROM models;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            List<Model> models = new ArrayList<>();
            while (set.next()) {
                models.add(getFromResultSet(set));
            }
            return models;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Model model) throws SaveException {
        try {
            String sql = "INSERT INTO models VALUES (DEFAULT, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getMakeId());
            statement.setString(2, model.getModel());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Model model) throws SaveException {
        try {
            String sql = "UPDATE models SET (make_id, model) = (?, ?) WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, model.getMakeId());
            statement.setString(2, model.getModel());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Model getFromResultSet(ResultSet set) throws SQLException {
        return new Model(
                set.getInt("id"),
                set.getInt("make_id"),
                set.getString("model")
        );
    }
}
