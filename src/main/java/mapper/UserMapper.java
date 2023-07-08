package mapper;

import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int addUser(User user);

    int deleteUserById(@Param("userId") int userId);

    List<User> searchUser(User user);

    int updateUser(User user);

    String authenticate(@Param("phoneNumber") String inputPhoneNumber,@Param("password") String inputPasswd);

}
