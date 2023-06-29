package entity;

import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Order {
    private String username;
    private int carId;
    private LocalDate time;
    private String PurchaseMethod;
    private String remark;

    public Order(String username, int carId, LocalDate time, String purchaseMethod, String remark) {
        this.username = username;
        this.carId = carId;
        this.time = time;
        PurchaseMethod = purchaseMethod;
        this.remark = remark;
    }

    public Order(String username, int carId, String time, String purchaseMethod, String remark) {
        this.username = username;
        this.carId = carId;
        this.time = toLocalDate(time);
        PurchaseMethod = purchaseMethod;
        this.remark = remark;
    }

    private LocalDate toLocalDate(String time){
        if(time.equals("")){
            return null;
        }
        return LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = toLocalDate(time);
    }

    public String getPurchaseMethod() {
        return PurchaseMethod;
    }

    public void setPurchaseMethod(String purchaseMethod) {
        PurchaseMethod = purchaseMethod;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "username='" + username + '\'' +
                ", carId=" + carId +
                ", time=" + time +
                ", PurchaseMethod='" + PurchaseMethod + '\'' +
                '}';
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
