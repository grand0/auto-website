package ru.kpfu.itis.gr201.ponomarev.cars.model.filter;

import ru.kpfu.itis.gr201.ponomarev.cars.model.*;

import java.util.List;

public class AdvertisementFilter {
    private String make;
    private String model;
    private List<Body> bodies;
    private List<Transmission> transmissions;
    private List<Engine> engines;
    private List<Drive> drives;
    private Float engineVolumeFrom;
    private Float engineVolumeTo;
    private Integer yearFrom;
    private Integer yearTo;
    private Integer horsepowerFrom;
    private Integer horsepowerTo;
    private Boolean leftWheel;
    private Integer priceFrom;
    private Integer priceTo;
    private Integer mileageFrom;
    private Integer mileageTo;
    private List<Condition> conditions;
    private Integer ownersFrom;
    private Integer ownersTo;
    private Boolean exchangeAllowed;
    private AdvertisementSorting sorting;

    public AdvertisementFilter() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Body> getBodies() {
        return bodies;
    }

    public void setBodies(List<Body> bodies) {
        this.bodies = bodies;
    }

    public List<Transmission> getTransmissions() {
        return transmissions;
    }

    public void setTransmissions(List<Transmission> transmissions) {
        this.transmissions = transmissions;
    }

    public List<Engine> getEngines() {
        return engines;
    }

    public void setEngines(List<Engine> engines) {
        this.engines = engines;
    }

    public List<Drive> getDrives() {
        return drives;
    }

    public void setDrives(List<Drive> drives) {
        this.drives = drives;
    }

    public Float getEngineVolumeFrom() {
        return engineVolumeFrom;
    }

    public void setEngineVolumeFrom(Float engineVolumeFrom) {
        this.engineVolumeFrom = engineVolumeFrom;
    }

    public Float getEngineVolumeTo() {
        return engineVolumeTo;
    }

    public void setEngineVolumeTo(Float engineVolumeTo) {
        this.engineVolumeTo = engineVolumeTo;
    }

    public Integer getYearFrom() {
        return yearFrom;
    }

    public void setYearFrom(Integer yearFrom) {
        this.yearFrom = yearFrom;
    }

    public Integer getYearTo() {
        return yearTo;
    }

    public void setYearTo(Integer yearTo) {
        this.yearTo = yearTo;
    }

    public Integer getHorsepowerFrom() {
        return horsepowerFrom;
    }

    public void setHorsepowerFrom(Integer horsepowerFrom) {
        this.horsepowerFrom = horsepowerFrom;
    }

    public Integer getHorsepowerTo() {
        return horsepowerTo;
    }

    public void setHorsepowerTo(Integer horsepowerTo) {
        this.horsepowerTo = horsepowerTo;
    }

    public Boolean getLeftWheel() {
        return leftWheel;
    }

    public void setLeftWheel(Boolean leftWheel) {
        this.leftWheel = leftWheel;
    }

    public Integer getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(Integer priceFrom) {
        this.priceFrom = priceFrom;
    }

    public Integer getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(Integer priceTo) {
        this.priceTo = priceTo;
    }

    public Integer getMileageFrom() {
        return mileageFrom;
    }

    public void setMileageFrom(Integer mileageFrom) {
        this.mileageFrom = mileageFrom;
    }

    public Integer getMileageTo() {
        return mileageTo;
    }

    public void setMileageTo(Integer mileageTo) {
        this.mileageTo = mileageTo;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Integer getOwnersFrom() {
        return ownersFrom;
    }

    public void setOwnersFrom(Integer ownersFrom) {
        this.ownersFrom = ownersFrom;
    }

    public Integer getOwnersTo() {
        return ownersTo;
    }

    public void setOwnersTo(Integer ownersTo) {
        this.ownersTo = ownersTo;
    }

    public Boolean getExchangeAllowed() {
        return exchangeAllowed;
    }

    public void setExchangeAllowed(Boolean exchangeAllowed) {
        this.exchangeAllowed = exchangeAllowed;
    }

    public AdvertisementSorting getSorting() {
        return sorting;
    }

    public void setSorting(AdvertisementSorting sorting) {
        this.sorting = sorting;
    }
}
