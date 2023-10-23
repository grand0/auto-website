package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.model.Car;
import ru.kpfu.itis.gr201.ponomarev.cars.model.User;

import java.util.List;

public interface UsersCarsDao {
    List<Car> getCarsOfUser(int userId);
    List<User> getUsersOfCar(int carId);

    List<Car> getAdvertisedCarsOfUser(int userId);
    List<Car> getNotAdvertisedCarsOfUser(int userId);

    void addCarToUser(int userId, int carId);
    void removeCarFromUser(int userId, int carId);
}
