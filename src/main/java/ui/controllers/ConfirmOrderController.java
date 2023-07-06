/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/6
 * description：
 */
package ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
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

    public void setContent(String[] contents) {
        addr.setText(contents[0]);
        addr.setText(contents[1]);
        brand.setText(contents[2]);
        carPrice.setText(contents[3]);
        insurance.setText(contents[4]);
        insurancePrice.setText(contents[5]);
        model.setText(contents[6]);
        name.setText(contents[7]);
        regCar.setText(contents[8]);
        servicePrice.setText(contents[9]);
        sid.setText(contents[10]);
        taxPrice.setText(contents[11]);
        tel.setText(contents[12]);
        totalPrice.setText(contents[13]);
    }
}
