package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Car;

import java.util.List;

public interface CarDao {
    Car get(int id);
    Car getByCar(Car car);
    List<Car> getAll();
    void save(Car car) throws SaveException;
}
