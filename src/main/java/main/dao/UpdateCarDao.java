package main.dao;

import main.entity.Car;
import main.impl.CarUpdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UpdateCarDao implements CarUpdate {
    @Override
    public int Update(Car car) {
        int n = -1;
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "update car set price = ?,type= ? ,powerType= ? ,brand= ? ,series= ? where carId = ? ")) {
                preparedStatement.setObject(1,car.getPrice());
                preparedStatement.setObject(2,car.getType());
                preparedStatement.setObject(3,car.getPowerType());
                preparedStatement.setObject(4,car.getBrand());
                preparedStatement.setObject(5,car.getSeries());
                preparedStatement.setObject(6,car.getCarId());
                n = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
           n=-1;
        }
        return n;
    }
}
