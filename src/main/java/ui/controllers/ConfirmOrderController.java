/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/6
 * description：
 */
package ui.controllers;

import entity.Car;
import entity.Insurance;
import entity.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.poi.util.LittleEndian;
import server.PurchaseCar;
import ui.Model;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ConfirmOrderController implements Initializable {
    @FXML
    private Label addr;
    @FXML
    private Label brand;
    @FXML
    private Label carPrice;
    @FXML
    private Label insurance;
    @FXML
    private Label insurancePrice;
    @FXML
    private Label model;
    @FXML
    private Label name;
    @FXML
    private Label regCar;
    @FXML
    private Label servicePrice;
    @FXML
    private Label sid;
    @FXML
    private Label taxPrice;
    @FXML
    private Label tel;
    @FXML
    private Label totalPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public  void loadNewData(){
        Order preOrder = Model.getPreOrder();
        Car car = Model.getCar();
        setContent(preOrder,car);
    }

    public void setContent(Order order,Car car) {
        addr.setText(order.getCusAddress());
        brand.setText(car.getBrand());
        carPrice.setText(car.getPrice().toString());
        insurance.setText(car.getPrice().toString());
        insurancePrice.setText(formatInsuranceList(order.getInsurances()));
        model.setText(car.getSeries());
        name.setText(order.getCusName());
        regCar.setText(order.getHasLicenseServer() ?"是":"否");
        servicePrice.setText(PurchaseCar.getServerPrice(order,car).toString());
        sid.setText(order.getCarId().toString());
        taxPrice.setText(order.getPurchaseTax().toString());
        tel.setText(order.getCusPhone());
        totalPrice.setText(PurchaseCar.getSum(order,car).toString());
    }

    private String formatInsuranceList(List<Insurance> insurances){
        StringBuilder sj = new StringBuilder();
        insurances.forEach(item->sj.append(item.toString()).append("\n"));
        return sj.toString();
    }
}
