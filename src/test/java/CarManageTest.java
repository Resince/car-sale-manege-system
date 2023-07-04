import entity.Car;
import org.junit.Test;
import server.CarManage;

public class CarManageTest {

    @Test
    public void testAddCar(){
        System.out.println(CarManage.addCar(100000, "ss", "ss", "ss", "ss"));
        System.out.println(CarManage.addCar(100022, "ss", "ss", "aa", "ss"));
    }

    @Test
    public void testAddCarList() {
        System.out.println(CarManage.addCarList(""));
    }

    @Test
    public void testDeleteCar() {
        System.out.println(CarManage.deleteCar(2840));
    }

    @Test
    public void testSearchCar() {
        System.out.println(CarManage.searchCarBySeries("ss"));
        System.out.println(CarManage.searchCarByBrand("ss"));
//        System.out.println(CarManage.searchCarByPrice(10000, 10003));
    }
    @Test
    public void testSearchCarByMultiple() {
        System.out.println(CarManage.searchCarByMultiple(CarManage.searchCarByBrand("ss"), CarManage.searchCarBySeries("ss")));
    }

    @Test
    public void testUpdateCar() {
        System.out.println(CarManage.updateCar(2836, null, null, null, null, "aa"));
    }
}
