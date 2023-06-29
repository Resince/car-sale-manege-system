package server;

import dao.UserDao;
import entity.User;
import utils.AuthState;
import utils.RegState;
import utils.UserInfo;

import java.util.regex.Pattern;

/**
 *
 */
public class UserAccess {
    private static final UserDao userDao=new UserDao();

    /**
     * check the phone number valid or not
     * @param str phone number
     * @return valid
     */
    private static boolean phoneNumberIsValid(String str){
        return Pattern.matches("^1[3456789]\\d{9}$", str);
    }

    /**
     * check the string valid or not
     * @param str string
     * @return
     * 0:invalid
     * 1:valid but not hard
     * 2:hard
     */
    private static int strIsValid(String str){
        int flag_n=0,flag_c=0;
        for(char c:str.toCharArray()){
            if(c>='0' && c<='9')
                flag_n=1;
            else if(c>='a' && c<='z')
                flag_c=1;
            else
                return 0;
        }
        return flag_n+flag_c;
    }

    /**
     *
     * @param username 用户名，只能字母+数字
     * @param passwd 密码，6-10位，必须同时密码+数字
     * @param name 姓名
     * @param phoneNumber 电话号
     * @return 注册状态码
     */
    public static RegState register(String username, String passwd, String name, String phoneNumber){
        int passwdH=strIsValid(passwd);
        if(passwdH==0 || strIsValid(username)==0)
            return RegState.InvalidCharacter;
        if(userDao.authenticate(username,"")!=AuthState.InvalidUsername)
            return RegState.UsernameExists;
        if(passwd.length()<6)
            return RegState.ShortPassword;
        if(passwd.length()>10)
            return RegState.LongPassword;
        if(passwdH!=2)
            return RegState.SimplePassword;
        if(!phoneNumberIsValid(phoneNumber))
            return RegState.InvalidPhoneNumber;
        User user=new User(username,passwd,name,phoneNumber);
        userDao.addUser(user);
        return RegState.Done;
    }

    public static AuthState authenticate(String username,String passwd){
        AuthState res=userDao.authenticate(username,passwd);
        return res;
    }

    /**
     * 修改用户信息，调用前应该再次验证身份
     */
    public static void update(String username, UserInfo key,String val){
//        User user=new User()
        //TODO
    }
}
