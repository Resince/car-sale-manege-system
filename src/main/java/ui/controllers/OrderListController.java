/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
package ui.controllers;

import entity.Order;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class OrderListController implements Initializable {
    @FXML
    private MFXPaginatedTableView<Order> table_orderList;
    @FXML
    private Label subtitle;

    Consumer<Order> action;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MFXTableColumn<Order> column_orderId = new MFXTableColumn<>("车主", true, Comparator.comparing(Order::getOrderId));
        column_orderId.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getOrderId));
        MFXTableColumn<Order> column_cusName = new MFXTableColumn<>("车主", true, Comparator.comparing(Order::getCusName));
        column_cusName.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getCusName));
//        MFXTableColumn<Order> column_carId = new MFXTableColumn<>("车主", true, Comparator.comparing(Order::getCar));
//        column_carId.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getCarId));

//        List<MFXTableColumn<Order>> columns = Arrays.asList(column_orderId, column_cusName, column_carId);
        List<MFXTableColumn<Order>> columns = Arrays.asList(column_orderId, column_cusName);
        table_orderList.getTableColumns().setAll(FXCollections.observableList(columns));

        table_orderList.autosizeColumnsOnInitialization();

        table_orderList.getSelectionModel().selectionProperty().addListener((observableValue, integerOrderObservableMap, t1) -> {
            if (!t1.isEmpty()) {
                if (t1.size() == 1)
                    action.accept(t1.values().stream().toList().get(0));
                table_orderList.getSelectionModel().clearSelection();
            }
        });
    }

    /**
     * 设置订单列表
     *
     * @param orderList 订单列表
     */
    public void setOrders(List<Order> orderList) {
        table_orderList.setItems(FXCollections.observableList(orderList));
    }

    /**
     * 设置点击动作
     *
     * @param action 点击订单条目之后的反应
     */
    public void setAction(Consumer<Order> action) {
        this.action = action;
    }

    public void setSubtitle(String text) {
        subtitle.setText(text);
    }
}
