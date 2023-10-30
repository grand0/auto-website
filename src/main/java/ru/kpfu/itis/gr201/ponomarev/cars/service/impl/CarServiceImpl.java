package ru.kpfu.itis.gr201.ponomarev.cars.service.impl;

import ru.kpfu.itis.gr201.ponomarev.cars.dao.CarDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.MakeDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dao.ModelDao;
import ru.kpfu.itis.gr201.ponomarev.cars.dto.CarDto;
import ru.kpfu.itis.gr201.ponomarev.cars.exception.SaveException;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Car;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Make;
import ru.kpfu.itis.gr201.ponomarev.cars.model.Model;
import ru.kpfu.itis.gr201.ponomarev.cars.service.CarService;

import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final CarDao carDao;
    private final MakeDao makeDao;
    private final ModelDao modelDao;

    public CarServiceImpl(CarDao carDao, MakeDao makeDao, ModelDao modelDao) {
        this.carDao = carDao;
        this.makeDao = makeDao;
        this.modelDao = modelDao;
    }

    @Override
    public List<CarDto> getAll() {
        return carDao.getAll().stream().map(this::toCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto get(int id) {
        Car car = carDao.get(id);
        return toCarDto(car);
    }

    public CarDto toCarDto(Car car) {
        Model model = modelDao.get(car.getModelId());
        Make make = makeDao.get(model.getMakeId());
        return new CarDto(
                car.getId(),
                make.getMake(),
                model.getModel(),
                car.getBody(),
                car.getTransmission(),
                car.getEngine(),
                car.getDrive(),
                car.getEngineVolume(),
                car.getYear(),
                car.getHorsepower(),
                car.isLeftWheel()
        );
    }

    @Override
    public int saveIfNotExists(Car car) throws SaveException {
        Car carDb = carDao.getByCar(car);
        if (carDb == null) {
            carDao.save(car);
            return car.getId();
        }
        return carDb.getId();
    }
}
