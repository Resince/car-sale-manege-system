package ui;

import entity.Car;
import entity.Order;
import entity.User;
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
import server.CarManage;
import server.PurchaseCar;
import server.UserManage;
import ui.controllers.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

enum MenuPage{
    MakeOrder,
    PayOrder,
    AllOrder,
    CarManage,
    UserManage
}

public class AppController implements Initializable {
    @FXML
    private MFXFontIcon btn_back;
    @FXML
    private MFXFontIcon btn_quit;
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
    private final HashMap<MenuPage,ToggleButton> pageToggle;
    private final HashMap<MenuPage,String> pageName;
    private User curUser;

    /* Controllers */
    private final MakeOrderController makeOrderController;
    private final OrderListController preOrderListController;
    private final PreOrderDetailController preOrderDetailController;
    private final OrderListController allOrderListController;
    private final AllOrderDetailController allOrderDetailController;
    private final LoginController loginController;
    private final CarManageController carManageController;
    private final CarDetailController carDetailController;
    private final UserManageController userManageController;
    private final UserDetailController userDetailController;


    /* pages */
    private Parent makeOrderPage;
    private Parent preOrderDetailPage;
    private Parent allOrderDetailPage;
    private Parent preOrderListPage;
    private Parent allOrderListPage;
    private Parent carManagePage;
    private Parent carDetailPage;
    private Parent userManagePage;
    private Parent userDetailPage;
    private Parent loginPage;


    public AppController(Stage stage) {
        pageName=new HashMap<>();
        pageName.put(MenuPage.MakeOrder,"签订订单");
        pageName.put(MenuPage.PayOrder,"支付订单");
        pageName.put(MenuPage.AllOrder,"所有订单");
        pageName.put(MenuPage.CarManage,"车辆管理");
        pageName.put(MenuPage.UserManage,"用户管理");
        pageToggle=new HashMap<>();

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
        userManageController = new UserManageController();
        userDetailController = new UserDetailController();
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
        makeOrderPage = addViewToMenu("fxml/MakeOrder.fxml", makeOrderController, "fas-pen-to-square", MenuPage.MakeOrder, this::showMakeOrderPage);
        preOrderListPage = addViewToMenu("fxml/OrderList.fxml", preOrderListController, "fas-paste", MenuPage.PayOrder, this::showPreOrderListPage);
        allOrderListPage = addViewToMenu("fxml/OrderList.fxml", allOrderListController, "fas-paste", MenuPage.AllOrder, this::showAllOrderListPage);
        carManagePage = addViewToMenu("fxml/CarManage.fxml", carManageController, "fas-paste", MenuPage.CarManage, this::showCarManagePage);
        userManagePage = addViewToMenu("fxml/UserManage.fxml", userManageController, "fas-user", MenuPage.UserManage, this::showUserMangePage);

        initDetailPages();

        loginPage=AppUtil.loadView("fxml/Login.fxml", loginController);

        btn_quit.setOnMouseClicked(event -> startLogin());

        startLogin();

    }

    private void startLogin(){
        /* START login */
        btn_quit.setVisible(false);
        navBar.getChildren().clear();
        contentPane.setContent(loginPage);
        /* WAITING loginController TO CALLBACK  enter */
    }

    public void enter(User user) {
        /* LOGIN SUCCEED */
        contentPane.setContent(null);
        toggleGroup.getToggles().clear();
        if(user.getType().equals("seller") || user.getType().equals("admin")){
            toggleGroup.getToggles().add(pageToggle.get(MenuPage.MakeOrder));
            toggleGroup.getToggles().add(pageToggle.get(MenuPage.PayOrder));
            toggleGroup.getToggles().add(pageToggle.get(MenuPage.AllOrder));
            makeOrderController.setCurUser(user);
        }
        if (user.getType().equals("manager") || user.getType().equals("admin")) {
            toggleGroup.getToggles().add(pageToggle.get(MenuPage.CarManage));
        }
        if(user.getType().equals("admin")){
            toggleGroup.getToggles().add(pageToggle.get(MenuPage.UserManage));
        }
        navBar.getChildren().setAll(toggleGroup.getToggles().stream().map(t -> (ToggleButton) t).toList());
        toggleGroup.getToggles().get(0).setSelected(true);
        btn_quit.setVisible(true);
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
        preOrderDetailController.setCloseAction(this::showPreOrderListPage);

        allOrderDetailPage = AppUtil.loadView("fxml/AllOrderDetail.fxml", allOrderDetailController);
        allOrderListController.setAction(order -> {
            allOrderDetailController.setOrder(order);
            setSceneContent(allOrderDetailPage, "订单详情");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showAllOrderListPage());
        });
        allOrderListController.setSubtitle("选择需要查看的订单");
        allOrderDetailController.setCloseAction(this::showAllOrderListPage);

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
        carDetailController.setCloseAction(this::showCarManagePage);

        userDetailPage = AppUtil.loadView("fxml/UserDetail.fxml", userDetailController);
        userManageController.setAddUserAction(() -> {
            userDetailController.addUserModeOn();
            setSceneContent(userDetailPage, "新用户注册");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showUserMangePage());
        });
        userManageController.setClickUserAction(user -> {
            userDetailController.modifyUserModeOn();
            userDetailController.setUser(user);
            setSceneContent(userDetailPage, "修改用户信息");
            btn_back.setVisible(true);
            btn_back.setOnMouseClicked(event -> showUserMangePage());
        });
        userDetailController.setCloseAction(this::showUserMangePage);
    }

    private void showUserMangePage() {
        btn_back.setVisible(false);
        List<User> userList = UserManage.searchAllUserList();
        userManageController.setUsers(userList);
        setSceneContent(userManagePage, "用户管理");
    }

    private void showMakeOrderPage() {
        setSceneContent(makeOrderPage,"填写订单");
    }

    private void showCarManagePage() {
        btn_back.setVisible(false);
        System.out.println("[::showCarManagePage]reload");
        List<Car> carList = CarManage.searchAllCarList();
        carManageController.setCars(carList);
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

    private Parent addViewToMenu(String fxmlRes, Object controller, String icon, MenuPage p, Runnable action) {
        Parent view = AppUtil.loadView(fxmlRes, controller);
        ToggleButton toggle = createToggle(icon, pageName.get(p));
        pageToggle.put(p,toggle);
        toggle.selectedProperty().addListener((observableValue, oldVal, newVal) -> {
            if (!oldVal && newVal) {
                action.run();
            }
        });
        return view;
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
