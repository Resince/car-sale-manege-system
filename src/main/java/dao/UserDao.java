package dao;

import entity.Car;
import entity.User;
import mapper.CarMapper;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import utils.AuthState;
import utils.SqlConnection;
import utils.SqlState;

import java.util.List;

public class UserDao {
    UserMapper userDao;

    /**
     * 添加user
     *
     * @return 返回user, 其中有自增的主键
     */
    public SqlState addUser(User user) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        int ans = userDao.addUser(user);
        System.out.println(user.getUserId());
        sqlSession.commit();
        return SqlState.Done;
    }

    /**
     * 根据userId删除用户
     */
    public SqlState deleteUserById(int userId) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        int ans = userDao.deleteUserById(userId);
        sqlSession.commit();
        return SqlState.Done;
    }

    /**
     * 按照非空字段进行搜索
     *
     * @param user 非空字段被认为是搜索对象
     * @return 返回user的list
     */
    public List<User> searchUser(User user) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        return userDao.searchUser(user);
    }

    public List<User> searchAllCarList(){
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        return userDao.searchUser(new User());
    }

    /**
     * 按照非空字段进行更新，空字段保留
     */
    public SqlState updateUser(User user) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        int ans = userDao.updateUser(user);
        sqlSession.commit();
        return SqlState.Done;
    }

    /**
     * 身份认证，如果只有userId，那么就是寻找是否有这个用户
     * 如果都有且不为空，则是验证这个账号的密码是否正确
     * 但是在验证密码是否是否为正确时，会提前验证是否账号是否存在
     *
     * @return AuthState
     */
    public AuthState authenticate(String inputPhoneNumber, String inputPasswd) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        if (userDao.authenticate(inputPhoneNumber, null) == null) {
            return AuthState.InvalidUsername;
        }
        String res = userDao.authenticate(inputPhoneNumber, inputPasswd);
        if (inputPasswd != null && res == null) {
            return AuthState.InvalidPassword;
        }
        if (res.equals("Admin")) {
            return AuthState.DoneAdmin;
        } else if (res.equals("CarManager")) {
            return AuthState.DoneCarManager;
        }else{
            return AuthState.DoneSalesman;
        }
    }
}
