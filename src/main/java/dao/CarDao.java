package dao;

import entity.Car;
import impl.CarDaoImpl;
import utils.SqlConnection;
import utils.SqlState;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CarDao implements CarDaoImpl {
    /**
     * 添加Car的信息
     *
     * @param car car的参数不应为空
     * @return 返回SqlState枚举类
     */
    @Override
    public SqlState addCar(Car car) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into car (price,type,powerType,brand,series) values (?,?,?,?,?)")) {
                preparedStatement.setObject(1, car.getPrice());
                preparedStatement.setObject(2, car.getType());
                preparedStatement.setObject(3, car.getPowerType());
                preparedStatement.setObject(4, car.getBrand());
                preparedStatement.setObject(5, car.getSeries());
                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    /**
     * 批量添加Car的信息
     *
     * @param carList 其中的Car 不需要要carId,为数据库自增
     * @return 返回SqlState枚举类
     */
    @Override
    public SqlState addCar(List<Car> carList) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into car (price,type,powerType,brand,series) values (?,?,?,?,?)")) {
                for (Car item : carList) {
                    preparedStatement.setObject(1, item.getPrice());
                    preparedStatement.setObject(2, item.getType());
                    preparedStatement.setObject(3, item.getPowerType());
                    preparedStatement.setObject(4, item.getBrand());
                    preparedStatement.setObject(5, item.getSeries());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    /**
     * 仅仅根据car的id对车辆进行删除
     *
     * @param carID
     * @return 返回SqlState枚举类
     */
    @Override
    public SqlState deleteCar(int carID) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from car where carId = ?")) {
                preparedStatement.setObject(1, carID);
                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    /**
     * 仅仅根据car的id对车辆进行删除
     *
     * @param carList carList中的id不能重复,且不为空
     * @return 返回SqlState枚举类
     */
    @Override
    public SqlState deleteCar(List<Car> carList) {
        int[] n;
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from car where carId = ?")) {
                for (Car item : carList) {
                    preparedStatement.setObject(1, item.getCarId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return SqlState.Done;
            }
        } catch (SQLException e) {
           return SqlState.SqlError;
        }
    }

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
     *
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

    @Override
    public SqlState UpdateCar(Car car) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "update car set price = ?,type= ? ,powerType= ? ,brand= ? ,series= ? where carId = ? ")) {
                preparedStatement.setObject(1, car.getPrice());
                preparedStatement.setObject(2, car.getType());
                preparedStatement.setObject(3, car.getPowerType());
                preparedStatement.setObject(4, car.getBrand());
                preparedStatement.setObject(5, car.getSeries());
                preparedStatement.setObject(6, car.getCarId());
                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
           return SqlState.SqlError;
        }
    }
}
