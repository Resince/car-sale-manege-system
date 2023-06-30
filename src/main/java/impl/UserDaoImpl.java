package impl;

import entity.User;
import utils.AuthState;
import utils.SqlState;

import java.util.List;

public interface UserDaoImpl {
    SqlState addUser(User user);

    /**
     * 用来搜索 user 中的属性
     * @return 返回值中 密码项为空
     */
    List<User> searchUserByObject(User user);

    SqlState updateUser(User user);

    AuthState authenticate(String inputUserId, String inputPasswd);

}
