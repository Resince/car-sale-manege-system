package impl;

import entity.Order;

import java.util.List;

public interface OrderImpl {
    int addOrder(Order order);
    List<Order> searchOrder(Order order);
}
