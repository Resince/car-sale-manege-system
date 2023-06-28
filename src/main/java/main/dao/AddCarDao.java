package main.dao;

import main.entity.Car;
import main.impl.CarAdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AddCarDao implements CarAdd {
    /**
     * 添加Car的信息
     * @param car car的参数不应为空
     * @return 返回总共影响的行数，失败返回-1
     */
    @Override
    public int addCar(Car car) {
        int n=-1;
        try(Connection connection = SqlConnection.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into car (price,type,powerType,brand,series) values (?,?,?,?,?)")){
                preparedStatement.setObject(1, car.getPrice());
                preparedStatement.setObject(2,car.getType());
                preparedStatement.setObject(3,car.getPowerType());
                preparedStatement.setObject(4,car.getBrand());
                preparedStatement.setObject(5,car.getSeries());
                n = preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            n=-1;
        }
        return n;
    }

    /**
     * 批量添加Car的信息
     * @param carList 其中的Car 不需要要carId,为数据库自增
     * @return 返回总共影响的行数，失败返回-1
     */
    @Override
    public int addCar(List<Car> carList) {
        int[] n;
        try(Connection connection = SqlConnection.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into car (price,type,powerType,brand,series) values (?,?,?,?,?)")){
                for(Car item:carList){
                    preparedStatement.setObject(1, item.getPrice());
                    preparedStatement.setObject(2,item.getType());
                    preparedStatement.setObject(3,item.getPowerType());
                    preparedStatement.setObject(4,item.getBrand());
                    preparedStatement.setObject(5,item.getSeries());
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
