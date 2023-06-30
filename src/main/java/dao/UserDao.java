package dao;

import entity.User;
import impl.UserDaoImpl;
import utils.AuthState;
import utils.SqlConnection;
import utils.SqlState;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDao implements UserDaoImpl {
    /**
     * 添加新用户
     *
     * @param user user中不允许有空值
     * @return 返回值如果为-1，这说明添加失败，成功返回被修改的行数
     */
    @Override
    public SqlState addUser(User user) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into user (name,password,phoneNumber,type) values (?,?,?,?)")) {
                preparedStatement.setObject(1, user.getName());
                preparedStatement.setObject(2, user.getPassword());
                preparedStatement.setObject(3, user.getPhoneNumber());
                preparedStatement.setObject(4, user.getType());
                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    private final Map<String, String> para = new LinkedHashMap<>();

    private String genSql(User user) {
        StringJoiner sj = new StringJoiner("and", "select * from user where ", "  ");
        Field[] fields = user.getClass().getDeclaredFields();
        for (Field item : fields) {
            try {
                item.setAccessible(true);
                String values = item.get(user).toString();
                if (!values.equals("") && !values.equals("-1")) {
                    sj.add(item.getName() + " = ? ");
                    para.put(item.getName(), values);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return sj.toString();
    }

    /**
     * 按照所给的user进行搜索
     *
     * @param user 非空值表示搜索对应值
     * @return 返回搜索的到类集合,其中的password项为空
     */
    @Override
    public List<User> searchUserByObject(User user) {
        List<User> users = new ArrayList<>();
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(genSql(user))) {
                int i = 1;
                for (String item : para.values()) {
                    preparedStatement.setObject(i, item);
                    i++;
                }
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        int userId = rs.getInt(1);
                        String pwd = "";
                        String name = rs.getString(3);
                        String pn = rs.getString(4);
                        String type = rs.getString(5);
                        users.add(new User(userId, pwd, name, pn,type));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * 更新其中的User数据
     *
     * @param user user中的数据非空
     * @return 如果失败就返回-1，成功返回更新的行数
     */
    @Override
    public SqlState updateUser(User user) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "update user set password=?,name=?,phoneNumber=?,type=? where userId=?")) {
                preparedStatement.setObject(1, user.getPassword());
                preparedStatement.setObject(2, user.getName());
                preparedStatement.setObject(3, user.getPhoneNumber());
                preparedStatement.setObject(4, user.getType());
                preparedStatement.setObject(5,user.getUserId());
                preparedStatement.executeUpdate();
                return SqlState.Done;
            }
        } catch (SQLException e) {
            return SqlState.SqlError;
        }
    }

    @Override
    public AuthState authenticate(String inputUserId, String inputPasswd) {
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select password from user where userId = ?")) {
                preparedStatement.setObject(1,inputUserId);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    return rs.getString(1).equals(inputPasswd)?AuthState.Done:AuthState.InvalidPassword;
                }
            }
        } catch (SQLException e) {
            return AuthState.InvalidUsername;
        }
    }
}
