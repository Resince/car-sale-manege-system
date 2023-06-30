package dao;

import entity.Order;
import impl.OrderDaoImpl;
import utils.SqlConnection;
import utils.SqlState;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class OrderDao implements OrderDaoImpl {
    @Override
    public SqlState addOrder(Order order) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into `order` (username,carId,time,PurchaseMethod,remark) values (?,?,?,?,?)")) {
                preparedStatement.setObject(1, order.getUsername());
                preparedStatement.setObject(2, order.getCarId());
                preparedStatement.setObject(3, order.getTime());
                preparedStatement.setObject(4, order.getPurchaseMethod());
                preparedStatement.setObject(5, order.getRemark());
                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    private final Map<String, String> para = new LinkedHashMap<>();

    private String genSql(Order order) {
        StringJoiner sj = new StringJoiner("and ", "select * from `order` where ", "  ");
        Field[] fields = order.getClass().getDeclaredFields();
        for (Field item : fields) {
            try {
                item.setAccessible(true);
                if (item.get(order) == null) {
                    continue;
                }
                String values = item.get(order).toString();
                if (!values.equals("") && !values.equals("-1")) {
                    sj.add(item.getName() + " = ? ");
                    para.put(item.getName(), values);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sj.toString();
    }

    @Override
    public List<Order> searchOrder(Order order) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(genSql(order))) {
                int i = 1;
                for (String item : para.values()) {
                    preparedStatement.setObject(i, item);
                    i++;
                }
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int userd = rs.getInt(1);
                        int carId = rs.getInt(2);
                        LocalDate localDate = rs.getDate(3).toLocalDate();
                        String p = rs.getString(4);
                        String mark = rs.getString(5);
                        orders.add(new Order(un,carId,localDate,p,mark));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public SqlState updateOrder(Order order) {
        return null;
    }
}
