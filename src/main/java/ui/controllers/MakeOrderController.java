package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckListView;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/4
 * description：
 */
public class MakeOrderController implements Initializable {
    @FXML
    private MFXComboBox<String> combo_brand;
    @FXML
    private MFXComboBox<String> combo_model;
    @FXML
    private MFXComboBox<String> combo_regCar;
    @FXML
    private MFXCheckListView<String> list_insurance;
    @FXML
    private MFXTextField text_addr;
    @FXML
    private MFXTextField text_name;
    @FXML
    private MFXTextField text_sid;
    @FXML
    private MFXTextField text_tel;
    @FXML
    private MFXButton btn_confirm;

    String name, tel, sid, addr;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] insuranceArray = {"交强险", "第三者责任险", "车损险", "附加险"};
        ObservableList<String> insuranceList = FXCollections.observableList(Arrays.stream(insuranceArray).toList());
        list_insurance.setItems(insuranceList);

        String[] regCarArray = {"是", "否"};
        ObservableList<String> regCarList = FXCollections.observableList(Arrays.stream(regCarArray).toList());
        combo_regCar.setItems(regCarList);

        text_name.setOnAction(c -> {
            name = text_name.getText();
            System.out.println("text_name on action");
        });
        text_addr.setOnMouseExited(c -> {
            addr = text_addr.getText();
            System.out.println("text_addr on input method text changed");
        });
        text_tel.setOnAction(c -> {
            tel = text_tel.getText();
        });
        text_sid.setOnAction(c -> {
            sid = text_sid.getText();
        });

        btn_confirm.setOnMouseClicked(c -> {
            System.out.println(name + addr + tel + sid);
        });

    }
}
