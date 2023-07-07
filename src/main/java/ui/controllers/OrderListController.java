/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import ui.AppUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.layout.GridPane;

import javafx.scene.layout.AnchorPane;

public class OrderListController implements Initializable {
    @FXML
    private MFXListView<MFXButton> list_orders;
    @FXML
    private AnchorPane root;

    @FXML
    private GridPane priPage;

    private final PreOrderDetailController preOrderDetailController;

    public OrderListController() {
        preOrderDetailController = new PreOrderDetailController(this::back);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent secPage = AppUtil.loadView("fxml/PreOrderDetail.fxml", preOrderDetailController);

        MFXButton btn = new MFXButton();
        btn.setText("Test Button");
        btn.setOnMouseClicked(event -> {
            System.out.println("clicked");
            root.getChildren().setAll(secPage);
        });
        List<MFXButton> list_btn = Arrays.asList(btn);
        list_orders.setItems(FXCollections.observableList(list_btn));
    }

    public void back() {
        root.getChildren().setAll(priPage);
    }
}
