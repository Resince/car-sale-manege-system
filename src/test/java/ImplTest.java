import dao.CarDao;
import dao.OrderDao;
import dao.UserDao;
import entity.Car;
import entity.Insurance;
import entity.Order;
import entity.User;
import org.junit.Test;
import server.PurchaseCar;
import utils.SqlState;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ImplTest {
    Car carUpdate = new Car();
    CarDao carDao = new CarDao();

    @Test
    public void testAddCar() {
        System.out.println(LocalTime.now());
        for (int i = 0; i < 1000; i++) {
            carDao.addCar(new Car(10000.0, "小汽车", "电动", "大众", "A-4",1));
        }
        System.out.println(LocalTime.now());
    }

    @Test
    public void testUpdateCar() {
        carUpdate.setCarId(1);
        carUpdate.setPrice(20.0);
        carUpdate.setSeries("fasdf");
        System.out.println(carDao.UpdateCar(carUpdate));
    }

    @Test
    public void testDeleteCarById() {
        System.out.println(carDao.deleteCarById(42));
    }

    @Test
    public void testAddCarBatch() {
        System.out.println(LocalTime.now());
        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            carList.add(new Car((double) i, "小汽车", "电动", "大众", "A-4",1));
        }
        System.out.println(carDao.addCar(carList));
        System.out.println(LocalTime.now());
    }

    @Test
    public void testSelectCarByCarId() {
        System.out.println(carDao.selectCarByCarId(2542));
    }

    @Test
    public void testSelectCar() {
        System.out.println(carDao.searchCar(new Car().setCarId(2542).setPrice(1.0)));
        System.out.println(carDao.searchCar(new Car().setType("q")));
    }

    UserDao userDao = new UserDao();
    User userAdd = new User("123456", "yang", "1780098", "worker");

    @Test
    public void testUserDao() {
        SqlState sqlState = userDao.addUser(userAdd);
        System.out.println(userAdd.getUserId());
        System.out.println(userDao.searchUser(new User().setName("yang")));
        System.out.println(userDao.updateUser(new User(6, "9874456", "yang", "lkjgjkgj", "llll")));
        System.out.println(userDao.authenticate(userAdd.getUserId(), null));
        System.out.println(userDao.authenticate(userAdd.getUserId(), null));
    }

    OrderDao orderDao = new OrderDao();
    List<Insurance> insurances = new ArrayList<>();
    Order order = new Order(2849, 17, "2023-01-21", "1233456678", "客户1", "123456789", insurances, true, "全额付款", 123, 234, "2024-01-01", 500,"dfasdf");

    @Test
    public void testOrderInsert() {
        insurances.add(new Insurance("A-Ins"));
        insurances.add(new Insurance("B-Ins"));
        System.out.println(orderDao.addOrder(order));
    }

    @Test
    public void testOrderAddIns() {
        System.out.println(orderDao.addIns("F-Ins", 900.0));
    }

    @Test
    public void testUpdateOrder(){
        insurances.add(new Insurance("E-Ins"));
        insurances.add(new Insurance("D-Ins"));
        System.out.println(orderDao.updateOrder(order.setOrderId(1).setCusPhone("1315")));
    }

    @Test
    public void testSearchOrder(){
        insurances.add(new Insurance("A-Ins"));
        List<Order> list = orderDao.searchOrder(new Order().setCusId("17").setInsurances(insurances));
        for(Order item:list){
            System.out.println(item);
        }
    }

    @Test
    public void testSearchCar(){
        System.out.println(carDao.searchCarByPrice(2, 8));
    }

    @Test
    public void testGetInsurancePrice(){
        Insurance insurance = new Insurance("车损险");
//        System.out.println(PurchaseCar.getInsurancePrice(insurance).getPrice());
//        System.out.println(PurchaseCar.getInsurancePrice(insurance).getPrice());
//        System.out.println(PurchaseCar.getInsurancePrice(insurance).getPrice());
    }
}
