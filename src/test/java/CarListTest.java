import org.junit.Test;
import server.CarManage;

public class CarListTest {
    @Test
    public void testSearchAllCarList(){
        CarManage.searchAllCarList().forEach(System.out::println);
    }
}
