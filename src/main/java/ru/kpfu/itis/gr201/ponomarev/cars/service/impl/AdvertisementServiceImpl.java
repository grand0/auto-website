package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.AdvertisementDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.AdvertisementImagesDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.model.AdvertisementImage;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementFilter;
import ru.kpfu.itis.gr201.ponomarev.cars.service.AdvertisementService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.CarService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.util.Constants;

import java.util.List;
import java.util.stream.Collectors;

public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementDao advertisementDao;
    private final AdvertisementImagesDao advertisementImagesDao;
    private final CarService carService;
    private final UserService userService;

    public AdvertisementServiceImpl(AdvertisementDao advertisementDao, AdvertisementImagesDao advertisementImagesDao, CarService carService, UserService userService) {
        this.advertisementDao = advertisementDao;
        this.advertisementImagesDao = advertisementImagesDao;
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    public List<AdvertisementDto> getAll() {
        return advertisementDao.getAll().stream().map(this::toAdvertisementDto).collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getRecent() {
        return advertisementDao.getRecent().stream().map(this::toAdvertisementDto).collect(Collectors.toList());
    }

    @Override
    public List<AdvertisementDto> getAllWithFilter(AdvertisementFilter filter) {
        return advertisementDao.getAllWithFilter(filter).stream()
                .map(this::toAdvertisementDto)
                .collect(Collectors.toList());
    }

    @Override
    public AdvertisementDto get(int id) {
        Advertisement advertisement = advertisementDao.get(id);
        return toAdvertisementDto(advertisement);
    }

    @Override
    public AdvertisementDto toAdvertisementDto(Advertisement advertisement) {
        List<String> images = advertisementImagesDao.getByAdvertisementId(advertisement.getId()).stream().map(AdvertisementImage::getImageUrl).collect(Collectors.toList());
        CarDto carDto = carService.get(advertisement.getCarId());
        UserDto userDto = userService.get(advertisement.getSellerId());
        return new AdvertisementDto(
                advertisement.getId(),
                carDto,
                advertisement.getDescription(),
                advertisement.getPrice(),
                userDto,
                advertisement.getPublicationTs().toLocalDateTime().format(Constants.DATE_TIME_FORMATTER),
                advertisement.getMileage(),
                advertisement.getCarColor(),
                advertisement.getCondition(),
                advertisement.getOwners(),
                advertisement.isExchangeAllowed(),
                advertisement.getViewCount(),
                images
        );
    }

    @Override
    public List<AdvertisementDto> getAllOfUser(int userId) {
        return advertisementDao.getAllOfUser(userId).stream().map(this::toAdvertisementDto).collect(Collectors.toList());
    }
}
