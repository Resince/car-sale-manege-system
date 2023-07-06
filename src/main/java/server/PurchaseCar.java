package server;

import dao.CarDao;
import dao.OrderDao;
import entity.Car;
import entity.Insurance;
import entity.Order;
import utils.SqlState;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchaseCar {
    private static final OrderDao manage = new OrderDao();

    /**
     * 添加预写入的订单信息
     *
     * @param brand            车辆品牌
     * @param series           车辆系列
     * @param userId           销售员id
     * @param cusID            客户身份证
     * @param cusName          客户姓名
     * @param cusPhone         客户电话号码
     * @param address          客户家庭住址
     * @param insurances       保险信息, 传入list
     * @param hasLicenseServer 上牌
     * @param payMethod        付款方式
     * @return 返回订单实体，可以继续用返回值输出以显示完整订单信息
     * 例：
     * System.out.println(addOrder(...));
     * <p>
     * 定价为车辆价格的10%
     * 汽车购置税为订单总额的10%
     * 店内优惠制度为：购买纯动力汽车打九五折
     * 上牌价格当前默认1000元
     */
    public static Order addPreOrder(String brand, String series, Integer userId, Integer cusID, String cusName, String cusPhone, String address, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod) {
        Car car = new CarDao().searchCar(new Car().setBrand(brand).setSeries(series)).get(0);
        int carId = car.getCarId();
        double price = car.getPrice();
        int deposit = culDeposit(price);
        int pmtDiscount =culPmtDiscount(car.getPowerType(),price);
        int purchaseTax = culPurchaseTax(insurances,price,deposit,pmtDiscount,hasLicenseServer);
        String orderTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String deliverTime = null;//交付时间没定
    return new Order(carId,userId,orderTime,cusID,cusName,cusPhone,insurances,hasLicenseServer,payMethod,pmtDiscount,deposit,deliverTime,purchaseTax,address);
    }

    /**
     * 获取订单总金额
     */
    public static double getSum(Order o) {
        CarDao searchCar = new CarDao();
        Car c = searchCar.selectCarByCarId(o.getCarId());
        double sum = 0;
        for (Insurance i : o.getInsurances()) {
            sum += i.getPrice(); // 保险
        }
        sum += c.getPrice(); // 车辆价格
        sum += o.getDeposit(); // 定金
        sum += o.getPurchaseTax(); // 税额
        sum -= o.getPmtDiscount(); // 折扣
        if (o.getHasLicenseServer()) sum += 0.1; // 上牌
        return sum;
    }


    /**
     * 根据订单id搜索订单
     * @param id 订单id
     * @return 返回订单集合，根据订单id搜索订单时，集合中应只有一个元素
     */
    public static List<Order> searchOrder(int id) {
        Order o = new Order();
        return manage.searchOrder(o.setOrderId(id));
    }

    private static int culDeposit(double price) {
        final double rate = 0.1;
        return (int) (rate*price);
    }

    private static int culPmtDiscount(String type,double price){
        final double rate = 0.05;
        if(type.equals("纯电动")){
            return (int) (rate*price);
        }else {
            return (int) price;
        }
    }

    private static int culPurchaseTax(List<Insurance> insurances,double price,double deposit,double pmtDiscount,boolean hasLicenseServer){
        // 牌照价格
        final double licensePrice = 0.1;
        // 税额
        final double rate = 0.1;
        double sum = 0;
        for (Insurance i : insurances) {
            sum += i.getPrice(); // 保险
        }
        sum += price; // 车辆价格
        sum += deposit; // 定价
        sum -= pmtDiscount;  // 优惠价格
        if (hasLicenseServer) sum += licensePrice; //上牌价格默认0.1万元
        return (int) (sum * rate);
    }
}
