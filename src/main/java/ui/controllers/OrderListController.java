/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
package ui.controllers;

import entity.Car;
import entity.Order;
import io.github.palexdev.materialfx.controls.MFXContextMenu;
import io.github.palexdev.materialfx.controls.MFXContextMenuItem;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrderListController implements Initializable {
    @FXML
    private MFXPaginatedTableView<Order> table_orderList;
    @FXML
    private Label subtitle;

    Consumer<Order> action;
    List<MFXTableColumn<Order>> columns = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HashMap<String, Function<Order, ? extends Comparable>> metaColumn = new HashMap<>();
        metaColumn.put("订单ID",Order::getOrderId);
        metaColumn.put("客户姓名",Order::getCusName);
        metaColumn.put("车辆品牌",order -> order.getCar().getBrand());
        metaColumn.put("车辆系列",order -> order.getCar().getSeries());
        metaColumn.put("车辆价格",order -> order.getCar().getPrice());
        metaColumn.put("销售工号",Order::getUserId);

        final double cWidth=580.0/metaColumn.size();
        List<MFXTableColumn<Order>> columns = new ArrayList<>();
        for (String k : metaColumn.keySet()) {
            MFXTableColumn<Order> column = new MFXTableColumn<>(k, false, Comparator.comparing(metaColumn.get(k)));
            column.setRowCellFactory(order -> new MFXTableRowCell<>(metaColumn.get(k)));
            column.setPrefWidth(cWidth);
            column.setMaxWidth(cWidth);
            column.setMinWidth(cWidth);
            columns.add(column);
        }
        table_orderList.getTableColumns().setAll(FXCollections.observableList(columns));

        table_orderList.getFilters().add(new IntegerFilter<>("序号", Order::getOrderId));
        table_orderList.getFilters().add(new StringFilter<>("客户姓名", Order::getCusName));
        table_orderList.getFilters().add(new StringFilter<>("车辆品牌", order -> order.getCar().getBrand()));
        table_orderList.getFilters().add(new StringFilter<>("车辆系列", order -> order.getCar().getSeries()));
        table_orderList.getFilters().add(new DoubleFilter<>("车辆价格", order -> order.getCar().getPrice()));
        table_orderList.getFilters().add(new IntegerFilter<>("销售工号", Order::getUserId));

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