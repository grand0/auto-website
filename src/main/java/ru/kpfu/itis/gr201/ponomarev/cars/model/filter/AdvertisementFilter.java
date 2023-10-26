package ru.kpfu.itis.gr201.ponomarev.cars.model.filter;

import ru.kpfu.itis.gr201.ponomarev.cars.model.Condition;
import ru.kpfu.itis.gr201.ponomarev.cars.service.BookmarksService;

import java.util.List;

public class AdvertisementFilter {
    private Integer priceFrom;
    private Integer priceTo;
    private Integer mileageFrom;
    private Integer mileageTo;
    private List<Condition> conditions;
    private Integer ownersFrom;
    private Integer ownersTo;
    private Boolean exchangeAllowed;

    public AdvertisementFilter() {
    }

    public AdvertisementFilter(Integer priceFrom, Integer priceTo, Integer mileageFrom, Integer mileageTo, List<Condition> conditions, Integer ownersFrom, Integer ownersTo, Boolean exchangeAllowed) {
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.mileageFrom = mileageFrom;
        this.mileageTo = mileageTo;
        this.conditions = conditions;
        this.ownersFrom = ownersFrom;
        this.ownersTo = ownersTo;
        this.exchangeAllowed = exchangeAllowed;
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
}
