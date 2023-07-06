package mapper;

import entity.Insurance;
import entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int addOrder(Order order);

    int addPurIns(@Param("insName")String insName, @Param("orderId") int orderId);

    int addIns(@Param("insName") String insName,@Param("price") double price);

    List<Order> searchOrder(Order order);

    List<Insurance> searchInsByOrderId(int orderId);

    int updateOrder(Order order);

    int deletePurIns(int orderId);
}
