package ru.kpfu.itis.gr201.ponomarev.cars.service;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;

public interface ModelService {
    int saveIfNotExists(String make, String model) throws SaveException;
}
