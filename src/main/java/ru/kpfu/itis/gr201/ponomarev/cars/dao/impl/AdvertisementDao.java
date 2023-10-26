package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Condition;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementFilter;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.*;

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
            String sql = "SELECT * FROM advertisements ORDER BY publication_ts DESC;";
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

    public List<Advertisement> getAllWithFilter(AdvertisementFilter filter) {
        try {
            String sql = "SELECT * FROM advertisements WHERE " +
                    "(? IS NULL OR price >= ?) AND " +
                    "(? IS NULL OR price <= ?) AND " +
                    "(? IS NULL OR mileage >= ?) AND " +
                    "(? IS NULL OR mileage <= ?) AND " +
                    "(? IS NULL OR owners >= ?) AND " +
                    "(? IS NULL OR owners <= ?) AND " +
                    "(condition = ANY(?)) AND " +
                    "(? IS NULL OR exchange_allowed = ?) " +
                    "ORDER BY publication_ts DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);
            if (filter.getPriceFrom() != null) {
                statement.setInt(1, filter.getPriceFrom());
                statement.setInt(2, filter.getPriceFrom());
            } else {
                statement.setNull(1, Types.INTEGER);
                statement.setNull(2, Types.INTEGER);
            }
            if (filter.getPriceTo() != null) {
                statement.setInt(3, filter.getPriceTo());
                statement.setInt(4, filter.getPriceTo());
            } else {
                statement.setNull(3, Types.INTEGER);
                statement.setNull(4, Types.INTEGER);
            }
            if (filter.getMileageFrom() != null) {
                statement.setInt(5, filter.getMileageFrom());
                statement.setInt(6, filter.getMileageFrom());
            } else {
                statement.setNull(5, Types.INTEGER);
                statement.setNull(6, Types.INTEGER);
            }
            if (filter.getMileageTo() != null) {
                statement.setInt(7, filter.getMileageTo());
                statement.setInt(8, filter.getMileageTo());
            } else {
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.INTEGER);
            }
            if (filter.getOwnersFrom() != null) {
                statement.setInt(9, filter.getOwnersFrom());
                statement.setInt(10, filter.getOwnersFrom());
            } else {
                statement.setNull(9, Types.INTEGER);
                statement.setNull(10, Types.INTEGER);
            }
            if (filter.getOwnersTo() != null) {
                statement.setInt(11, filter.getOwnersTo());
                statement.setInt(12, filter.getOwnersTo());
            } else {
                statement.setNull(11, Types.INTEGER);
                statement.setNull(12, Types.INTEGER);
            }
            if (filter.getConditions() != null) {
                String[] conditionsStr = filter.getConditions().stream()
                        .map(Condition::getCondition)
                        .toArray(String[]::new);
                Array conditions = connection.createArrayOf("VARCHAR", conditionsStr);
                statement.setArray(13, conditions);
            } else {
                String[] allConditionsStr = Arrays.stream(Condition.values())
                        .map(Condition::getCondition)
                        .toArray(String[]::new);
                Array allConditions = connection.createArrayOf("VARCHAR", allConditionsStr);
                statement.setArray(13, allConditions);
            }
            if (filter.getExchangeAllowed() != null) {
                statement.setObject(14, filter.getExchangeAllowed());
                statement.setObject(15, filter.getExchangeAllowed());
            } else {
                statement.setNull(14, Types.BOOLEAN);
                statement.setNull(15, Types.BOOLEAN);
            }
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

    public void delete(int id) {
        try {
            String sql = "DELETE FROM advertisements WHERE id = ?;";
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
