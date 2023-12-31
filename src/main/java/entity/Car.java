package entity;

import java.util.Objects;

public class Car {
    private Integer carId;
    private Double price;
    private String type;
    private String powerType;
    private String brand;
    private String series;

    public Car() {}

    public Car(Double price, String type, String powerType, String brand, String series) {
        this.price = price;
        this.type = type;
        this.powerType = powerType;
        this.brand = brand;
        this.series = series;
    }

    public Car(int carId, Double price, String type, String powerType, String brand, String series) {
        this(price,type,powerType,brand,series);
        this.carId = carId;
    }

    public Integer getCarId() {
        return carId;
    }

    public Car setCarId(Integer carId) {
        this.carId = carId;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Car setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getType() {
        return type;
    }

    public Car setType(String type) {
        this.type = type;
        return this;
    }

    public String getPowerType() {
        return powerType;
    }

    public Car setPowerType(String powerType) {
        this.powerType = powerType;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public Car setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getSeries() {
        return series;
    }

    public Car setSeries(String series) {
        this.series = series;
        return this;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", powerType='" + powerType + '\'' +
                ", brand='" + brand + '\'' +
                ", series='" + series + '\'' +
                '}';
    }

    /**
     * 用于多字段查询中判断车辆是否相等
     * @return
     */
    @Override
    public int hashCode() {
        return carId.hashCode();
    }

    /**
     * 用于多字段查询中判断车辆是否相等
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Car) {
            Car c = (Car) obj;
            return Objects.equals(this.getCarId(), c.getCarId());
        } else return false;
    }
}
