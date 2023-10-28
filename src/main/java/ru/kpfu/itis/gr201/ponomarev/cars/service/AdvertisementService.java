package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.AdvertisementDto;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementFilter;

import java.util.List;

public interface AdvertisementService {
    List<AdvertisementDto> getAll();
    List<AdvertisementDto> getRecent();
    List<AdvertisementDto> getAllWithFilter(AdvertisementFilter filter);

    AdvertisementDto get(int id);
    AdvertisementDto toAdvertisementDto(Advertisement advertisement);
    List<AdvertisementDto> getAllOfUser(int userId);
}
