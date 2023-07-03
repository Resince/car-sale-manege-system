package dao;

import entity.Insurance;
import entity.Order;
import impl.OrderMapper;
import org.apache.ibatis.session.SqlSession;
import utils.SqlConnection;
import utils.SqlState;

import java.util.List;

public class OrderDao {
    OrderMapper orderDao;

    public SqlState addOrder(Order order) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            orderDao = sqlSession.getMapper(OrderMapper.class);
            List<Insurance> insurances = order.getInsurances();
            orderDao.addOrder(order);
            for (Insurance item : insurances) {
                orderDao.addPurIns(item.getInsName(), order.getOrderId());
            }
            sqlSession.commit();
            return SqlState.Done;
        }
    }

    public SqlState addIns(String name, double price) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            orderDao = sqlSession.getMapper(OrderMapper.class);
            orderDao.addIns(name, price);
            sqlSession.commit();
            return SqlState.Done;
        }
    }

    public List<Order> searchOrder(Order order) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            orderDao = sqlSession.getMapper(OrderMapper.class);
            return null;
        }
    }

    public SqlState updateOrder(Order order) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            orderDao = sqlSession.getMapper(OrderMapper.class);

            sqlSession.commit();
            return SqlState.Done;
        }
    }

}
