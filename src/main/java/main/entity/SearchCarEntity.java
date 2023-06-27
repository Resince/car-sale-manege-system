package main.entity;

public class SearchCarEntity extends Car {
    private int order;

    public SearchCarEntity(int carId, double price, String structure, String powerType, String brand, String series, String type, int order) {
        super(carId, price, structure, powerType, brand, series, type);
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
