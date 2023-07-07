package server;

import dao.OrderDao;
import entity.Car;
import entity.Insurance;
import entity.Order;
import utils.ExcelReader;
import utils.SqlState;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class PurchaseCar {
    private static final OrderDao manage = new OrderDao();

    private static final Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类


    private static List<Insurance> insuranceList;

    /**
     * 添加预写入的订单信息
     * 例：
     * System.out.println(addOrder(...));
     * <p>
     * 定价为车辆价格的10%
     * 汽车购置税为订单总额的10%
     * 店内优惠制度为：购买纯动力汽车打九五折
     * 上牌价格当前默认1000元
     *
     * @param order 传入已添加用户信息和管理员信息的order订单
     */
    public static Order genPreOrder(Order order, Car car) {
        int carId = car.getCarId();
        double price = car.getPrice();
        int deposit = culDeposit(price);
        int pmtDiscount = culPmtDiscount(car.getPowerType(), price);
        int purchaseTax = culPurchaseTax(order.getInsurances(), price, deposit, pmtDiscount, order.getHasLicenseServer());
        return order.setPurchaseTax(purchaseTax).setCarId(carId).setDeposit(deposit).setPmtDiscount(pmtDiscount).setOrderTime(new Date()).setDeliveryTime(null);
    }

    /**
     * 将订单修改为已支付状态
     */
    public static boolean confirmOrder(Order order) {
        if (manage.updateOrder(order.setIsPay(String.valueOf(true))) == SqlState.SqlError) {
            logger.warning("数据库更新失败");
            return false;
        }
        else return manage.updateOrder(order.setIsPay(String.valueOf(true))) == SqlState.Done;
    }


    /**
     * 添加前置订单到数据库
     * @param order 缺少
     */
    public static void addPreOrder(Order order){
        manage.addOrder(order);
    }


    /**
     * 正常情况下是没有保险的金额的，需要从数据库中获取
     * 为了减少数据库查询，这里尽量只查询一次
     */
    public static Double getInsuranceListPrice(List<Insurance> insurance) {
        Double sum = 0.0;
        if (insuranceList == null) {
            insuranceList = manage.searchAllInsurance();
        }
        for (Insurance ins : insurance) {
            for (Insurance item : insuranceList) {
                if (item.getInsName().equals(ins.getInsName())) {
                    ins.setPrice(item.getPrice());
                    sum += item.getPrice();
                }
            }
        }
        return sum;
    }



    public static Car getCarByBrandSeries(Car car) {
        return CarManage.searchCarByBrandSeries(car.getBrand(), car.getSeries());
    }



    /**
     * 获取订单总金额（不包含服务费）
     */
    public static Double getSum(Order order, Car car) {
        double sum = 0;
        sum += car.getPrice(); // 车辆价格
        sum += order.getDeposit(); // 定金
        sum += getInsuranceListPrice(order.getInsurances()); // 保险
        sum += order.getPurchaseTax(); // 税额
        sum -= order.getPmtDiscount(); // 折扣
        if (order.getHasLicenseServer()) sum += 0.1; // 上牌
        return sum;
    }

//    /**
//     * 服务费为订单总额的1%
//     */
//    public static Double getServerPrice(Order order, Car car) {
//        final double rate = 0.01;
//        return getSum(order, car) * rate;
//    }

    /**
     * 根据订单id搜索订单
     */
    public static List<Order> searchOrder(int id) {
        Order o = new Order();
        return manage.searchOrder(o.setOrderId(id));
    }

    /**
     * 定价为车辆价格的10%
     */
    private static int culDeposit(double price) {
        final double rate = 0.1;
        return (int) (rate * price);
    }

    /**
     * 折扣
     * 纯电动打九五折
     */
    private static int culPmtDiscount(String type, double price) {
        final double rate = 0.05;
        if (type.equals("纯电动")) {
            return (int) (rate * price);
        } else {
            return (int) price;
        }
    }

    /**
     * 税额
     * 税额为计税额的10%
     */
    private static int culPurchaseTax(List<Insurance> insurances, double price, double deposit, double pmtDiscount, boolean hasLicenseServer) {
        // 牌照价格
        final double licensePrice = 0.1;
        // 税额
        final double rate = 0.1;
        double sum = 0;
        sum += getInsuranceListPrice(insurances);
        sum += price; // 车辆价格
        sum += deposit; // 定价
        sum -= pmtDiscount;  // 优惠价格
        if (hasLicenseServer) sum += licensePrice; //上牌价格默认0.

        return (int) (sum * rate);
    }

}
