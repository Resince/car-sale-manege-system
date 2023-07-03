import dao.CarDao;
import dao.UserDao;
import entity.Car;
import entity.User;
import org.junit.Assert;
import org.junit.Test;
import utils.SqlState;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ImplTest {
    Car carAdd = new Car(10000,"小汽车","电动","大众","A-4");
    Car carUpdate = new Car();
    CarDao carDao = new CarDao();

    @Test
    public void testAddCar() {
        System.out.println(LocalTime.now());
        for(int i=0;i<1000;i++){
            carDao.addCar(new Car(10000,"小汽车","电动","大众","A-4"));
        }
        System.out.println(LocalTime.now());
    }

    @Test
    public void testUpdateCar(){
        carUpdate.setCarId(1);
        carUpdate.setPrice(20.0);
        carUpdate.setSeries("fasdf");
        System.out.println(carDao.UpdateCar(carUpdate));
    }

    @Test
    public void testDeleteCarById(){
        System.out.println(carDao.deleteCarById(42));
    }

    @Test
    public void testAddCarBatch(){
        System.out.println(LocalTime.now());
        List<Car> carList = new ArrayList<>();
        for(int i=0;i<10;i++){
            carList.add(new Car(i,"小汽车","电动","大众","A-4"));
        }
        System.out.println(carDao.addCar(carList));
        System.out.println(LocalTime.now());
    }

    @Test
    public void testSelectCarByCarId(){
        System.out.println(carDao.selectCarByCarId(2542));
    }

    @Test
    public void testSelectCar(){
        System.out.println(carDao.searchCar(new Car().setCarId(2542).setPrice(1.0)));
        System.out.println(carDao.searchCar(new Car().setType("q")));
    }

    UserDao userDao = new UserDao();
    User userAdd = new User("123456","yang","1780098","worker");

    @Test
    public void testUserDao(){
        SqlState sqlState =  userDao.addUser(userAdd);
        System.out.println(userAdd.getUserId());
        System.out.println(userDao.searchUser(new User().setName("yang")));
        System.out.println(userDao.updateUser(new User(6,"9874456","yang","lkjgjkgj","llll")));
        System.out.println(userDao.authenticate(userAdd.getUserId(),null));
        System.out.println(userDao.authenticate(userAdd.getUserId(),null));
    }
}
