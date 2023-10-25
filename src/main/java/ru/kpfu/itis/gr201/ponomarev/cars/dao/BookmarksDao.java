package ru.kpfu.itis.gr201.ponomarev.cars.dao;

import java.util.List;

public interface BookmarksDao {
    List<Integer> getAllIdsOfUser(int userid);
    void add(int userId, int advertisementId);
    void remove(int userId, int advertisementId);
}
