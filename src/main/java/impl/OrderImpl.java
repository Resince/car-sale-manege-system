package impl;

import entity.Order;
import utils.SqlState;

import java.util.List;

public interface OrderImpl {
    SqlState addOrder(Order order);
    List<Order> searchOrder(Order order);
}
