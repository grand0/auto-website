package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Advertisement;
import ru.kpfu.itis.gr201.ponomarev.cars.model.filter.AdvertisementFilter;

import java.util.List;

public interface AdvertisementDao {
    Advertisement get(int id);
    List<Advertisement> getAll();
    List<Advertisement> getRecent();
    List<Advertisement> getAllWithFilter(AdvertisementFilter filter);
    List<Advertisement> getAllOfUser(int userId);
    void save(Advertisement advertisement) throws SaveException;
    void incrementViewCount(int id);
    void delete(int id);
}
