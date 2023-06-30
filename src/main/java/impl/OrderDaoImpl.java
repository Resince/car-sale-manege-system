package impl;

import entity.Order;
import utils.SqlState;

import java.util.List;

public interface OrderDaoImpl {
    SqlState addOrder(Order order);

    List<Order> searchOrder(Order order);

    SqlState updateOrder(Order order);
}
