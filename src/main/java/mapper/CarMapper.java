package mapper;

import entity.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {
    int addCar(Car car);

    int deleteCarById(@Param("carId") int carId);

    //List<Car> selectCarByCarId(@Param("carId") int carId);

    //List<Car> searchCarByPrice(@Param("priceLeft") double priceLeft, @Param("priceRight") double priceRight);

    List<Car> selectCar(Car car);

    List<Car> searchBrandSeries();

    int updateCar(Car car);
}
