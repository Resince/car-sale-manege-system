package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    private Integer orderId;
    private Integer carId;
    private Integer userId;
    private LocalDate orderTime;
    private Integer cusId;
    private String cusName;
    private String cusPhone;
    private List<Insurance> insurances;
    private Boolean hasLicenseServer;
    private String payMethod;
    private Integer pmtDiscount;
    private Integer deposit;
    private LocalDate deliveryTime;
    private Integer purchaseTax;

    public Order(Integer carId, Integer userId, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, Integer purchaseTax) {
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
    }

    public Order(Integer orderId, Integer carId, Integer userId, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, Integer purchaseTax) {
        this(carId, userId, cusId, cusName, cusPhone, insurances, hasLicenseServer, payMethod, pmtDiscount, deposit, purchaseTax);
        this.orderId = orderId;
    }

    public Order(Integer orderId, Integer carId, Integer userId, LocalDate orderTime, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, LocalDate deliveryTime, Integer purchaseTax) {
        this(orderId, carId, userId, cusId, cusName, cusPhone, insurances, hasLicenseServer, payMethod, pmtDiscount, deposit, purchaseTax);
        this.orderTime = orderTime;
        this.deliveryTime = deliveryTime;
    }

    public Order(Integer carId, Integer userId, String orderTime, Integer cusId, String cusName, String cusPhone, List<Insurance> insurances, Boolean hasLicenseServer, String payMethod, Integer pmtDiscount, Integer deposit, String deliveryTime, Integer purchaseTax) {
        this(carId, userId, cusId, cusName, cusPhone, insurances, hasLicenseServer, payMethod, pmtDiscount, deposit, purchaseTax);
        this.orderTime = orderTime == null ? null : LocalDate.parse(orderTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.deliveryTime = deliveryTime == null ? null : LocalDate.parse(deliveryTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public Order setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
        return this;
    }

    public Integer getCusId() {
        return cusId;
    }

    public Order setCusId(Integer cusId) {
        this.cusId = cusId;
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

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public Order setDeliveryTime(LocalDate deliveryTime) {
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
}
