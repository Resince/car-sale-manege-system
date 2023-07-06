package ui;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import ui.controllers.ConfirmOrderController;
import ui.controllers.MakeOrderController;

public class CtrlApp implements Initializable {

    private final Stage stage;
    private double xOffset;
    private double yOffset;
    @FXML
    private MFXFontIcon closeIcon;
    @FXML
    private MFXFontIcon minimizeIcon;
    @FXML
    private VBox navBar;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private HBox windowHeader;
    @FXML
    private MFXScrollPane contentPane;
    @FXML
    private Label title;


    private final MakeOrderController makeOrderController;
    private final ConfirmOrderController confirmOrderController;

    private final ToggleGroup toggleGroup;

    public CtrlApp(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
        makeOrderController = new MakeOrderController(stage);
        confirmOrderController = new ConfirmOrderController();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        windowHeader.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        windowHeader.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        addViewToMenu("fxml/MakeOrder.fxml", makeOrderController, "fas-pen-to-square", "签订订单");
        addViewToMenu("fxml/ConfirmOrder.fxml", confirmOrderController, "fas-paste", "确认订单");
    }

    private void addViewToMenu(String fxmlRes, Object controller, String icon, String title) {
        Parent view = ViewLoader.loadView(fxmlRes, c -> controller);
        ToggleButton toggle = createToggle(icon, title);
        toggle.setOnAction(event -> {
            contentPane.setContent(view);
            ScrollUtils.addSmoothScrolling(contentPane);
            this.title.setText(title);
        });
        navBar.getChildren().add(toggle);
    }

    private ToggleButton createToggle(String icon, String text) {
        MFXIconWrapper wrapper = new MFXIconWrapper(new MFXFontIcon(icon, 24), 32);
        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);
        return toggleNode;
    }
}
/*
FONTAWESOME_BRANDS("FontAwesome/brands/FontAwesomeBrands.ttf", FontAwesomeBrands::toCode),
FONTAWESOME_REGULAR("FontAwesome/regular/FontAwesomeRegular.ttf", FontAwesomeRegular::toCode),
FONTAWESOME_SOLID("FontAwesome/solid/FontAwesomeSolid.ttf", FontAwesomeSolid::toCode),

&#xe988;//写订单
&#xe989;//查看订单
&#xe98a;//查看订单
&#xe900;//用户
&#xe901;//用户
 */
