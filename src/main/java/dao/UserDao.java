package dao;

import impl.UserManageImpl;
import entity.User;
import utils.AuthState;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserDao implements UserManageImpl {
    /**
     * 添加新用户
     *
     * @param user user中不允许有空值
     * @return 返回值如果为-1，这说明添加失败，成功返回被修改的行数
     */
    @Override
    public int addUser(User user) {
        int n = -1;
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into user (username,password,name,phoneNumber) values (?,?,?,?)")) {
                preparedStatement.setObject(1, user.getUserName());
                preparedStatement.setObject(2, user.getPassword());
                preparedStatement.setObject(3, user.getName());
                preparedStatement.setObject(4, user.getPhoneNumber());
                n = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            n = -1;
        }
        return n;
    }

    private String sql;
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
     * @return 返回搜索的到类集合
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
                        String un = rs.getString(1);
                        String pwd = rs.getString(2);
                        String name = rs.getString(3);
                        String pn = rs.getString(4);
                        users.add(new User(un, pwd, name, pn));
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
    public int updateUser(User user) {
        int n = -1;
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "update user set password=?,name=?,phoneNumber=? where username=?")) {
                preparedStatement.setObject(1, user.getPassword());
                preparedStatement.setObject(2, user.getName());
                preparedStatement.setObject(3, user.getPhoneNumber());
                preparedStatement.setObject(4, user.getUserName());
                n = preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            n = -1;
        }
        return n;
    }

    @Override
    public AuthState authenticate(String inputUsername, String inputPasswd) {
        return null;
    }


}
