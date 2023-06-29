package utils;

public enum RegState {
    UsernameExists,     //用户名已存在
    ShortPassword,      //密码太短(<6)
    LongPassword,       //密码太长(>10)
    InvalidCharacter,   //密码有非法字符
    SimplePassword,     //密码太简单(没有至少包含数字+字母)
    InvalidPhoneNumber, //非法手机号
    Done
}
