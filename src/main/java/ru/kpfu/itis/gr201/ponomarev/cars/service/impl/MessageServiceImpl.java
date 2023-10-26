package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.MessageDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.impl.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.MessageDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Message;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.MessageService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

public class MessageServiceImpl implements MessageService {

    private final MessageDao messageDao;
    private final UserService userService;
    private final AdvertisementDao advertisementDao;
    private final AdvertisementService advertisementService;

    public MessageServiceImpl(MessageDao messageDao, UserService userService, AdvertisementDao advertisementDao, AdvertisementService advertisementService) {
        this.messageDao = messageDao;
        this.userService = userService;
        this.advertisementDao = advertisementDao;
        this.advertisementService = advertisementService;
    }

    @Override
    public List<UserDto> getAllRecipientsOfAdvertisement(int advertisementId, int senderId) {
        List<Message> messages = messageDao.getAllOfAdvertisement(advertisementId);
        Set<Integer> users = new HashSet<>();
        for (Message m : messages) {
            users.add(m.getSenderId());
            users.add(m.getRecipientId());
        }
        users.remove(senderId);
        return users.stream().map(userService::get).sorted(Comparator.comparing(UserDto::toString)).collect(Collectors.toList());
    }

    @Override
    public List<MessageDto> getAllOfAdvertisementAndUser(int advertisementId, int userId) {
        return messageDao.getAllOfAdvertisementAndUser(advertisementId, userId).stream().map(this::toMessageDto).collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getAllAdvertisementsToWhichUserSentMessage(int userId) {
        return messageDao.getAllAdvertisementIdsToWhichUserSentMessage(userId)
                .stream()
                .map(advertisementDao::get)
                .map(advertisementService::toAdvertisementDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto toMessageDto(Message message) {
        UserDto sender = userService.get(message.getSenderId());
        return new MessageDto(
                sender,
                message.getMessage(),
                message.getSentTs().toString()
        );
    }
}
