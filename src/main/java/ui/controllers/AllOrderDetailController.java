/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/8
 * description：
 */
package ui.controllers;

import entity.Order;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import ui.AppUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class AllOrderDetailController implements Initializable {
    @FXML
    private MFXButton btn_deleteOrder;
    @FXML
    private MFXScrollPane pane_orderDetail;

    private final ConfirmOrderController confirmOrderController;
    private Order order;

    public AllOrderDetailController() {
        confirmOrderController = new ConfirmOrderController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent orderDetail = AppUtil.loadView("fxml/ConfirmOrder.fxml", confirmOrderController);
        pane_orderDetail.setContent(orderDetail);
        ScrollUtils.addSmoothScrolling(pane_orderDetail);

        btn_deleteOrder.setOnMouseClicked(event -> {
            //TODO:delete order
        });
    }

    public void setOrder(Order order) {
        this.order = order;
//        confirmOrderController.setContent(order,null);//TODO:Car信息合并入Order
    }
}
