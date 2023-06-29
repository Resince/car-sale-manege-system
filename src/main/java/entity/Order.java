package entity;

import java.time.LocalDate;

public class Order {
    private String username;
    private int carId;
    private LocalDate time;
    private String PurchaseMethod;
    private String remark;

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

    public void setTime(LocalDate time) {
        this.time = time;
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
