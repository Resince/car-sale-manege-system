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
import java.util.*;
import java.util.function.Consumer;

public class OrderListController implements Initializable {
    @FXML
    private MFXPaginatedTableView<Order> table_orderList;
    @FXML
    private Label subtitle;

    Consumer<Order> action;
    List<MFXTableColumn<Order>> columns = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MFXTableColumn<Order> column_orderId = new MFXTableColumn<>("订单ID", true, Comparator.comparing(Order::getOrderId));
        MFXTableColumn<Order> column_cusName = new MFXTableColumn<>("客户姓名", true, Comparator.comparing(Order::getCusName));
        MFXTableColumn<Order> column_carBrand = new MFXTableColumn<>("车辆品牌", true, Comparator.comparing(order -> order.getCar().getBrand()));
        MFXTableColumn<Order> column_carSeries = new MFXTableColumn<>("车辆系列",true,Comparator.comparing(order -> order.getCar().getSeries()));
        MFXTableColumn<Order> column_carPrice = new MFXTableColumn<>("车辆价格",true,Comparator.comparing(order -> order.getCar().getPrice()));
        MFXTableColumn<Order> column_userName = new MFXTableColumn<>("销售姓名", true, Comparator.comparing(Order::getUserId));




        column_orderId.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getOrderId));
        column_cusName.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getCusName));
        column_carBrand.setRowCellFactory(order -> new MFXTableRowCell<>(order1 -> order1.getCar().getBrand()));
        column_carSeries.setRowCellFactory(order -> new MFXTableRowCell<>(order1 -> order1.getCar().getSeries()));
        column_carPrice.setRowCellFactory(order -> new MFXTableRowCell<>(order1 -> order1.getCar().getPrice()));
        column_userName.setRowCellFactory(order -> new MFXTableRowCell<>(Order::getUserId));

        List<MFXTableColumn<Order>> columns = Arrays.asList(column_orderId, column_cusName, column_carBrand,column_carSeries,column_carPrice,column_userName);
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
