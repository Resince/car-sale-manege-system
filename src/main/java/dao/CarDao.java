package dao;

import entity.Car;
import impl.CarMapper;
import org.apache.ibatis.session.SqlSession;
import utils.SqlConnection;
import utils.SqlState;

import java.util.List;

public class CarDao {
    CarMapper carDao;

    /**
     * 添加车辆
     *
     * @param car carId不需要填写,填写了也会忽视,其他的字段应该非空
     * @return 返回Car, 其中包含car新增的carId
     */
    public Car addCar(Car car) {
        SqlSession sqlSession = SqlConnection.getSession();
        carDao = sqlSession.getMapper(CarMapper.class);
        int ans = carDao.addCar(car);
        sqlSession.commit();
        return car;
    }

    /**
     * 批量添加车辆
     *
     * @param carList 车辆的List
     * @return 返回SqlState
     */
    public SqlState addCar(List<Car> carList) {
        SqlSession sqlSession = SqlConnection.getSession(true);
        carDao = sqlSession.getMapper(CarMapper.class);
        for (Car item : carList) {
            carDao.addCar(item);
        }
        sqlSession.commit();
        return SqlState.Done;
    }

    /**
     * 根据carId删除车辆
     *
     * @param carID carId应从原有的类中指定
     * @return 返回SqlState
     */
    public SqlState deleteCarById(int carID) {
        SqlSession sqlSession = SqlConnection.getSession();
        carDao = sqlSession.getMapper(CarMapper.class);
        int ans = carDao.deleteCarById(carID);
        sqlSession.commit();
        return SqlState.Done;
    }

    /**
     * 动态搜索车辆
     *
     * @param car 其中搜索其中的非空字段,如果都为空那么就搜索全部车辆
     * @return 返回 carList
     */
    public List<Car> searchCar(Car car) {
        SqlSession sqlSession = SqlConnection.getSession();
        carDao = sqlSession.getMapper(CarMapper.class);
        return carDao.selectCar(car);
    }

    /**
     * 左闭右闭价格区间查找车辆
     *
     * @param priceLeft  价格区间左侧
     * @param priceRight 价格区间右侧
     * @return 返回车辆列表
     */
    public List<Car> searchCarByPrice(double priceLeft, double priceRight) {
        SqlSession sqlSession = SqlConnection.getSession();
        carDao = sqlSession.getMapper(CarMapper.class);
        return carDao.searchCarByPrice(priceLeft, priceRight);
    }

    /**
     * 根据carID搜索车辆
     *
     * @param carId 非空
     * @return 返回 carList
     */
    public Car selectCarByCarId(int carId) {
        SqlSession sqlSession = SqlConnection.getSession();
        carDao = sqlSession.getMapper(CarMapper.class);
        List<Car> c = carDao.selectCarByCarId(carId);
        return (c == null) ? null : c.get(0);
    }

    /**
     * 动态更新Car信息
     *
     * @param car 参数 carId 为必须填写的字段,其他的字段都可以为空
     * @return 返回 SqlState
     * 有两种car参数实例创建方法
     * <br>
     * 1. 使用new Car(1, null ,null,"电动","大众","A-2")
     * 不需要的字段设为null即可
     * <br>
     * 2. 使用set方法 Car car = new Car() car.setCarId(1)
     * 不需要更新的内容就不设置,默认为空
     * <br>
     * 推荐使用方法2
     */
    public SqlState UpdateCar(Car car) {
        SqlSession sqlSession = SqlConnection.getSession();
        carDao = sqlSession.getMapper(CarMapper.class);
        int ans = carDao.updateCar(car);
        sqlSession.commit();
        return SqlState.Done;
    }

}
