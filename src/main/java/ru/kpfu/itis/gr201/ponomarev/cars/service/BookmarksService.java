package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;

import java.util.List;

public interface BookmarksService {
    List<AdvertisementDto> getAllOfUser(int userId);
    boolean isBookmarked(int userId, int advertisementId);
}
