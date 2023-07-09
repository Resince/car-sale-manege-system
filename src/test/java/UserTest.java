import entity.User;
import org.junit.Test;
import server.UserManage;

public class UserTest {

    @Test
    public void testAddUser(){
        UserManage.addUser(new User().setPassword("124315243").setName("yang").setType("admin").setPassword("11231413241").setPhoneNumber("12314141"));
    }
}
