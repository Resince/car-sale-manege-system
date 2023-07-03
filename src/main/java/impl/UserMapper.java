package impl;

import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int addUser(User user);

    List<User> searchUser(User user);

    int updateUser(User user);

    String authenticate(@Param("userId") int inputUserId,@Param("password") String inputPasswd);

}
