package ui;

import entity.Car;
import entity.Order;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private static Order preOrder;
    private static Car car;

    public static Car getCar() {
        return car;
    }

    public static void setCar(Car car) {
        Model.car = car;
    }

    public static Order getPreOrder() {
        return preOrder;
    }

    public static void setPreOrder(Order preOrder) {
        Model.preOrder = preOrder;
    }

}
