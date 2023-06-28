package main.impl;

import main.entity.User;

import java.util.List;

public interface UserSearch {
    List<User> searchUserByObject(User user);
}
