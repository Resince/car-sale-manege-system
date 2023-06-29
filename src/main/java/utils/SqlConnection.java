package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class SqlConnection {
    //获取到db.properties文件中的数据库信息
    //私有变量
        private static String driver;
        private static String url;
        private static String user;
        private static String password;

        private static Connection conn;

        //静态块
        static {
            try {
                //1.新建属性集对象
                Properties properties = new Properties();
                //2通过反射，新建字符输入流，读取db.properties文件
                InputStream input = SqlConnection.class.getClassLoader().getResourceAsStream("db.properties");
                //3.将输入流中读取到的属性，加载到properties属性集对象中
                properties.load(input);
                //4.根据键，获取properties中对应的值
                driver = properties.getProperty("driver");
                url = properties.getProperty("url");
                user = properties.getProperty("user");
                password = properties.getProperty("password");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //返回数据库连接
        public static Connection getConnection() {
            try {
                if(conn==null || conn.isClosed()){
                    //注册数据库的驱动
                    Class.forName(driver);
                    //获取数据库连接（里面内容依次是：主机名和端口、用户名、密码）
                    conn = DriverManager.getConnection(url, user, password);
                }
                //返回数据库连接
                return conn;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}

