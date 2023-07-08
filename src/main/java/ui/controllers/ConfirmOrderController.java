/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/6
 * description：
 */
package ui.controllers;

import entity.Insurance;
import entity.Order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.RowConstraints;
import server.PurchaseCar;

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
    @FXML
    private RowConstraints grid9;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setContent(Order order) {
        addr.setText(order.getCusAddress());
        brand.setText(order.getCar().getBrand());
        carPrice.setText((order.getCar().getPrice()*10000)+" 元");
        insurance.setText(formatInsuranceList(order.getInsurances()));
        insurancePrice.setText(PurchaseCar.getInsuranceListPrice(order.getInsurances()).toString()+" 元");
        model.setText(order.getCar().getSeries());
        name.setText(order.getCusName());
        regCar.setText(order.getHasLicenseServer() ? "是" : "否");
        servicePrice.setText(PurchaseCar.getServerPrice(order)+" 元");
        sid.setText(order.getCusId());
        taxPrice.setText(order.getPurchaseTax().toString()+" 元");
        tel.setText(order.getCusPhone());
        totalPrice.setText(PurchaseCar.getSum(order)+" 元");
    }

    /**
     * 修正边框高度，以便容纳保险名单
     */
    private String formatInsuranceList(List<Insurance> insurances) {
        StringBuilder sj = new StringBuilder();
        for (int i = 0; i < insurances.size(); i++) {
            sj.append(insurances.get(i).getInsName());
            if(i%2==1){
                sj.append("\n");
            } else {
                sj.append(" ");
            }
        }
        insurance.setWrapText(true);
        grid9.setMaxHeight(25 * insurances.size());
        return sj.toString();
    }

}