package main.dao;

import main.entity.SearchUserEntity;
import main.impl.UserSearch;

public class test {
    public static void main(String[] args) {
        UserSearch userSearch = new SearchUserDao();
        System.out.println(userSearch.searchUserByObject(new SearchUserEntity("remark", "", "", "", -1)));
    }
}
