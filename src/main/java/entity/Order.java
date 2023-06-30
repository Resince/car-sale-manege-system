package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {
    private int orderId;
    private int carId;
    private int userId;
    private LocalDate orderTime;
    private int cusID;
    private String  cusName;
    private String cusPhone;
    private List<Insurance> insurances;
    private boolean hasLicenseServer;
    private int pmtDiscount;
    private int deposit;
    private LocalDate deliveryTime;
    private int purchaseTax;

    public Order(int orderId, int carId, int userId, LocalDate orderTime, int cusID, String cusName, String cusPhone, List<Insurance> insurances, boolean hasLicenseServer, int pmtDiscount, int deposit, LocalDate deliveryTime, int purchaseTax) {
        this.orderId = orderId;
        this.carId = carId;
        this.userId = userId;
        this.orderTime = orderTime;
        this.cusID = cusID;
        this.cusName = cusName;
        this.cusPhone = cusPhone;
        this.insurances = insurances;
        this.hasLicenseServer = hasLicenseServer;
        this.pmtDiscount = pmtDiscount;
        this.deposit = deposit;
        this.deliveryTime = deliveryTime;
        this.purchaseTax = purchaseTax;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDate orderTime) {
        this.orderTime = orderTime;
    }

    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusPhone() {
        return cusPhone;
    }

    public void setCusPhone(String cusPhone) {
        this.cusPhone = cusPhone;
    }

    public List<Insurance> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<Insurance> insurances) {
        this.insurances = insurances;
    }

    public boolean isHasLicenseServer() {
        return hasLicenseServer;
    }

    public void setHasLicenseServer(boolean hasLicenseServer) {
        this.hasLicenseServer = hasLicenseServer;
    }

    public int getPmtDiscount() {
        return pmtDiscount;
    }

    public void setPmtDiscount(int pmtDiscount) {
        this.pmtDiscount = pmtDiscount;
    }

    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getPurchaseTax() {
        return purchaseTax;
    }

    public void setPurchaseTax(int purchaseTax) {
        this.purchaseTax = purchaseTax;
    }
}
