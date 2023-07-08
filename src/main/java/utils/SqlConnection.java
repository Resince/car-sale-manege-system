package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlConnection {
    private static final String RESOURCE = "mybatis-config.xml";
    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            Reader reader=Resources.getResourceAsReader(RESOURCE);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对应一次数据库会话，SqlSession的作用域应该是函数体类，需要及时的close
     * 每次连接数据库都应该分配不同的SqlSession,不应返回同一个SqlSession
     *
     * @return SqlSession
     */
    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }

    public static SqlSession getSession(boolean Batch){
        return sqlSessionFactory.openSession(ExecutorType.BATCH);
    }
}

