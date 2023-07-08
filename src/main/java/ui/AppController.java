package ui;

import entity.Car;
import entity.Order;
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
import server.PurchaseCar;
import ui.controllers.*;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class AppController implements Initializable {
    @FXML
    private MFXFontIcon btn_back;
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

    private final Stage stage;
    private double xOffset;
    private double yOffset;
    private final ToggleGroup toggleGroup;
    private ToggleButton homeToggle;

    /* Controllers */
    private final MakeOrderController makeOrderController;
    private final OrderListController preOrderListController;
    private final PreOrderDetailController preOrderDetailController;
    private final OrderListController allOrderListController;
    private final AllOrderDetailController allOrderDetailController;
    private final LoginController loginController;
    private final CarManageController carManageController;
    private final CarDetailController carDetailController;


    /* pages */
    private Parent preOrderDetailPage;
    private Parent allOrderDetailPage;
    private Parent preOrderListPage;
    private Parent allOrderListPage;
    private Parent carManagePage;
    private Parent carDetailPage;

    public AppController(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
        loginController = new LoginController(this::enter);
        makeOrderController = new MakeOrderController(stage);
        preOrderDetailController = new PreOrderDetailController();
        allOrderDetailController = new AllOrderDetailController();
        preOrderListController = new OrderListController();
        allOrderListController = new OrderListController();
        carManageController = new CarManageController(stage);
        carDetailController = new CarDetailController();
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

        /* SETUP menu BUT NOT SHOW */
        addViewToMenu("fxml/MakeOrder.fxml", makeOrderController, "fas-pen-to-square", "签订订单", null, true);
        preOrderListPage = addViewToMenu("fxml/OrderList.fxml", preOrderListController, "fas-paste", "支付订单", this::showPreOrderListPage);
        allOrderListPage = addViewToMenu("fxml/OrderList.fxml", allOrderListController, "fas-paste", "查看订单", this::showAllOrderListPage);
        carManagePage = addViewToMenu("fxml/CarManage.fxml", carManageController, "fas-paste", "车辆管理", this::showCarManagePage);

        initDetailPages();

        /* START login */
        Parent view_login = AppUtil.loadView("fxml/Login.fxml", loginController);
        contentPane.setContent(view_login);

        /* WAITING loginController TO CALLBACK  enter */
    }

    private void initDetailPages() {
        preOrderDetailPage = AppUtil.loadView("fxml/PreOrderDetail.fxml", preOrderDetailController);
        preOrderListController.setAction(order -> {
            preOrderDetailController.setOrder(order);
            setSceneContent(preOrderDetailPage, "待支付的订单");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showPreOrderListPage());
        });
        preOrderListController.setSubtitle("选择想要支付的订单");

        allOrderDetailPage = AppUtil.loadView("fxml/AllOrderDetail.fxml", allOrderDetailController);
        allOrderListController.setAction(order -> {
            allOrderDetailController.setOrder(order);
            setSceneContent(allOrderDetailPage, "订单详情");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showAllOrderListPage());
        });
        allOrderListController.setSubtitle("选择需要查看的订单");

        carDetailPage = AppUtil.loadView("fxml/CarDetail.fxml", carDetailController);
        carManageController.setAddCarAction(() -> {
            carDetailController.addCarModeOn();
            setSceneContent(carDetailPage, "添加车辆");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showCarManagePage());
        });
        carManageController.setClickCarAction(car -> {
            carDetailController.modifyCarModeOn();
            carDetailController.setCar(car);
            setSceneContent(carDetailPage, "修改车辆信息");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showCarManagePage());
        });
    }

    private void showMakeOrderPage() {

    }

    private void showCarManagePage() {
        btn_back.setVisible(false);

        //TODO:查询车辆列表
        carManageController.setCars(Arrays.asList(
                new Car(1234, 12.2, "A", "油", "奥迪", "6", 4),
                new Car(2234, 16.2, "x", "电", "奔驰", "2", 42),
                new Car(3234, 14.2, "T", "混", "奔奔", "2", 0),
                new Car(4234, 222.2, "C", "增", "雪弗莱", "5", 1)
        ));

        setSceneContent(carManagePage, "车辆管理");
    }

    /**
     * 下面两个函数显示 支付订单 和 查看所有订单 的页面
     * 函数内需要实现获取对应订单列表
     */
    private void showPreOrderListPage() {
        btn_back.setVisible(false);
        List<Order> orderList = PurchaseCar.getUnpaidOrderList();
        preOrderListController.setOrders(orderList);

        setSceneContent(preOrderListPage, "支付订单");
    }

    private void showAllOrderListPage() {
        btn_back.setVisible(false);
        List<Order> orderList = PurchaseCar.getAllOrderList();
        allOrderListController.setOrders(orderList);

        setSceneContent(allOrderListPage, "所有订单");
    }

    public void enter() {
        /* LOGIN SUCCEED */
        contentPane.setContent(null);
        navBar.getChildren().setAll(toggleGroup.getToggles().stream().map(t -> (ToggleButton) t).toList());
        homeToggle.setSelected(true);
    }

    private Parent addViewToMenu(String fxmlRes, Object controller, String icon, String title, Runnable action, boolean setHome) {
        Parent view = AppUtil.loadView(fxmlRes, controller);
        ToggleButton toggle = createToggle(icon, title);
        toggle.setToggleGroup(toggleGroup);

        toggle.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!oldVal && newVal) {
                if (action != null)
                    action.run();
                setSceneContent(view, title);
            }
        });
        if (setHome)
            homeToggle = toggle;
        return view;
    }

    private Parent addViewToMenu(String fxmlRes, Object controller, String icon, String title, Runnable action) {
        return addViewToMenu(fxmlRes, controller, icon, title, action, false);
    }

    private void setSceneContent(Parent view, String title) {
        contentPane.setContent(view);
        ScrollUtils.addSmoothScrolling(contentPane);
        this.title.setText(title);
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