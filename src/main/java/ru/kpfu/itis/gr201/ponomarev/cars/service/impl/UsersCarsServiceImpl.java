package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.UsersCarsDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.UserDto;
import ru.kpfu.itis.gr201.ponomarev.cars.service.CarService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UserService;
import ru.kpfu.itis.gr201.ponomarev.cars.service.UsersCarsService;

import java.util.List;
import java.util.stream.Collectors;

public class UsersCarsServiceImpl implements UsersCarsService {

    private final UsersCarsDao usersCarsDao;
    private final CarService carService;
    private final UserService userService;

    public UsersCarsServiceImpl(UsersCarsDao usersCarsDao, CarService carService, UserService userService) {
        this.usersCarsDao = usersCarsDao;
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    public List<CarDto> getCarsOfUser(int userId) {
        return usersCarsDao.getCarsOfUser(userId).stream().map(carService::toCarDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersOfCar(int carId) {
        return usersCarsDao.getUsersOfCar(carId).stream().map(userService::toUserDto).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getAdvertisedCarsOfUser(int userId) {
        return usersCarsDao.getAdvertisedCarsOfUser(userId).stream().map(carService::toCarDto).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getNotAdvertisedCarsOfUser(int userId) {
        return usersCarsDao.getNotAdvertisedCarsOfUser(userId).stream().map(carService::toCarDto).collect(Collectors.toList());
    }
}
