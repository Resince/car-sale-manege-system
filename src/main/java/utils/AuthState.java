package utils;

/**
 * 登录状态
 */
public enum AuthState {
    InvalidUsername,    //用户名不存在
    InvalidPassword,    //密码错误
    Done  // 用户名与密码均正确
}