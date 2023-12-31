package impl;

import entity.Insurance;
import entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    void addOrder(Order order);

    void addPurIns(@Param("insName")String insName, @Param("orderId") int orderId);

    int addIns(@Param("insName") String insName,@Param("price") double price);

    List<Order> searchOrder(Order order);

    List<Insurance> searchInsByOrderId(int orderId);

    void updateOrder(Order order);

    void deletePurIns(int orderId);
}
