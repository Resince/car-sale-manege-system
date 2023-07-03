package server;


import dao.CarDao;
import entity.Car;
import utils.ExcelReader;

import java.util.List;
import java.util.logging.Logger;

public class CarManage {
    // TODO
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
     * @param price
     * @param type
     * @param powerType
     * @param brand
     * @param series
     */
    public static boolean addCar(double price, String type, String powerType, String brand, String series) {
        // session
        // impl
        Car car = new Car(price, type, powerType, brand, series);
        if (manage.addCar(car).name().equals("SqlError")) {
            logger.warning("数据库添加失败");
            return false;
        } else return true;
        // session close()
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
            if (manage.addCar(add).name().equals("SqlError")) {
                logger.warning("数据库添加失败");
                return false;
            } else return true;
        }
    }

    /**
     * 根据carID进行删除
     * @param carID 汽车id
     * @return SqlState
     */
    public static boolean deleteCar(int carID) {
        if (manage.deleteCarById(carID).name().equals("SqlError")) {
            logger.warning("数据库删除失败");
            return false;
        } else return true;
    }

    /**
     * 批量删除车辆信息
     * @param fileName Excel文件地址
     * @return boolean
     */
    public static boolean deleteCarList(String fileName){
        List<Car> delete = ExcelReader.readExcel(fileName);
        if (null == delete)
        {
            logger.warning("文件读取数据为空！");
            return false;
        }
        else {
            if (manage.addCar(delete).name().equals("SqlError")) {
                logger.warning("数据库删除失败");
                return false;
            } else return true;
        }
    }




    public static void main(String[] args) {
        CarManage.addCarList("C:\\Users\\KiKi\\Desktop\\test.xlsx");
    }

}
