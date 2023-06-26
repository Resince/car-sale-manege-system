package main.impl;

import main.entity.User;

import java.util.List;

public interface UserSearch {
    User searchUserByUn(String un);

    List<User> searchUserByName(String name);

    User searchUserByPn(String pn);
}
