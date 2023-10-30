package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.MakeDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.ModelDao;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Make;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Model;
import ru.kpfu.itis.gr201.ponomarev.cars.service.ModelService;

public class ModelServiceImpl implements ModelService {

    private final MakeDao makeDao;
    private final ModelDao modelDao;

    public ModelServiceImpl(MakeDao makeDao, ModelDao modelDao) {
        this.makeDao = makeDao;
        this.modelDao = modelDao;
    }

    @Override
    public int saveIfNotExists(String make, String model) throws SaveException {
        Make makeDb = makeDao.getByName(make);
        if (makeDb == null) {
            makeDao.save(new Make(make));
            makeDb = makeDao.getByName(make);
        }
        Model modelDb = modelDao.getByMakeIdAndName(makeDb.getId(), model);
        if (modelDb == null) {
            modelDao.save(new Model(makeDb.getId(), model));
            modelDb = modelDao.getByMakeIdAndName(makeDb.getId(), model);
        }
        return modelDb.getId();
    }
}
