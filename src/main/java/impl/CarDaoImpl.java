package impl;

import entity.Car;
import utils.SqlState;

import java.util.List;

public interface CarDaoImpl {
    SqlState addCar(Car car);

    SqlState addCar(List<Car> carList);

    SqlState deleteCar(Car car);

    SqlState deleteCar(List<Car> carList);

    List<Car> searchCarByObject(Car car);

    SqlState UpdateCar(Car car);
}
