package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Model;

import java.util.List;

public interface ModelDao {
    Model get(int id);
    Model getByMakeIdAndName(int makeId, String name);
    List<Model> search(String make, String query);
    List<Model> getAll();
    void save(Model model) throws SaveException;
}
