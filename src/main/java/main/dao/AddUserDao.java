package main.dao;

import main.impl.UserAdd;
import main.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserDao implements UserAdd {
    @Override
    public int addUser(User user) {
        int n=0;
        try(Connection connection = SqlConnection.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into user (username,password,name,phoneNumber) values (?,?,?,?)")){
                preparedStatement.setObject(1, user.getUserName());
                preparedStatement.setObject(2,user.getPassword());
                preparedStatement.setObject(3,user.getName());
                preparedStatement.setObject(4,user.getPhoneNumber());
                n = preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n;
    }
}
