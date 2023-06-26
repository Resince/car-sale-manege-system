package main.impl;

import main.entity.Car;

import java.util.List;

public interface CarSearch {
    List<Car> searchCarByPrice(double price);

    List<Car> searchCarByDiscount(double count);

    List<Car> searchCarByPowerType(String price);

    List<Car> searchCarByBrand(String price);

    List<Car> searchCarByVersion(String price);
}
