package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.*;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementFilter;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementSorting;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            StringBuilder sb = new StringBuilder(
                    "SELECT advertisements.id, car_id, description, price, seller_id, publication_ts, mileage, car_color, condition, owners, exchange_allowed, view_count " +
                    "FROM advertisements " +
                    "JOIN cars on advertisements.car_id = cars.id " +
                    "JOIN models on cars.model_id = models.id " +
                    "JOIN makes on models.make_id = makes.id " +
                    "WHERE " +
                    "(? IS NULL OR make ILIKE ('%' || ? || '%')) AND " +
                    "(? IS NULL OR model ILIKE ('%' || ? || '%')) AND " +
                    "(body = ANY(?)) AND " +
                    "(transmission = ANY(?)) AND " +
                    "(engine = ANY(?)) AND " +
                    "(drive = ANY(?)) AND " +
                    "(? IS NULL OR engine_volume >= ?) AND " +
                    "(? IS NULL OR engine_volume <= ?) AND " +
                    "(? IS NULL OR year >= ?) AND " +
                    "(? IS NULL OR year <= ?) AND " +
                    "(? IS NULL OR horsepower >= ?) AND " +
                    "(? IS NULL OR horsepower <= ?) AND " +
                    "(? IS NULL OR left_wheel = ?) AND " +
                    "(? IS NULL OR price >= ?) AND " +
                    "(? IS NULL OR price <= ?) AND " +
                    "(? IS NULL OR mileage >= ?) AND " +
                    "(? IS NULL OR mileage <= ?) AND " +
                    "(? IS NULL OR owners >= ?) AND " +
                    "(? IS NULL OR owners <= ?) AND " +
                    "(condition = ANY(?)) AND " +
                    "(? IS NULL OR exchange_allowed = ?) "
            );

            if (filter.getSorting() == null) {
                filter.setSorting(AdvertisementSorting.PUBLICATION_TIME);
            }
            switch (filter.getSorting()) {
                case CAR_NAME:
                    sb.append("ORDER BY make || ' ' || model ");
                    break;
                case MILEAGE:
                    sb.append("ORDER BY mileage ");
                    break;
                case PRICE:
                    sb.append("ORDER BY price ");
                    break;
                case PUBLICATION_TIME:
                    sb.append("ORDER BY publication_ts ");
                    break;
                case VIEWS:
                    sb.append("ORDER BY view_count ");
                    break;
            }
            if (filter.getSorting().isDesc()) {
                sb.append("DESC;");
            } else {
                sb.append("ASC;");
            }
            String sql = sb.toString();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, filter.getMake());
            statement.setString(2, filter.getMake());
            
            statement.setString(3, filter.getModel());
            statement.setString(4, filter.getModel());
            
            Stream<Body> bodies = filter.getBodies() != null ? filter.getBodies().stream() : Arrays.stream(Body.values());
            String[] bodiesStr = bodies
                    .map(Body::getBody)
                    .toArray(String[]::new);
            Array bodiesArr = connection.createArrayOf("VARCHAR", bodiesStr);
            statement.setArray(5, bodiesArr);
            
            Stream<Transmission> transmissions = filter.getTransmissions() != null ? filter.getTransmissions().stream() : Arrays.stream(Transmission.values());
            String[] transmissionsStr = transmissions
                    .map(Transmission::getTransmission)
                    .toArray(String[]::new);
            Array transmissionsArr = connection.createArrayOf("VARCHAR", transmissionsStr);
            statement.setArray(6, transmissionsArr);
            
            Stream<Engine> engines = filter.getEngines() != null ? filter.getEngines().stream() : Arrays.stream(Engine.values());
            String[] enginesStr = engines
                    .map(Engine::getEngine)
                    .toArray(String[]::new);
            Array enginesArr = connection.createArrayOf("VARCHAR", enginesStr);
            statement.setArray(7, enginesArr);

            Stream<Drive> drives = filter.getDrives() != null ? filter.getDrives().stream() : Arrays.stream(Drive.values());
            String[] drivesStr = drives
                    .map(Drive::getDrive)
                    .toArray(String[]::new);
            Array drivesArr = connection.createArrayOf("VARCHAR", drivesStr);
            statement.setArray(8, drivesArr);

            if (filter.getEngineVolumeFrom() != null) {
                statement.setFloat(9, filter.getEngineVolumeFrom());
                statement.setFloat(10, filter.getEngineVolumeFrom());
            } else {
                statement.setNull(9, Types.NUMERIC);
                statement.setNull(10, Types.NUMERIC);
            }

            if (filter.getEngineVolumeTo() != null) {
                statement.setFloat(11, filter.getEngineVolumeTo());
                statement.setFloat(12, filter.getEngineVolumeTo());
            } else {
                statement.setNull(11, Types.NUMERIC);
                statement.setNull(12, Types.NUMERIC);
            }

            if (filter.getYearFrom() != null) {
                statement.setInt(13, filter.getYearFrom());
                statement.setInt(14, filter.getYearFrom());
            } else {
                statement.setNull(13, Types.INTEGER);
                statement.setNull(14, Types.INTEGER);
            }

            if (filter.getYearTo() != null) {
                statement.setInt(15, filter.getYearTo());
                statement.setInt(16, filter.getYearTo());
            } else {
                statement.setNull(15, Types.INTEGER);
                statement.setNull(16, Types.INTEGER);
            }

            if (filter.getHorsepowerFrom() != null) {
                statement.setInt(17, filter.getHorsepowerFrom());
                statement.setInt(18, filter.getHorsepowerFrom());
            } else {
                statement.setNull(17, Types.INTEGER);
                statement.setNull(18, Types.INTEGER);
            }

            if (filter.getHorsepowerTo() != null) {
                statement.setInt(19, filter.getHorsepowerTo());
                statement.setInt(20, filter.getHorsepowerTo());
            } else {
                statement.setNull(19, Types.INTEGER);
                statement.setNull(20, Types.INTEGER);
            }

            if (filter.getLeftWheel() != null) {
                statement.setBoolean(21, filter.getLeftWheel());
                statement.setBoolean(22, filter.getLeftWheel());
            } else {
                statement.setNull(21, Types.BOOLEAN);
                statement.setNull(22, Types.BOOLEAN);
            }

            if (filter.getPriceFrom() != null) {
                statement.setInt(23, filter.getPriceFrom());
                statement.setInt(24, filter.getPriceFrom());
            } else {
                statement.setNull(23, Types.INTEGER);
                statement.setNull(24, Types.INTEGER);
            }

            if (filter.getPriceTo() != null) {
                statement.setInt(25, filter.getPriceTo());
                statement.setInt(26, filter.getPriceTo());
            } else {
                statement.setNull(25, Types.INTEGER);
                statement.setNull(26, Types.INTEGER);
            }

            if (filter.getMileageFrom() != null) {
                statement.setInt(27, filter.getMileageFrom());
                statement.setInt(28, filter.getMileageFrom());
            } else {
                statement.setNull(27, Types.INTEGER);
                statement.setNull(28, Types.INTEGER);
            }

            if (filter.getMileageTo() != null) {
                statement.setInt(29, filter.getMileageTo());
                statement.setInt(30, filter.getMileageTo());
            } else {
                statement.setNull(29, Types.INTEGER);
                statement.setNull(30, Types.INTEGER);
            }

            if (filter.getOwnersFrom() != null) {
                statement.setInt(31, filter.getOwnersFrom());
                statement.setInt(32, filter.getOwnersFrom());
            } else {
                statement.setNull(31, Types.INTEGER);
                statement.setNull(32, Types.INTEGER);
            }

            if (filter.getOwnersTo() != null) {
                statement.setInt(33, filter.getOwnersTo());
                statement.setInt(34, filter.getOwnersTo());
            } else {
                statement.setNull(33, Types.INTEGER);
                statement.setNull(34, Types.INTEGER);
            }

            Stream<Condition> conditions = filter.getConditions() != null ? filter.getConditions().stream() : Arrays.stream(Condition.values());
            String[] conditionsStr = conditions
                    .map(Condition::getCondition)
                    .toArray(String[]::new);
            Array conditionsArr = connection.createArrayOf("VARCHAR", conditionsStr);
            statement.setArray(35, conditionsArr);

            if (filter.getExchangeAllowed() != null) {
                statement.setObject(36, filter.getExchangeAllowed());
                statement.setObject(37, filter.getExchangeAllowed());
            } else {
                statement.setNull(36, Types.BOOLEAN);
                statement.setNull(37, Types.BOOLEAN);
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
