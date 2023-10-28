package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.model.Message;

import java.util.List;

public interface MessageDao {
    void send(Message message);
    List<Message> getAllOfAdvertisement(int advertisementId);
    List<Message> getAllOfAdvertisementAndUser(int advertisementId, int senderId);
    List<Integer> getAllAdvertisementIdsToWhichUserSentMessage(int userId);
    void setAllAsReadForAdvertisementAndRecipient(int advertisementId, int recipientId);
    List<Message> getUnreadMessagesOfRecipient(int recipientId);
}
