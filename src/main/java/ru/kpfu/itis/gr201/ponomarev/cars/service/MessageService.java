package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.MessageDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Message;

import java.util.List;

public interface MessageService {
    List<UserDto> getAllRecipientsOfAdvertisement(int advertisementId, int senderId);
    List<MessageDto> getAllOfAdvertisementAndUser(int advertisementId, int senderId);
    List<AdvertisementDto> getAllAdvertisementsToWhichUserSentMessage(int userId);

    MessageDto toMessageDto(Message message);
}
