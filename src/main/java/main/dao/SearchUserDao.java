package main.dao;

import main.entity.SearchUserEntity;
import main.entity.User;
import main.impl.UserSearch;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SearchUserDao implements UserSearch {
    private String sql;
    private Map<String, String> para;

    private String getSql(SearchUserEntity se) {
        para = new HashMap<>();
        if (!se.getUserName().equals("")) {
            para.put("username", se.getUserName());
        }
        if (!se.getPassword().equals("")) {
            para.put("password", se.getPassword());
        }
        if (!se.getName().equals("")) {
            para.put("name", se.getName());
        }
        if (!se.getPhoneNumber().equals("")) {
            para.put("phoneNumber", se.getPhoneNumber());
        }

        StringJoiner sj = new StringJoiner(",", "select * from user where ", "order by ");
        for (String item : para.keySet()) {
            sj.add(item + " = ? ");
        }
        System.out.println(sj.toString() + se.orderString());
        return sj.toString() + se.orderString();
    }

    @Override
    public List<User> searchUserByObject(SearchUserEntity searchUserEntity) {
        List<User> users = new ArrayList<>();
        try (Connection connection = SqlConnection.getConnection()) {
            assert connection != null;
            try (PreparedStatement preparedStatement = connection.prepareStatement(getSql(searchUserEntity))) {
                int i=1;
                for (String item:para.values()) {
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
