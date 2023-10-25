package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.BookmarksDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.BookmarksService;

import java.util.List;
import java.util.stream.Collectors;

public class BookmarksServiceImpl implements BookmarksService {

    private final BookmarksDao bookmarksDao;
    private final AdvertisementService advertisementService;

    public BookmarksServiceImpl(BookmarksDao bookmarksDao, AdvertisementService advertisementService) {
        this.bookmarksDao = bookmarksDao;
        this.advertisementService = advertisementService;
    }

    @Override
    public List<AdvertisementDto> getAllOfUser(int userId) {
        return bookmarksDao.getAllIdsOfUser(userId)
                .stream()
                .map(advertisementService::get)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isBookmarked(int userId, int advertisementId) {
        return bookmarksDao.getAllIdsOfUser(userId).contains(advertisementId);
    }
}
