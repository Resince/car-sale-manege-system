package dao;

import entity.Order;
import impl.OrderImpl;

import java.time.LocalDate;

public class test {
    public static void main(String[] args) {
        OrderImpl order = new OrderDao();
//        System.out.println(order.searchOrder(new Order("duanyue", 1, "2023-06-29", "haha", "null")));
        System.out.println(order.addOrder(new Order("nihao", 1, "2023-06-29", "haha", "null")));
    }
}
