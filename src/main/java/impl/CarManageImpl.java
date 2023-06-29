package impl;

import entity.Car;

import java.util.List;

public interface CarManageImpl {
    int addCar(Car car);

    int addCar(List<Car> carList);

    int deleteCar(Car car);

    int deleteCar(List<Car> carList);

    List<Car> searchCarByObject(Car car);

    int Update(Car car);
}
