package main.dao;

import main.impl.UserUpdate;
import main.entity.User;

import java.util.List;

public class UpdateUser implements UserUpdate {
    String sql="update";
    @Override
    public int updateUser(User user){
        return 0;
    }

    @Override
    public int updateUser(List<User> userList) {
        return 0;
    }
}
