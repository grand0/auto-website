package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.RegistrationException;

import java.util.List;

public interface Dao<T> {

    T get(int id);
    List<T> getAll();
    void save(T t) throws RegistrationException;
}
