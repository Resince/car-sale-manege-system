package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        Connection connection = SqlConnection.getConnection();
        assert connection != null;
        PreparedStatement ps = connection.prepareStatement("INSERT INTO test (id, name) VALUES (?,?)");
        ps.setObject(1, "123456"); // 注意：索引从1开始
        ps.setObject(2, "Bob"); // name
        int n = ps.executeUpdate(); // 1
        connection.close();
    }
}
