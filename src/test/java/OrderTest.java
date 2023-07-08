import dao.OrderDao;
import entity.Car;
import entity.Insurance;
import entity.Order;
import org.junit.Test;
import server.PurchaseCar;

import java.util.ArrayList;
import java.util.List;

public class OrderTest {
    OrderDao orderDao = new OrderDao();
    List<Insurance> insuranceList = new ArrayList<>();
    Car car = new Car().setCarId(2863);
    Order order = new Order(6,car,"2023-11-11","12345","haha","12434114",insuranceList,true,"全额",500,300,"2033-11-11",300,"大连理工大学","false");

    @Test
    public void testAddOrder() {
        insuranceList.add(new Insurance("交强险"));
        insuranceList.add(new Insurance("第三者责任险"));
        System.out.println(orderDao.addOrder(order));
    }

    @Test
    public void testSearchOrder() {
        System.out.println(orderDao.searchOrder(new Order().setCar(new Car().setCarId(2863)).setCusPhone("12434114")));
    }


    @Test
    public void testUpdateOrder() {
        orderDao.updateOrder(order.setCar(new Car().setCarId(2864)).setOrderId(15));
    }

    @Test
    public void testGetAllOrderList(){
        PurchaseCar.getAllOrderList().forEach(System.out::println);
    }

}