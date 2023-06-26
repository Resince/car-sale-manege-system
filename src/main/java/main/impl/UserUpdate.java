package main.impl;

import main.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserUpdate {
    int updateUser(User user);

    int updateUser(List<User> userList);
}
