package main.impl;

import main.entity.Car;
import main.entity.SearchCarEntity;

import java.util.List;

public interface CarSearch {
    List<Car> searchCarByObject(SearchCarEntity searchCarEntity);
}
