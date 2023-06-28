package main.dao;

import main.entity.Car;
import main.impl.CarDelete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DeleteCar implements CarDelete {
    /**
     * 仅仅根据car的id对车辆进行删除
     * @param car car.id不能为空
     * @return 返回影响的行数
     */
    @Override
    public int deleteCar(Car car) {
        int n=-1;
        try(Connection connection = SqlConnection.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from car where carId = ?")){
                preparedStatement.setObject(1, car.getCarId());
                n = preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            n=-1;
        }
        return n;
    }

    /**
     * 仅仅根据car的id对车辆进行删除
     * @param carList carList中的id不能重复,且不为空
     * @return 返回受影响的行数
     */
    @Override
    public int deleteCar(List<Car> carList) {
        int[] n ;
        try(Connection connection = SqlConnection.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from car where carId = ?")){
                for(Car item:carList){
                    preparedStatement.setObject(1,item.getCarId());
                    preparedStatement.addBatch();
                }
                n = preparedStatement.executeBatch();
            }
        }catch (SQLException e) {
            return -1;
        }
        return Arrays.stream(n).sum();
    }
}
