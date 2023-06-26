package main.impl;

import main.entity.Car;

import java.util.List;

public interface CarUpdate {
    int Update(Car car);

    int Update(List<Car> car);
}
