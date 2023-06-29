package impl;

import entity.User;
import utils.AuthState;

import java.util.List;

public interface UserManageImpl {
    int addUser(User user);

    List<User> searchUserByObject(User user);

    int updateUser(User user);

    AuthState authenticate(String inputUsername, String inputPasswd);

}
