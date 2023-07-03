package impl;

import entity.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    int addCar(Car car);

    int deleteCarById(@Param("carId") int carId);

    List<Car> selectCarByCarId(@Param("carId") int carId);

    List<Car> selectCar(Car car);

    int updateCar(Car car);
}
