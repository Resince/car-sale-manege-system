package main.dao;

import main.entity.User;
import main.impl.UserSearch;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SearchUserDao implements UserSearch {
    private String sql;
    private final Map<String, String> para = new LinkedHashMap<>();

    private String getSql(User user) {
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
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSql(user))) {
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
}
