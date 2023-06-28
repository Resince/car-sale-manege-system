package main.dao;

import main.impl.UserAdd;
import main.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserDao implements UserAdd {
    /**
     * 添加新用户
     * @param user user中不允许有空值
     * @return 返回值如果为-1，这说明添加失败，成功返回被修改的行数
     */
    @Override
    public int addUser(User user) {
        int n=-1;
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
            n=-1;
        }
        return n;
    }
}
