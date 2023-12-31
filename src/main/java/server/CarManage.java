package server;


import dao.CarDao;
import entity.Car;
import org.apache.commons.collections4.CollectionUtils;
import utils.ExcelReader;
import utils.SqlState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import static java.util.stream.Collectors.toList;

public class CarManage {

    /**
     *  总体要求：需要实现对车辆数据的增删改查
     *  需要支持批量添加，即通过分析excel来对添加到数据库
     *  需要在添加以及更新字段时对数据进行约束
     *  其他的自行添加
     */

    private static final CarDao manage = new CarDao();

    private static final Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类

    /**
     *添加单个车辆信息
     */
    public static boolean addCar(double price, String type, String powerType, String brand, String series) {
        Car car = new Car(price, type, powerType, brand, series);
        return manage.addCar(car).getCarId()!=null;
    }

    /**
     * 批量添加车辆信息
     * @param fileName Excel文件地址
     * @return 是否添加成功
     */
    public static boolean addCarList(String fileName){
        List<Car> add = ExcelReader.readExcel(fileName);
        if (null == add)
        {
            logger.warning("文件读取数据为空！");
            return false;
        }
        else {
            if (manage.addCar(add) == SqlState.SqlError) {
                logger.warning("数据库添加失败");
                return false;
            } else return manage.addCar(add) == SqlState.Done;
        }
    }

    /**
     * 根据carID进行删除
     * @param carID 汽车id
     * @return SqlState
     */
    public static boolean deleteCar(int carID) {
        if (manage.deleteCarById(carID) == SqlState.SqlError) {
            logger.warning("数据库删除失败");
            return false;
        } else return manage.deleteCarById(carID) == SqlState.Done;
    }

//    /**
//     * 批量删除车辆信息
//     * @param fileName Excel文件地址
//     * @return boolean
//     */
//    public static boolean deleteCarList(String fileName){
//        List<Car> delete = ExcelReader.readExcel(fileName);
//        if (null == delete)
//        {
//            logger.warning("文件读取数据为空！");
//            return false;
//        }
//        else {
//            if (manage.addCar(delete) == SqlState.SqlError) {
//                logger.warning("数据库删除失败");
//                return false;
//            } else return manage.addCar(delete);
//        }
//    }

    /**
     * 根据车辆类型进行搜索
     * @param type 车辆类型
     * @return 返回车辆链表
     */
    public static List<Car> searchCarByType(String type) {
        Car c = new Car();
        c.setType(type);
        return manage.searchCar(c);
    }

    /**
     * 根据车辆品牌进行搜索
     * @param brand 车辆品牌
     * @return 返回车辆链表
     */
    public static List<Car> searchCarByBrand(String brand) {
        Car c = new Car();
        c.setBrand(brand);
        return manage.searchCar(c);
    }

    /**
     * 根据车辆能源类型进行搜索
     * @param powerType 车辆能源类型
     * @return 返回车辆链表
     */
    public static List<Car> searchCarByPowerType(String powerType) {
        Car c = new Car();
        c.setPowerType(powerType);
        return manage.searchCar(c);
    }

    /**
     * 根据车辆系列进行搜索
     * @param series 车辆系列
     * @return 返回车辆链表
     */
    public static List<Car> searchCarBySeries(String series) {
        Car c = new Car();
        c.setSeries(series);
        return manage.searchCar(c);
    }

    /**
     * 根据价格区间查找车辆，闭区间
     * @param price1 区间左侧
     * @param price2 区间右侧
     * @return 返回车辆链表
     */
    public static List<Car> searchCarByPrice(double price1, double price2) {
        return manage.searchCarByPrice(price1, price2);
    }

    /**
     * 根据多字段查询
     *
     * 例：
     * CarManage.searchCarByMultiple(searchCarByPrice(100000, 200000), searchCarByBand("dd");
     * 根据三个字段查询则再套用一层
     * 以此类推..
     *
     * @param l1 根据字段1查询返回的list
     * @param l2 根据字段2查询返回的list
     * @return 返回两次查询结果的交集
     */
    public static List<Car> searchCarByMultiple(List<Car> l1, List<Car> l2) {
        Collection<Car> c = CollectionUtils.intersection(l1, l2);
        return new ArrayList<>(c);
    }

    /**
     * 更新车辆信息
     * @param carID 必填，不能为空
     * @param price 不需要修改则传入null
     * @param type 不需要修改则传入null
     * @param powerType 不需要修改则传入null
     * @param brand 不需要修改则传入null
     * @param series 不需要修改则传入null
     * @return 返回是否更新成功
     */
    public static boolean updateCar(int carID, Double price, String type, String powerType, String brand, String series) {
        Car c = new Car(carID, price, type, powerType, brand, series);
        if (manage.UpdateCar(c) == SqlState.SqlError) {
            logger.warning("数据库更新失败!");
            return false;
        } else return manage.UpdateCar(c) == SqlState.Done;
    }

}
