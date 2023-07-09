package server;


import dao.UserDao;
import entity.User;
import utils.ExcelReader;
import utils.SqlState;

import java.util.*;
import java.util.logging.Logger;

public class UserManage {
    private static final UserDao manage = new UserDao();

    private static final Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

    /*
      目前没有查找，若需要则添加
     */

    /**
     * 添加单个用户信息
     */
    public static void addUser(User user) {
       manage.addUser(user);
    }

    /**
     * 根据用户id删除用户
     */
    public static boolean deleteUserById(int userId) {
        if (manage.deleteUserById(userId) == SqlState.SqlError) {
            logger.warning("数据库添加失败");
            return false;
        } else return manage.deleteUserById(userId) == SqlState.Done;
    }


    /**
     * @return 所有用户
     */
    public static List<User> searchAllUserList(){
        return manage.searchAllCarList();
    }



    /**
     * 更新用户信息
     * @param user 更新的用户（以工号定位）
     * @return 返回是否更新成功
     */
    public static boolean updateUser(User user) {
        if (manage.updateUser(user) == SqlState.SqlError) {
            logger.warning("数据库更新失败!");
            return false;
        } else return manage.updateUser(user) == SqlState.Done;
    }


}
