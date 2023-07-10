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
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ConfirmOrderController implements Initializable {
    public Label pmtDiscount;
    @FXML
    private Label addr;
    @FXML
    private Label brand;
    @FXML
    private Label type;
    @FXML
    private Label power;
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

        // 车款
        Double carPrice_ = order.getCar().getPrice() * 10000;
        DecimalFormat df = new DecimalFormat("#.00");
        String res_CarPrice = df.format(carPrice_);
        carPrice.setText(res_CarPrice + " 元");

        insurance.setText(formatInsuranceList(order.getInsurances()));
        insurancePrice.setText(df.format(PurchaseCar.getInsuranceListPrice(order.getInsurances())) + " 元");
        model.setText(order.getCar().getSeries());
        name.setText(order.getCusName());
        regCar.setText(order.getHasLicenseServer() ? "是" : "否");
        servicePrice.setText(df.format(PurchaseCar.getServerPrice(order)) + " 元");
        sid.setText(order.getCusId());
        taxPrice.setText(df.format(order.getPurchaseTax()) + " 元");
        tel.setText(order.getCusPhone());

        double sum = PurchaseCar.getSum(order) + PurchaseCar.getServerPrice(order);
        totalPrice.setText(sum + " 元");

        type.setText(order.getCar().getType());
        power.setText(order.getCar().getPowerType());
        pmtDiscount.setText(df.format(order.getPmtDiscount()) + " 元");
    }

    /**
     * 修正边框高度，以便容纳保险名单
     */
    private String formatInsuranceList(List<Insurance> insurances) {
        StringBuilder sj = new StringBuilder();
        for (int i = 0; i < insurances.size(); i++) {
            sj.append(insurances.get(i).getInsName());
            if (i % 2 == 1) {
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
