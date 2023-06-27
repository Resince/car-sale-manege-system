package main.dao;

import main.impl.UserUpdate;
import main.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UpdateUserDao implements UserUpdate {
    /**
     * 更新其中的User数据
     * @param user user中的数据非空
     * @return 如果失败就返回-1，成功返回更新的行数
     */
    @Override
    public int updateUser(User user){
        int n=-1;
        try(Connection connection = SqlConnection.getConnection()){
            assert connection != null;
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "update user set password=?,name=?,phoneNumber=? where username=?")){
                preparedStatement.setObject(1,user.getPassword());
                preparedStatement.setObject(2,user.getName());
                preparedStatement.setObject(3,user.getPhoneNumber());
                preparedStatement.setObject(4,user.getUserName());
                n = preparedStatement.executeUpdate();
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return n;
    }

}
