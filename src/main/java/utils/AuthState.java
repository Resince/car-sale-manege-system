package utils;

/**
 * 登录状态
 */
public enum AuthState {
    InvalidUsername,    // 用户名不存在
    InvalidPassword,    // 密码错误
    DoneAdmin,          // 管理员
    DoneSalesman,       // 销售
    DoneCarManager      // 汽车管理
}