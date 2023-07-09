package utils;

/**
 * 登录状态
 */
public enum AuthState {
    InvalidUsername,    // 用户名不存在
    InvalidPassword,    // 密码错误
    DoneAdmin,          // 管理员
    DoneSeller,       // 销售
    DoneManager      // 汽车管理
}