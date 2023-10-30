package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.AdvertisementImage;

import java.util.List;

public interface AdvertisementImagesDao {
    AdvertisementImage get(int id);
    List<AdvertisementImage> getAll();
    List<AdvertisementImage> getByAdvertisementId(int advertisementId);
    void save(AdvertisementImage advertisementImage) throws SaveException;
}
