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

    /**
     * 添加订单
     *
     * @param order 其中的orderId应该为空，该字段无论有无，系统都是自动添加
     * @return 返回添加完的order,在其中有自动添加上的orderId
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
        return order;
    }

    /**
     *  添加保险信息
     * @param name 保险的名字
     * @param price 保险的价格
     * @return 返回SqlState
     */
    public SqlState addIns(String name, double price) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        int n = orderDao.addIns(name, price);
        sqlSession.commit();
        return SqlState.Done;
    }

    /**
     * 搜索订单<br>
     * 支持按照order中的非空字段进行搜索<br>
     * <strong>但是不支持按照保险的信息进行检索</strong><br>
     * 举例： 不支持：<br>
     * insurances.add(new Insurance("A-Ins"));<br>
     * searchOrder(new Order().setCusId(17).setInsurances(insurances));<br>
     * 这样无法找到同时拥有 A-Ins 和 cusId=17的order
     * @param order 其中的非空字段作为搜索的对象
     * @return 返回Order的集合
     */
    public List<Order> searchOrder(Order order) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        return orderDao.searchOrder(order);
    }

    /**
     * 根据orderId搜索保险信息
     * @return 返回Insurance的list
     */
    public List<Insurance> searchInsByOrderId(int orderId){
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        return orderDao.searchInsByOrderId(orderId);
    }

    /**
     * 更新order其中的值<br>
     * 如果字段没有设置，那就说明不更新
     * @param order 其中orderId不应该为空
     * @return 更新的行数
     */
    public SqlState updateOrder(Order order) {
        SqlSession sqlSession = SqlConnection.getSession();
        orderDao = sqlSession.getMapper(OrderMapper.class);
        orderDao.updateOrder(order);
        if (order.getInsurances() != null) {
            orderDao.deletePurIns(order.getOrderId());
            for (Insurance item : order.getInsurances()) {
                orderDao.addPurIns(item.getInsName(), order.getOrderId());
            }
        }
        sqlSession.commit();
        return SqlState.Done;
    }

}
