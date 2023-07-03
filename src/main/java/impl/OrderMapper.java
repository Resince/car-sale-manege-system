package impl;

import entity.Order;
import org.apache.ibatis.annotations.Param;
import utils.SqlState;

import java.util.List;

public interface OrderMapper {
    int addOrder(Order order);

    int addPurIns(@Param("insName")String insName,@Param("orderId") int orderId);

    int addIns(@Param("insName") String insName,@Param("price") double price);

    List<Order> searchOrder(Order order);

    int updateOrder(Order order);
}
