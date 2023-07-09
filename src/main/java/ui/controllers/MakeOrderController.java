package ui.controllers;

import entity.Car;
import entity.Insurance;
import entity.Order;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.CarManage;
import server.PurchaseCar;
import ui.AppUtil;

import java.net.URL;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/4
 * description：
 */
public class MakeOrderController implements Initializable {
    @FXML
    private GridPane gridPane_content;
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

    private MFXStageDialog dialog;
    private final Stage stage;
    private final List<String> insuranceOpts;
    private final List<String> regCarOpts;
    private final List<MFXTextField> needValidate;
    private List<Insurance> insuranceList;
    Order order = new Order();
    ConfirmOrderController confirmOrderController;

    public MakeOrderController(Stage stage) {
        this.stage = stage;
        insuranceOpts = Arrays.asList("交强险", "第三者责任险", "车损险", "附加险");
        regCarOpts = Arrays.asList("是", "否");
        needValidate = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setComboBoxItem();
        setConfirmDialog();
        initConstrains();
        btn_confirm.setOnMouseClicked(c -> {
            if(AppUtil.checkValid(needValidate)){
                updatePreOrder();
                dialog.showDialog();
                initConstrains();
            }
        });
    }
    
    /**
     * 设置下拉菜单内容
     */
    private void setComboBoxItem() {
        Map<String, Set<String>> bsMap = CarManage.getBSMap();
        List<String> brandlist = bsMap.keySet().stream().toList();
        combo_model.setItems(FXCollections.observableList(CarManage.getSeries().stream().toList()));
        combo_brand.setItems(FXCollections.observableList(brandlist));
        combo_brand.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov, String old_value, String new_value) -> {
                    combo_model.clearSelection();
                    combo_model.setItems(FXCollections.observableList(bsMap.get(new_value).stream().toList()));
                }
        );
        list_insurance.setItems(FXCollections.observableList(insuranceOpts));
        combo_regCar.setItems(FXCollections.observableList(regCarOpts));
    }

    /**
     * 设置确认菜单内容
     */
    private void setConfirmDialog() {
        MFXScrollPane scrollPane = new MFXScrollPane();
        scrollPane.setMaxSize(450, 380);
        confirmOrderController = new ConfirmOrderController();
        Parent view_confirmOrder = AppUtil.loadView("fxml/ConfirmOrder.fxml", confirmOrderController);
        scrollPane.setContent(view_confirmOrder);
        ScrollUtils.addSmoothScrolling(scrollPane);

        MFXGenericDialog dialogContent = MFXGenericDialogBuilder.build().get();
        dialogContent.setContent(scrollPane);
        dialogContent.setHeaderIcon(new MFXFontIcon("fas-circle-info", 18));
        dialogContent.setHeaderText("Confirm");

        dialog = MFXGenericDialogBuilder.build(dialogContent).toStageDialogBuilder().get();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setDraggable(true);
        dialog.setTitle("Confirm Order");
        dialog.setOwnerNode(gridPane_content);
        dialog.setScrimPriority(ScrimPriority.WINDOW);
        dialog.setScrimOwner(true);

        dialogContent.addActions(
                Map.entry(new MFXButton("Confirm"), event -> {
                    PurchaseCar.addUnpaidOrder(order.setIsPay("false"));
                    dialog.close();
                }),
                Map.entry(new MFXButton("Cancel"), event -> dialog.close())
        );
    }

    private void updatePreOrder() {
        insuranceList = new ArrayList<>();
        list_insurance.getSelectionModel().getSelectedValues().forEach(item -> {
            insuranceList.add(new Insurance(item));
        });

        order.setHasLicenseServer(combo_regCar.getSelectionModel().getSelectedItem().equals("是"))
                .setCusId(text_sid.getText())
                .setCusName(text_name.getText())
                .setCusPhone(text_tel.getText())
                .setCusAddress(text_addr.getText())
                .setInsurances(insuranceList);

        Car selectCar = CarManage.getCarByBrandSeries(new Car()
                .setBrand(combo_brand.getSelectionModel().getSelectedItem())
                .setSeries(combo_model.getSelectionModel().getSelectedItem()));
        order = PurchaseCar.genPreOrder(order.setCar(selectCar));
        confirmOrderController.setContent(order.setCar(selectCar));
    }

    private void initConstrains() {
        needValidate.addAll(Arrays.asList(
                text_name, text_addr, text_sid, text_tel,
                combo_brand, combo_model, combo_regCar
        ));
        for (MFXTextField textField : needValidate)
            AppUtil.addConstraint(textField, AppUtil.ConstraintType.NotNull);
        AppUtil.addConstraint(text_name, AppUtil.ConstraintType.NoSpecialChar);
        AppUtil.addConstraint(text_tel, AppUtil.ConstraintType.IsPhoneNum);
        AppUtil.addConstraint(text_sid, AppUtil.ConstraintType.IsSID);
        for (MFXTextField textField : needValidate)
            AppUtil.setValidatorListener(textField);
    }
}

