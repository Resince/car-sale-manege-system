package ui;

import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.controllers.LoginController;
import ui.controllers.MakeOrderController;
import ui.controllers.OrderListController;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

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
    private final OrderListController preOrderListController;
    private final LoginController loginController;

    private final ToggleGroup toggleGroup;
    private ToggleButton homeToggle;

    public AppController(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
        makeOrderController = new MakeOrderController(stage);
        preOrderListController = new OrderListController();
        loginController = new LoginController(this::enter);
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

        /* SETUP menu BUT NOT SHOW*/
        addViewToMenu("fxml/MakeOrder.fxml", makeOrderController, "fas-pen-to-square", "签订订单", true);
        addViewToMenu("fxml/OrderList.fxml", preOrderListController, "fas-paste", "支付订单");


        /* START login */
        Parent view_login = AppUtil.loadView("fxml/Login.fxml", loginController);
        contentPane.setContent(view_login);

        /* WAITING loginController TO CALLBACK  enter */
    }

    public void enter() {
        /* LOGIN SUCCEED */
        contentPane.setContent(null);
        navBar.getChildren().setAll(toggleGroup.getToggles().stream().map(t -> (ToggleButton) t).toList());
        homeToggle.setSelected(true);
    }

    private void addViewToMenu(String fxmlRes, Object controller, String icon, String title, boolean setHome) {
        Parent view = AppUtil.loadView(fxmlRes, controller);
        ToggleButton toggle = createToggle(icon, title);
        toggle.setToggleGroup(toggleGroup);

        toggle.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!oldVal && newVal) {
                contentPane.setContent(view);
                ScrollUtils.addSmoothScrolling(contentPane);
                this.title.setText(title);
            }
        });

        if (setHome)
            homeToggle = toggle;
    }

    private void addViewToMenu(String fxmlRes, Object controller, String icon, String title) {
        addViewToMenu(fxmlRes, controller, icon, title, false);
    }

    private ToggleButton createToggle(String icon, String text) {
        MFXIconWrapper wrapper = new MFXIconWrapper(new MFXFontIcon(icon, 24), 32);
        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
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
