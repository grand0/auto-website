package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.BookmarksDao;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookmarksDaoImpl implements BookmarksDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public List<Integer> getAllIdsOfUser(int userid) {
        try {
            String sql = "SELECT advertisement_id FROM bookmarks WHERE user_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userid);
            ResultSet set = statement.executeQuery();
            List<Integer> ids = new ArrayList<>();
            while (set.next()) {
                ids.add(set.getInt("advertisement_id"));
            }
            return ids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(int userId, int advertisementId) {
        try {
            String sql = "INSERT INTO bookmarks (user_id, advertisement_id) VALUES (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, advertisementId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void remove(int userId, int advertisementId) {
        try {
            String sql = "DELETE FROM bookmarks WHERE (user_id, advertisement_id) = (?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, advertisementId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
