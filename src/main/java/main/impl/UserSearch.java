package main.impl;

import main.entity.SearchUserEntity;
import main.entity.User;

import java.util.List;

public interface UserSearch {
    List<User> searchUserByObject(SearchUserEntity searchUserEntity);
}
