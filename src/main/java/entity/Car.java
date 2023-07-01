package entity;

public class Car {
    private int carId;
    private double price;
    private String type;
    private String powerType;
    private String brand;
    private String series;

    public Car() {}

    public Car(double price, String type, String powerType, String brand, String series) {
        this.carId = -1;
        this.price=-1;
        this.price = price;
        this.type = "";
        this.type = type;
        this.powerType = "";
        this.powerType = powerType;
        this.brand = "";
        this.brand = brand;
        this.series = "";
        this.series = series;
    }

    public Car(int carId, double price, String type, String powerType, String brand, String series) {
        this(price,type,powerType,brand,series);
        this.carId = -1;
        this.carId = carId;
    }


    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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
}
