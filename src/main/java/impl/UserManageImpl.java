package impl;

import entity.User;
import utils.AuthState;
import utils.SqlState;

import java.util.List;

public interface UserManageImpl {
    SqlState addUser(User user);

    List<User> searchUserByObject(User user);

    SqlState updateUser(User user);

    AuthState authenticate(String inputUsername, String inputPasswd);

}
