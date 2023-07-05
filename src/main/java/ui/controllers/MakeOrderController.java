package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
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
    private MFXComboBox<String> combo_insurance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] insuranceArray={"sd","ww"};
        ObservableList<String> insuranceList= FXCollections.observableList(Arrays.stream(insuranceArray).toList());
        combo_insurance.setItems(insuranceList);
    }
}
