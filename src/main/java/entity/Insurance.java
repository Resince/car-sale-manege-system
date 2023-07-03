package entity;

public class Insurance {
    private String insName;
    private Double price;

    public Insurance(String insName){
        this.insName = insName;
    }

    public Insurance(String insName, Double price) {
        this.insName = insName;
        this.price = price;
    }

    public String getInsName() {
        return insName;
    }

    public Insurance setInsName(String insName) {
        this.insName = insName;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public Insurance setPrice(Double price) {
        this.price = price;
        return this;
    }
}
