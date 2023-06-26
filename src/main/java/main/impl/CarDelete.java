package main.impl;

import main.entity.Car;

import java.util.List;

public interface CarDelete {
    int deleteCar(Car car);

    int deleteCar(List<Car> carList);
}
