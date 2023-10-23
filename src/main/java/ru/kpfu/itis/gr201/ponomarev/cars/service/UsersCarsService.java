package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;

import java.util.List;

public interface UsersCarsService {
    List<CarDto> getCarsOfUser(int userId);
    List<UserDto> getUsersOfCar(int carId);
    List<CarDto> getAdvertisedCarsOfUser(int userId);
    List<CarDto> getNotAdvertisedCarsOfUser(int userId);
}
