package main.dao;

import main.entity.Car;
import main.impl.CarSearch;
import main.impl.UserSearch;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SearchCarDao implements CarSearch {
    private String sql;
    private final Map<String, String> para = new LinkedHashMap<>();

    public String getSql(Car car) {
        StringJoiner sj = new StringJoiner(" and ", "select * from car where ", " ");
        Field[] fields = car.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                String values = field.get(car).toString();
                if (!values.equals("") && !values.equals("-1") && !values.equals("-1.0")) {
                    sj.add(field.getName() + " = ? ");
                    para.put(field.getName(), values);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return sj.toString();
    }

    /**
     * 通过传入的Car类进行搜索
     * @param car 根据传入的非空数据，进行搜索，如果数据为空说明不以该参数搜索
     * @return car的list
     * 举例：如果传入的是 car(123456,"","","","") 则说明搜索 价格为123456的car
     */
    @Override
    public List<Car> searchCarByObject(Car car) {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSql(car))) {
                int i = 1;
                for (String item : para.values()) {
                    preparedStatement.setObject(i, item);
                    i++;
                }
                System.out.println(preparedStatement.toString());
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int carId = rs.getInt(1);
                        double price = rs.getDouble(2);
                        String type = rs.getString(3);
                        String powerType = rs.getString(4);
                        String brand = rs.getString(5);
                        String series = rs.getString(6);
                        cars.add(new Car(carId, price, type, powerType, brand, series));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cars;
    }
}
