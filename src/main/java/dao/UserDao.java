package dao;

import entity.User;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import utils.AuthState;
import utils.SqlConnection;

import java.util.List;

public class UserDao {
    UserMapper userDao;

    /**
     * 添加user
     */
    public int addUser(User user) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        int ans = userDao.addUser(user);
        sqlSession.commit();
        sqlSession.close();
        return ans;
    }

    /**
     * 根据userId删除用户
     */
    public int deleteUserById(int userId) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        int ans = userDao.deleteUserById(userId);
        sqlSession.commit();
        sqlSession.close();
        return ans;
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
        List<User> users = userDao.searchUser(user);
        sqlSession.close();
        return users;
    }

    public List<User> searchAllCarList(){
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        List<User> users = userDao.searchUser(new User());
        sqlSession.close();
        return users;
    }

    /**
     * 按照非空字段进行更新，空字段保留
     */
    public int updateUser(User user) {
        SqlSession sqlSession = SqlConnection.getSession();
        userDao = sqlSession.getMapper(UserMapper.class);
        int ans = userDao.updateUser(user);
        sqlSession.commit();
        sqlSession.close();
        return ans;
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
            sqlSession.close();
            return AuthState.InvalidUsername;
        }
        String res = userDao.authenticate(inputPhoneNumber, inputPasswd);
        if (inputPasswd != null && res == null) {
            sqlSession.close();
            return AuthState.InvalidPassword;
        }
        if (res.equals("Admin")) {
            sqlSession.close();
            return AuthState.DoneAdmin;
        } else if (res.equals("CarManager")) {
            sqlSession.close();
            return AuthState.DoneCarManager;
        }else{
            sqlSession.close();
            return AuthState.DoneSalesman;
        }
    }
}
