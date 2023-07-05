package ui;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

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

    private final ToggleGroup toggleGroup;

    public CtrlApp(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
    }

    private ToggleButton createToggle(String icon, String text, double rotate) {
        MFXIconWrapper wrapper = new MFXIconWrapper(icon, 24, 32);
        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);
        if (rotate != 0) wrapper.getIcon().setRotate(rotate);
        return toggleNode;
    }

    private Parent loadView(String fxmlName, Callback<Class<?>, Object> controllerFactory){
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlName));
        loader.setControllerFactory(controllerFactory);
        try {
            return loader.load();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
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

        Parent view_makeOrder = loadView("fxml/MakeOrder.fxml", c -> new MakeOrderController());
        ToggleButton toggle_makeOrder = createToggle("mfx-angle-right", "签订订单", 0);
        toggle_makeOrder.setOnAction(event -> {
            contentPane.setContent(view_makeOrder);
            title.setText("订单信息");
        });
        navBar.getChildren().add(toggle_makeOrder);
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