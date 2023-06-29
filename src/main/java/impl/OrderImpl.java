package impl;

import entity.Order;

public interface OrderImpl {
    int addOrder(Order order);
    Order searchOrder(Order order);
}
