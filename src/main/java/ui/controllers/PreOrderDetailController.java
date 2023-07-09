/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
package ui.controllers;

import entity.Order;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import server.PurchaseCar;
import ui.AppUtil;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class PreOrderDetailController implements Initializable {
    @FXML
    private HBox box_payMethod;
    @FXML
    private MFXButton btn_confirm;
    @FXML
    private MFXComboBox<String> combo_num;
    @FXML
    private MFXDatePicker date_delivery;
    @FXML
    private Label label_total;
    @FXML
    private MFXScrollPane pane_orderDetail;
    @FXML
    private MFXRadioButton rbtn_full;
    @FXML
    private MFXRadioButton rbtn_installment;
    @FXML
    private Label label_payment;
    @FXML
    private Label label_num;

    private final ConfirmOrderController confirmOrderController;
    private final ToggleGroup toggleGroup;
    private Double curTot;
    private Order curOrder;
    private final List<String> insNumList;
    private List<MFXTextField> needValidate;
    private Runnable closeAction;

    public PreOrderDetailController() {
        insNumList = Arrays.asList("6期", "12期", "24期");
        confirmOrderController = new ConfirmOrderController();
        toggleGroup = new ToggleGroup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent orderDetail = AppUtil.loadView("fxml/ConfirmOrder.fxml", confirmOrderController);
        pane_orderDetail.setContent(orderDetail);
        ScrollUtils.addSmoothScrolling(pane_orderDetail);

        toggleGroup.getToggles().add(rbtn_full);
        toggleGroup.getToggles().add(rbtn_installment);

        rbtn_full.setOnMouseClicked(event -> {
            label_num.setVisible(false);
            combo_num.setVisible(false);
            combo_num.setText("支付");
            label_payment.setText(String.format("全款支付%.4f万元", curTot));
        });
        rbtn_installment.setOnMouseClicked(event -> {
            label_num.setVisible(true);
            combo_num.setVisible(true);
            combo_num.clear();
            label_payment.setText("");
        });

        combo_num.setItems(FXCollections.observableList(insNumList));
        combo_num.selectedItemProperty().addListener((observableValue, s, t1) -> {
            if (!t1.equals(s)) {
                System.out.println("[combo_num.setOnCommit]");
                double per = curTot / getInsNum();
                label_payment.setText(String.format("分期%s，每期支付%.4f万元", t1, per));
            }
        });

        btn_confirm.setOnMouseClicked(event -> {
            if (AppUtil.checkValid(needValidate) & (getPayMethod() != null)) {
                confirmPay();
                closeAction.run();
            }
        });

        initConstraints();
    }

    private void confirmPay() {
        curOrder.setIsPay("True");
        curOrder.setDeliveryTime(localToDate(date_delivery.getValue()));
        curOrder.setPayMethod(String.format("%s%s",getPayMethod(),combo_num.getText()));
        PurchaseCar.addPaidOrder(curOrder);
        System.out.println(curOrder.toString());
    }

    private Date localToDate(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    private int getInsNum() {
        return switch (combo_num.getText()) {
            case "6期" -> 6;
            case "12期" -> 12;
            case "24期" -> 24;
            default -> 0;
        };
    }

    private String getPayMethod() {
        if (toggleGroup.getSelectedToggle() == null) {
            box_payMethod.pseudoClassStateChanged(AppUtil.INVALID_PSEUDO_CLASS, true);
            return null;
        }
        box_payMethod.pseudoClassStateChanged(AppUtil.INVALID_PSEUDO_CLASS, false);
        MFXRadioButton s=(MFXRadioButton) toggleGroup.getSelectedToggle();
        if(s==rbtn_full)
            return "全款";
        if(s==rbtn_installment)
            return "分期";
        return null;
    }

    private void initConstraints() {
        needValidate = Arrays.asList(combo_num, date_delivery);
        for (MFXTextField textField : needValidate) {
            textField.getStyleClass().add("validated-field");
            AppUtil.addConstraint(textField, AppUtil.ConstraintType.NotNull);
            AppUtil.setValidatorListener(textField);
        }
    }

    public void setOrder(Order order) {
        curOrder = order;
        confirmOrderController.setContent(order);
        label_total.setText(String.format("%.4f 万元", curTot = PurchaseCar.getSum(order)));
        label_payment.setText("");
        label_num.setVisible(false);
        combo_num.setVisible(false);
        combo_num.setText("支付");
        date_delivery.clear();
        toggleGroup.selectToggle(null);
        combo_num.clear();
    }

    public void setCloseAction(Runnable closeAction) {
        this.closeAction = closeAction;
    }
}
