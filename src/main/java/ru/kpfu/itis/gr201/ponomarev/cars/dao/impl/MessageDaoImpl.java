package ru.kpfu.itis.gr201.ponomarev.cars.dao.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.MessageDao;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Message;
import ru.kpfu.itis.gr201.ponomarev.cars.util.DatabaseConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageDaoImpl implements MessageDao {

    private final Connection connection = DatabaseConnectionUtil.getConnection();

    @Override
    public void send(Message message) {
        try {
            String sql = "INSERT INTO messages (sender_id, advertisement_id, recipient_id, message, sent_ts) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, message.getSenderId());
            statement.setInt(2, message.getAdvertisementId());
            statement.setInt(3, message.getRecipientId());
            statement.setString(4, message.getMessage());
            statement.setTimestamp(5, message.getSentTs());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getAllOfAdvertisement(int advertisementId) {
        try {
            String sql = "SELECT * FROM messages WHERE advertisement_id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, advertisementId);
            ResultSet set = statement.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (set.next()) {
                messages.add(getFromResultSet(set));
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> getAllOfAdvertisementAndUser(int advertisementId, int userId) {
        try {
            String sql = "SELECT * FROM messages WHERE advertisement_id = ? AND (sender_id = ? OR recipient_id = ?) ORDER BY sent_ts DESC;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, advertisementId);
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            ResultSet set = statement.executeQuery();
            List<Message> messages = new ArrayList<>();
            while (set.next()) {
                messages.add(getFromResultSet(set));
            }
            return messages;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> getAllAdvertisementIdsToWhichUserSentMessage(int userId) {
        try {
            String sql = "select distinct advertisement_id " +
                    "from messages " +
                    "where advertisement_id in (select id " +
                    "                           from advertisements " +
                    "                           where seller_id != ?) " +
                    "  and ? = sender_id;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            statement.setInt(2, userId);
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

    private Message getFromResultSet(ResultSet set) throws SQLException {
        return new Message(
                set.getInt("id"),
                set.getInt("sender_id"),
                set.getInt("recipient_id"),
                set.getInt("advertisement_id"),
                set.getString("message"),
                set.getTimestamp("sent_ts")
        );
    }
}
