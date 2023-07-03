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

    public SqlState addUser(User user) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            int ans = userDao.addUser(user);
            System.out.println(user.getUserId());
            sqlSession.commit();
            return SqlState.Done;
        }
    }

    public List<User> searchUser(User user) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            return userDao.searchUser(user);
        }
    }

    public SqlState updateUser(User user) {
        try (SqlSession sqlSession = SqlConnection.getSession()) {
            userDao = sqlSession.getMapper(UserMapper.class);
            int ans = userDao.updateUser(user);
            sqlSession.commit();
            return SqlState.Done;
        }
    }

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
