package server;


import dao.UserDao;
import entity.User;
import utils.ExcelReader;

import java.util.List;
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
        if (manage.deleteUserById(userId) <=0) {
            logger.warning("数据库添加失败");
            return false;
        } else return manage.deleteUserById(userId) >0;
    }


    /**
     * @return 所有用户
     */
    public static List<User> searchAllUserList(){
        return manage.searchAllCarList();
    }



    /**
     * 更新用户信息
     *
     * @param userId     必填，不能为空
     * @param password     不需要修改则传入null
     * @param name      不需要修改则传入null
     * @param phoneNumber 不需要修改则传入null
     * @param type     不需要修改则传入null
     * @return 返回是否更新成功
     */
    public static boolean updateUser(int userId, String password, String name, String phoneNumber, String type) {
        User updateUser = new User(userId, password, name, phoneNumber, type);
        if (manage.updateUser(updateUser) <=0) {
            logger.warning("数据库更新失败!");
            return false;
        } else return manage.updateUser(updateUser) >0;
    }


}
