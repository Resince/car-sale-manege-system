package server;

import dao.UserDao;
import entity.User;
import utils.RegState;

import java.util.regex.Pattern;

/**
 *
 */
public class UserAccess {
    private static final UserDao userDao = new UserDao();

    /**
     * check the phone number valid or not
     *
     * @param str phone number
     * @return valid
     */
    public static boolean phoneNumberIsValid(String str) {
        return Pattern.matches("^1[3456789]\\d{9}$", str);
    }

    /**
     * check the string valid or not
     *
     * @param str string
     * @return 0:invalid
     * 1:valid but not hard
     * 2:hard
     */
    public static int strIsValid(String str) {
        int flag_n = 0, flag_c = 0;
        for (char c : str.toCharArray()) {
            if (c >= '0' && c <= '9')
                flag_n = 1;
            else if (c >= 'a' && c <= 'z')
                flag_c = 1;
            else
                return 0;
        }
        return flag_n + flag_c;
    }

    /**
     * @param userId      用户唯一标识
     * @param passwd      密码，6-10位，必须同时密码+数字
     * @param name        姓名
     * @param phoneNumber 电话号
     * @return 注册状态码
     */
    public static RegState register(int userId, String passwd, String name, String phoneNumber) {
        int passwdH = strIsValid(passwd);
        if (passwdH == 0 || strIsValid(String.valueOf(userId)) == 0)
            return RegState.InvalidCharacter;
        if (!phoneNumberIsValid(phoneNumber))
            return RegState.InvalidPhoneNumber;
//        if (userDao.authenticate(phoneNumber, null) != AuthState.InvalidUsername)
//            return RegState.UsernameExists;
        if (passwd.length() < 6)
            return RegState.ShortPassword;
        if (passwd.length() > 10)
            return RegState.LongPassword;
        if (passwdH != 2)
            return RegState.SimplePassword;

        User user = new User(String.valueOf(userId), passwd, name, phoneNumber);
        userDao.addUser(user);
        return RegState.Done;
    }

    /**
     * 可以用来判断用户名是否合理，以及用户名和密码是否正确
     */
    public static User authenticate(String phoneNumber, String passwd) {
        return userDao.authenticate(phoneNumber, passwd);
    }

}
