package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Car;

import java.util.List;

public interface CarService {
    List<CarDto> getAll();
    CarDto get(int id);
    CarDto toCarDto(Car car);
    int saveIfNotExists(Car car) throws SaveException;
}
