package dao;

import entity.Insurance;
import entity.Order;
import mapper.OrderMapper;
import org.apache.ibatis.session.SqlSession;
import utils.SqlConnection;

import java.util.List;

public class OrderDao {
    OrderMapper orderDao;

    /**
     * 添加订单
     *
     * @param order 其中的orderId应该为空，该字段无论有无，系统都是自动添加
     * @return 返回添加完的order, 在其中有自动添加上的orderId
     */
    public Order addOrder(Order order) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        List<Insurance> insurances = order.getInsurances();
        orderDao.addOrder(order);
        for (Insurance item : insurances) {
            orderDao.addPurIns(item.getInsName(), order.getOrderId());
        }
        sqlSession.commit();
        sqlSession.close();
        return order;
    }

//    /**
//     * 添加保险信息
//     *
//     * @param name  保险的名字
//     * @param price 保险的价格
//     */
//    public int addIns(String name, double price) {
//        SqlSession sqlSession = SqlConnection.getSession();
//        orderDao = sqlSession.getMapper(OrderMapper.class);
//        int n = orderDao.addIns(name, price);
//        sqlSession.commit();
//        sqlSession.close();
//        return n;
//    }

    /**
     * 搜索订单<br>
     * 支持按照order中的非空字段进行搜索<br>
     * <strong>但是不支持按照保险的信息进行检索</strong><br>
     * 举例： 不支持：<br>
     * insurances.add(new Insurance("A-Ins"));<br>
     * searchOrder(new Order().setCusId(17).setInsurances(insurances));<br>
     * 这样无法找到同时拥有 A-Ins 和 cusId=17的order
     *
     * @param order 其中的非空字段作为搜索的对象
     * @return 返回Order的集合
     */
    public List<Order> searchOrder(Order order) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        List<Order> orderList = orderDao.searchOrder(order);
        sqlSession.close();
        return orderList;
    }

    public List<Insurance> searchAllInsurance() {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        List<Insurance> insurances = orderDao.searchAllInsurance();
        sqlSession.close();
        return insurances;
    }

    /**
     * 更新order其中的值<br>
     * 如果字段没有设置，那就说明不更新
     *
     * @param order 其中orderId不应该为空
     * @return 更新的行数
     */
    public int updateOrder(Order order) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        int ans = orderDao.updateOrder(order);
        if (order.getInsurances() != null) {
            orderDao.deletePurIns(order.getOrderId());
            for (Insurance item : order.getInsurances()) {
                orderDao.addPurIns(item.getInsName(), order.getOrderId());
            }
        }
        sqlSession.commit();
        sqlSession.close();
        return ans;
    }

    public int deleteOrder(Order order) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        if (order.getInsurances() != null) {
            orderDao.deletePurIns(order.getOrderId());
        }
        int ans = orderDao.deleteOrder(order.getOrderId());
        sqlSession.commit();
        sqlSession.close();
        return ans;
    }

}
