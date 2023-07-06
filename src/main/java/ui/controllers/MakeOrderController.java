package ui.controllers;

import entity.Car;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
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
import ui.ViewLoader;
import java.net.URL;
import java.util.*;
import java.util.List;
import server.CarManage;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/4
 * description：
 */
public class MakeOrderController implements Initializable {
    final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
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
    private String name, tel, sid, addr;

    public MakeOrderController(Stage stage) {
        this.stage = stage;
        insuranceOpts = Arrays.asList("交强险", "第三者责任险", "车损险", "附加险");
        regCarOpts = Arrays.asList("是", "否");
        needValidate = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list_insurance.setItems(FXCollections.observableList(insuranceOpts));
        combo_regCar.setItems(FXCollections.observableList(regCarOpts));

        MFXScrollPane scrollPane = new MFXScrollPane();
        scrollPane.setMaxSize(450, 380);
        Parent view_confirmOrder = ViewLoader.loadView("fxml/ConfirmOrder.fxml", null);
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

        Map<String, Set<String>> bsMap = CarManage.getBSMap();
        List<String> brandlist = bsMap.keySet().stream().toList();
        combo_model.setItems(FXCollections.observableList(CarManage.getSeries().stream().toList()));
        combo_brand.setItems(FXCollections.observableList(brandlist));
        combo_brand.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> ov ,String old_value,String new_value)->{
                    combo_model.setItems(FXCollections.observableList(bsMap.get(new_value).stream().toList()));
                }
        );
        dialogContent.addActions(
                Map.entry(new MFXButton("Confirm"), event -> {
                    //TODO
                }),
                Map.entry(new MFXButton("Cancel"), event -> dialog.close())
        );

        btn_confirm.setOnMouseClicked(c -> {
            if (checkValid()) {
                name = text_name.getText();
                sid = text_sid.getText();
                tel = text_tel.getText();
                addr = text_addr.getText();
                System.out.println(name + addr + tel + sid);
                dialog.showDialog();
            }
        });

        initConstrains();
    }

    private void initConstrains() {
        needValidate.add(text_name);
        needValidate.add(text_addr);
        needValidate.add(text_sid);
        needValidate.add(text_tel);
        for (MFXTextField textField : needValidate) {
            setNotNullConstrain(textField);
            setListener(textField);
        }
    }

    private void setNotNullConstrain(MFXTextField node) {
        Constraint lengthConstraint = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("Password must be at least 8 characters long")//TODO
                .setCondition(node.textProperty().length().greaterThanOrEqualTo(8))
                .get();
        node.getValidator().constraint(lengthConstraint);
    }

    private void setListener(MFXTextField node) {
        node.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                node.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        });
        node.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {    //focus lost
                List<Constraint> constraints = node.validate();
                if (!constraints.isEmpty()) {
                    node.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                }
            }
        });
    }

    private boolean checkValid() {
        boolean res = true;
        for (MFXTextField node : needValidate) {
            if (!node.validate().isEmpty()) {
                node.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                res = false;
            }
        }
        return res;
    }
}

