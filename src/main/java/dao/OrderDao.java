package dao;

import entity.Insurance;
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

    private final Map<String, String> funGetMap = new LinkedHashMap<>();
    private final Map<String, String> funSetMap = new LinkedHashMap<>();

    /**
     * 添加订单
     *
     * @param order 订单类
     * @return SqlState中的结果
     */
    @Override
    public SqlState addOrder(Order order) {
        Map<String, String> variableMap = getVariableMap(order);
        StringJoiner sj = new StringJoiner(",","insert into `,order` ( "," ) values (?,?,?,?,?,?,?,?,?,?,?,?)");

        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into `order` (username,carId,time,PurchaseMethod,remark) values (?,?,?,?,?)")) {

                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    @Override
    public List<Order> searchOrder(Order order) {
        Map<String, String> variableMap = getVariableMap(order);
        List<Order> orders = new ArrayList<>();
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(genSelectSql(variableMap))) {
                int i = 1;
                for (String item : variableMap.keySet()) {
                    if (!item.equals("insurances")) {
                        System.out.println(item);
                        preparedStatement.setObject(i, variableMap.get(item));
                        i++;
                    }
                }
                System.out.println(preparedStatement);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        Order res = new Order();
                        res.setOrderId(rs.getInt(1));
                        res.setCarId(rs.getInt(2));
                        res.setUserId(rs.getInt(3));
                        res.setOrderTime(LocalDate.from(rs.getTime(4).toLocalTime()));
                        res.setCusID(rs.getInt(5));
                        res.setCusName(rs.getString(6));
                        res.setCusPhone(rs.getString(7));
                        res.setHasLicenseServer(rs.getString(8).equals("true"));
                        res.setPmtDiscount(rs.getInt(9));
                        res.setDeposit(rs.getInt(10));
                        res.setDeliveryTime(LocalDate.from(rs.getTime(11).toLocalTime()));
                        res.setPurchaseTax(rs.getInt(12));
                        orders.add(order);
                    }
                }
            }
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from purIns join Insurances on purIns.insName = Insurances.insName where orderId = ?")){
                preparedStatement.setObject(1,order.getOrderId());
                try(ResultSet rs = preparedStatement.executeQuery()){
                    List<Insurance> list = new ArrayList<>();
                    while (rs.next()){
                        list.add(new Insurance(rs.getString("insName"),rs.getDouble("price")));
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

    private Map<String, String> getVariableMap(Order order) {
        Map<String, String> variableMap = new LinkedHashMap<>();
        Field[] fields = order.getClass().getDeclaredFields();
        for (Field item : fields) {
            try {
                item.setAccessible(true);
                if (item.get(order) == null) {
                    continue;
                }
                variableMap.put(item.getName(), item.get(order).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return variableMap;
    }

    private String genSelectSql(Map<String, String> variableMap) {
        StringJoiner sj = new StringJoiner("and ", "select * from `order` where ", "  ");
        for (String item : variableMap.keySet()) {
            if (item.equals("insurances")) {
                continue;
            }
            sj.add(item + " = ? ");
        }
        return sj.toString();
    }

    private String genAddSql(Order order) {
        return null;
    }
}
