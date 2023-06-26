package main.entity;

public class Car {
    private int carId;
    private double price;
    private String structure;
    private String powerType;
    private String brand;
    private String series;
    private String type;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", price=" + price +
                ", structure='" + structure + '\'' +
                ", powerType='" + powerType + '\'' +
                ", brand='" + brand + '\'' +
                ", series='" + series + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
