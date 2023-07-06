package ui.controllers;

import entity.Car;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.CarManage;
import ui.CtrlApp;

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
    private GridPane gradPane_content;
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

    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;

    String name, tel, sid, addr;

    Stage stage;

    public MakeOrderController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String[] insuranceArray = {"交强险", "第三者责任险", "车损险", "附加险"};
        ObservableList<String> insuranceList = FXCollections.observableList(Arrays.stream(insuranceArray).toList());
        list_insurance.setItems(insuranceList);

        String[] regCarArray = {"是", "否"};
        ObservableList<String> regCarList = FXCollections.observableList(Arrays.stream(regCarArray).toList());
        combo_regCar.setItems(regCarList);

        MFXTextField textField=new MFXTextField();
        textField.setFloatingText("114");
        textField.setText("233333");

        dialogContent=MFXGenericDialogBuilder.build().get();
        MFXScrollPane scrollPane=new MFXScrollPane();
        scrollPane.setMaxSize(450,380);
        Parent view_confirmOrder = CtrlApp.loadView("fxml/ConfirmOrder.fxml", null);
        scrollPane.setContent(view_confirmOrder);
        dialogContent.setContent(scrollPane);

        dialog=MFXGenericDialogBuilder.build(dialogContent).toStageDialogBuilder().get();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setDraggable(true);
        dialog.setTitle("Confirm Order");
        dialog.setOwnerNode(gradPane_content);
        dialog.setScrimPriority(ScrimPriority.WINDOW);
        dialog.setScrimOwner(true);

        Map<String, Set<String>> bsMap = CarManage.getBSMap();
        List<String> brandlist = bsMap.keySet().stream().toList();
        combo_model.setItems(FXCollections.observableList(CarManage.getSeries().stream().toList()));
        combo_brand.setItems(FXCollections.observableList(brandlist));
        combo_brand.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov ,String old_value,String new_value)->{
                    combo_model.setItems(FXCollections.observableList(bsMap.get(new_value).stream().toList()));
                }
        );
/*
        dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .get();
        dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(true)
                .setTitle("Dialogs Preview")
                .setOwnerNode(gradPane_content)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
*/
        dialogContent.addActions(
                Map.entry(new MFXButton("Confirm"), event -> {
                }),
                Map.entry(new MFXButton("Cancel"), event -> dialog.close())
        );

        btn_confirm.setOnMouseClicked(c -> {
            name = text_name.getText();
            sid = text_sid.getText();
            tel = text_tel.getText();
            addr = text_addr.getText();
            System.out.println(name + addr + tel + sid);

            MFXFontIcon infoIcon = new MFXFontIcon("fas-circle-info", 18);
            dialogContent.setHeaderIcon(infoIcon);
            dialogContent.setHeaderText("Confirm");
            dialog.showDialog();
        });
    }
}
