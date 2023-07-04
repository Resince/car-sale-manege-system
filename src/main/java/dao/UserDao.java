package dao;

import entity.User;
import impl.UserMapper;
import org.apache.ibatis.session.SqlSession;
import utils.AuthState;
import utils.SqlConnection;
import utils.SqlState;

import java.util.List;

public class UserDao {
    UserMapper userDao;

    /**
     * 添加user
     * @return 返回user,其中有自增的主键
     */
    public SqlState addUser(User user) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            int ans = userDao.addUser(user);
            System.out.println(user.getUserId());
            sqlSession.commit();
            return SqlState.Done;
        }
    }

    /**
     * 按照非空字段进行搜索
     * @param user 非空字段被认为是搜索对象
     * @return 返回user的list
     */
    public List<User> searchUser(User user) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            return userDao.searchUser(user);
        }
    }

    /**
     * 按照非空字段进行更新，空字段保留
     */
    public SqlState updateUser(User user) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            int ans = userDao.updateUser(user);
            sqlSession.commit();
            return SqlState.Done;
        }
    }

    /**
     * 身份认证，如果只有userId，那么就是寻找是否有这个用户
     * 如果都有且不为空，则是验证这个账号的密码是否正确
     * 但是在验证密码是否是否为正确时，会提前验证是否账号是否存在
     * @return AuthState
     */
    public AuthState authenticate(int inputUserId, String inputPasswd) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            if(userDao.authenticate(inputUserId, null)==null){
                return AuthState.InvalidUsername;
            }else if(inputPasswd!=null && userDao.authenticate(inputUserId,inputPasswd)==null){
                return AuthState.InvalidPassword;
            }else {
                return AuthState.Done;
            }
        }
    }
}
