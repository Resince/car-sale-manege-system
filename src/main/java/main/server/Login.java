package main.server;

import main.dao.AddUserDao;
import main.entity.User;
import main.impl.UserAdd;


public class Login {
    UserAdd userAdd;
    public int login(String username,String pwd,String name,String phoneNumber){
        userAdd = new AddUserDao();
        return userAdd.addUser(new User(username,pwd,name,phoneNumber));
    }
}
