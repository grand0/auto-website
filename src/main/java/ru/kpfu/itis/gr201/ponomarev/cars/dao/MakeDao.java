package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Make;

import java.util.List;

public interface MakeDao {
    Make get(int id);
    Make getByName(String name);
    List<Make> search(String query);
    List<Make> getAll();
    void save(Make make) throws SaveException;
}
