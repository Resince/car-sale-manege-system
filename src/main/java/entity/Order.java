package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class Order {
    private Integer orderId;
    private Integer carId;
    private Integer userId;
    private Integer cusId;
    private Date orderTime;
    private String cusName;
    private String cusPhone;
    private List<Insurance> insurances;
    private Boolean hasLicenseServer;
    private String payMethod;
    private Integer pmtDiscount;
    private Integer deposit;
    private Date deliveryTime;
    private Integer purchaseTax;
    private String cusAddress;

    private Order(Integer carId, Integer userId, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, Integer purchaseTax,String address) {
        this.carId = carId;
        this.userId = userId;
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.insurances = insurances;
        this.hasLicenseServer = hasLicenseServer;
        this.payMethod = payMethod;
        this.pmtDiscount = pmtDiscount;
        this.deposit = deposit;
        this.purchaseTax = purchaseTax;
        this.cusAddress = address;
    }

    private Order(Integer orderId, Integer carId, Integer userId, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, Integer purchaseTax,String address) {
        this(carId, userId, cusId, cusName, cusPhone, insurances, hasLicenseServer, payMethod, pmtDiscount, deposit, purchaseTax,address);
        this.orderId = orderId;
    }

    // 时间只需要用字符串来创建
    public Order(Integer carId, Integer userId, String orderTime, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, String deliveryTime, Integer purchaseTax,String address) {
        this(carId, userId, cusId, cusName, cusPhone, insurances, hasLicenseServer, payMethod, pmtDiscount, deposit, purchaseTax,address);
        try {
            this.orderTime = orderTime == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(orderTime);
            this.deliveryTime = deliveryTime == null ? null : new SimpleDateFormat("yyyy-MM-dd").parse(deliveryTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 提供给xml文件使用
    public Order(Integer orderId, Integer carId, Integer userId, Integer cusId, Date orderTime, String cusName, String cusPhone, List<Insurance> insurances, String hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, Date deliveryTime, Integer purchaseTax, String address) {
        this(orderId, carId, userId, cusId, cusName, cusPhone, insurances, hasLicenseServer.equals("true"), payMethod, pmtDiscount, deposit, purchaseTax,address);
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
    }

    public Order() {
    }

    public Integer getOrderId() {
        return orderId;
    }

    public Order setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getCarId() {
        return carId;
    }

    public Order setCarId(Integer carId) {
        this.carId = carId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Order setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getCusId() {
        return cusId;
    }

    public Order setCusId(Integer cusId) {
        this.cusId = cusId;
        return this;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public Order setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public String getCusName() {
        return cusName;
    }

    public Order setCusName(String cusName) {
        this.cusName = cusName;
        return this;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public Order setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
        return this;
    }

    public List<Insurance> getInsurances() {
        return insurances;
    }

    public Order setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
        return this;
    }

    public Boolean getHasLicenseServer() {
        return hasLicenseServer;
    }

    public Order setHasLicenseServer(Boolean hasLicenseServer) {
        this.hasLicenseServer = hasLicenseServer;
        return this;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public Order setPayMethod(String payMethod) {
        this.payMethod = payMethod;
        return this;
    }

    public Integer getPmtDiscount() {
        return pmtDiscount;
    }

    public Order setPmtDiscount(Integer pmtDiscount) {
        this.pmtDiscount = pmtDiscount;
        return this;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public Order setDeposit(Integer deposit) {
        this.deposit = deposit;
        return this;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public Order setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
        return this;
    }

    public Integer getPurchaseTax() {
        return purchaseTax;
    }

    public Order setPurchaseTax(Integer purchaseTax) {
        this.purchaseTax = purchaseTax;
        return this;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public Order setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", carId=" + carId +
                ", userId=" + userId +
                ", cusId=" + cusId +
                ", orderTime=" + orderTime +
                ", cusName='" + cusName + '\'' +
                ", cusPhone='" + cusPhone + '\'' +
                ", insurances=" + insurances +
                ", hasLicenseServer=" + hasLicenseServer +
                ", payMethod='" + payMethod + '\'' +
                ", pmtDiscount=" + pmtDiscount +
                ", deposit=" + deposit +
                ", deliveryTime=" + deliveryTime +
                ", purchaseTax=" + purchaseTax +
                ", cusAddress='" + cusAddress + '\'' +
                '}';
    }
}
