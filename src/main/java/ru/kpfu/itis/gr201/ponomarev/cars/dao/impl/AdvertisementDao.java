package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Condition;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementDao implements Dao<Advertisement> {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public Advertisement get(int id) {
        try {
            String sql = "SELECT * FROM advertisements WHERE id = ?;";
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

    @Override
    public List<Advertisement> getAll() {
        try {
            String sql = "SELECT * FROM advertisements;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            List<Advertisement> advertisements = new ArrayList<>();
            while (set.next()) {
                advertisements.add(getFromResultSet(set));
            }
            return advertisements;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Advertisement> getAllOfUser(int userId) {
        try {
            String sql = "SELECT * FROM advertisements WHERE seller_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet set = statement.executeQuery();
            List<Advertisement> advertisements = new ArrayList<>();
            while (set.next()) {
                advertisements.add(getFromResultSet(set));
            }
            return advertisements;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Advertisement advertisement) throws SaveException {
        try {
            String sql = "INSERT INTO advertisements (car_id, description, price, seller_id, publication_ts, mileage, car_color, condition, owners, exchange_allowed, view_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, advertisement.getCarId());
            statement.setString(2, advertisement.getDescription());
            statement.setInt(3, advertisement.getPrice());
            statement.setInt(4, advertisement.getSellerId());
            statement.setTimestamp(5, advertisement.getPublicationTs());
            statement.setInt(6, advertisement.getMileage());
            statement.setString(7, advertisement.getCarColor());
            statement.setString(8, advertisement.getCondition().getCondition());
            statement.setInt(9, advertisement.getOwners());
            statement.setBoolean(10, advertisement.isExchangeAllowed());
            statement.setInt(11, advertisement.getViewCount());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                advertisement.setId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, Advertisement advertisement) throws SaveException {
        try {
            String sql = "UPDATE advertisements SET (car_id, description, price, seller_id, publication_ts, mileage, car_color, condition, owners, exchange_allowed, view_count) = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, advertisement.getCarId());
            statement.setString(2, advertisement.getDescription());
            statement.setInt(3, advertisement.getPrice());
            statement.setInt(4, advertisement.getSellerId());
            statement.setTimestamp(5, advertisement.getPublicationTs());
            statement.setInt(6, advertisement.getMileage());
            statement.setString(7, advertisement.getCarColor());
            statement.setString(8, advertisement.getCondition().getCondition());
            statement.setInt(9, advertisement.getOwners());
            statement.setBoolean(10, advertisement.isExchangeAllowed());
            statement.setInt(11, advertisement.getViewCount());
            statement.setInt(12, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void incrementViewCount(int id) {
        try {
            String sql = "UPDATE advertisements SET view_count = view_count + 1 WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Advertisement getFromResultSet(ResultSet set) throws SQLException {
        return new Advertisement(
                set.getInt("id"),
                set.getInt("car_id"),
                set.getString("description"),
                set.getInt("price"),
                set.getInt("seller_id"),
                set.getTimestamp("publication_ts"),
                set.getInt("mileage"),
                set.getString("car_color"),
                Condition.getByName(set.getString("condition")),
                set.getInt("owners"),
                set.getBoolean("exchange_allowed"),
                set.getInt("view_count")
        );
    }
}
