package main.impl;

import main.entity.Car;

import java.util.List;

public interface CarAdd {
    int addCar(Car car);

    int addCar(List<Car> carList);
}
