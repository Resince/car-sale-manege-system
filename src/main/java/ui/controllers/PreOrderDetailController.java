/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
package ui.controllers;

import entity.Order;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import ui.AppUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class PreOrderDetailController implements Initializable {
    @FXML
    private MFXScrollPane pane_orderDetail;

    private final ConfirmOrderController confirmOrderController;

    public PreOrderDetailController() {
        confirmOrderController = new ConfirmOrderController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent orderDetail = AppUtil.loadView("fxml/ConfirmOrder.fxml", confirmOrderController);
        pane_orderDetail.setContent(orderDetail);
        ScrollUtils.addSmoothScrolling(pane_orderDetail);
    }

    public void setOrder(Order order){
        confirmOrderController.setContent(order);
    }

}
