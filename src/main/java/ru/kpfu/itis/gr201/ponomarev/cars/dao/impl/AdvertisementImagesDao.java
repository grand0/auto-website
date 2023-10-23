package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.Dao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.AdvertisementImage;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvertisementImagesDao implements Dao<AdvertisementImage> {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public AdvertisementImage get(int id) {
        try {
            String sql = "SELECT * FROM advertisement_images WHERE id = ?;";
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
    public List<AdvertisementImage> getAll() {
        try {
            String sql = "SELECT * FROM advertisement_images;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet set = statement.executeQuery();
            List<AdvertisementImage> advertisementImages = new ArrayList<>();
            while (set.next()) {
                advertisementImages.add(getFromResultSet(set));
            }
            return advertisementImages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AdvertisementImage> getByAdvertisementId(int advertisementId) {
        try {
            String sql = "SELECT * FROM advertisement_images WHERE advertisement_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, advertisementId);
            ResultSet set = statement.executeQuery();
            List<AdvertisementImage> advertisementImages = new ArrayList<>();
            while (set.next()) {
                advertisementImages.add(getFromResultSet(set));
            }
            return advertisementImages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(AdvertisementImage advertisementImage) throws SaveException {
        try {
            String sql = "INSERT INTO advertisement_images VALUES (DEFAULT, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, advertisementImage.getAdvertisementId());
            statement.setString(2, advertisementImage.getImageUrl());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(int id, AdvertisementImage advertisementImage) throws SaveException {
        try {
            String sql = "UPDATE advertisement_images SET (advertisement_id, image_url) = (?, ?) WHERE id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, advertisementImage.getAdvertisementId());
            statement.setString(2, advertisementImage.getImageUrl());
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private AdvertisementImage getFromResultSet(ResultSet set) throws SQLException {
        return new AdvertisementImage(
                set.getInt("id"),
                set.getInt("advertisement_id"),
                set.getString("image_url")
        );
    }
}
