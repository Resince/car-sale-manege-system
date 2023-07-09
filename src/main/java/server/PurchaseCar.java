package server;

import dao.OrderDao;
import entity.Car;
import entity.Insurance;
import entity.Order;
import entity.User;
import utils.ExcelReader;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PurchaseCar {
    private static final OrderDao manage = new OrderDao();
    private static final Logger logger = Logger.getLogger(ExcelReader.class.getName()); // 日志打印类
    private static List<Insurance> insuranceList;

    /**
     * 添加预写入的订单信息
     */
    public static Order genPreOrder(Order order) {
        double price = order.getCar().getPrice();
        int deposit = culDeposit(price);
        int pmtDiscount = culPmtDiscount(order.getCar().getPowerType(), price);
        int purchaseTax = culPurchaseTax(order.getInsurances(), price, deposit, pmtDiscount, order.getHasLicenseServer());
        return order.setPurchaseTax(purchaseTax).setDeposit(deposit).setPmtDiscount(pmtDiscount).setOrderTime(new Date()).setDeliveryTime(null);
    }

    /**
     * 将订单修改为已支付状态
     */
    public static boolean addPaidOrder(Order order) {
        if (manage.updateOrder(order.setIsPay(String.valueOf(true))) <=0) {
            logger.warning("数据库更新失败");
            return false;
        } else return manage.updateOrder(order.setIsPay(String.valueOf(true))) >0;
    }

    /**
     * 添加未支付订单到数据库
     */
    public static void addUnpaidOrder(Order order) {
        if(order.getUser()==null){
            order.setUser(new User());
        }
        if(order.getCar()==null){
            order.setCar(new Car());
        }
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

    /**
     * 获取订单总金额（不包含服务费）
     */
    public static Double getSum(Order order) {
        double sum = 0;
        sum += order.getCar().getPrice(); // 车辆价格
        sum += order.getDeposit(); // 定金
        sum += getInsuranceListPrice(order.getInsurances()); // 保险
        sum += order.getPurchaseTax(); // 税额
        sum -= order.getPmtDiscount(); // 折扣
        if (order.getHasLicenseServer()) sum += 0.1; // 上牌
        return sum;
    }

    /**
     * 服务费为订单总额的1%
     */
    public static Double getServerPrice(Order order) {
        final double rate = 0.01;
        return getSum(order) * rate;
    }

    /**
     * 查询所有的未支付订单
     */
    public static List<Order> getUnpaidOrderList() {
        return manage.searchOrder(new Order().setIsPay("false").setCar(new Car()).setUser(new User()));
    }

//    public static List<Order> getPaidOrderList() {
//        return manage.searchOrder(new Order().setIsPay("true").setCar(new Car()).setUser(new User()));
//    }

    public static List<Order> getAllOrderList() {
        return manage.searchOrder(new Order().setCar(new Car()).setUser(new User()));
    }

//    /**
//     * 根据订单id搜索订单
//     */
//    public static List<Order> searchOrder(int id) {
//        Order order = new Order();
//        if(order.getUser()==null){
//            order.setUser(new User());
//        }
//        if(order.getCar()==null){
//            order.setCar(new Car());
//        }
//        return manage.searchOrder(order.setOrderId(id));
//    }

    public static void deleteOrder(Order order){
        if(order.getUser()==null){
            order.setUser(new User());
        }
        if(order.getCar()==null){
            order.setCar(new Car());
        }
        manage.deleteOrder(order);
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
