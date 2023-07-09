package ui.controllers;

import entity.Car;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import server.CarManage;
import ui.App;
import ui.AppUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class CarDetailController implements Initializable {
    @FXML
    private MFXButton btn_confirm;
    @FXML
    private Label subtitle;
    @FXML
    private MFXTextField text_brand;
    @FXML
    private MFXTextField text_carId;
    @FXML
    private MFXTextField text_count;
    @FXML
    private MFXTextField text_level;
    @FXML
    private MFXTextField text_powerType;
    @FXML
    private MFXTextField text_price;
    @FXML
    private MFXTextField text_series;

    private List<MFXTextField> needValidate;
    private Runnable closeAction;

    public CarDetailController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initConstrains();
    }

    private void initConstrains() {
        needValidate = Arrays.asList(text_brand, text_carId, text_count,
                text_level, text_powerType, text_price, text_series);
        for (MFXTextField textField : needValidate) {
            textField.getStyleClass().add("validated-field");
            AppUtil.addConstraint(textField, AppUtil.ConstraintType.NotNull);
            AppUtil.setValidatorListener(textField);
        }
        AppUtil.addConstraint(text_count, AppUtil.ConstraintType.IsNonNegativeInt);
        AppUtil.addConstraint(text_price, AppUtil.ConstraintType.IsPositiveNumeric);
    }

    public void setCar(Car car) {
        text_brand.setText(car.getBrand());
        text_carId.setText(car.getCarId().toString());
        text_count.setText(car.getNumber().toString());
        text_level.setText(car.getSeries());
        text_powerType.setText(car.getPowerType());
        text_price.setText(car.getPrice().toString());
        text_series.setText(car.getType());
    }

    public void clear() {
        text_brand.clear();
        text_brand.clear();
        text_carId.clear();
        text_count.clear();
        text_level.clear();
        text_powerType.clear();
        text_price.clear();
        text_series.clear();
    }

    public void addCarModeOn() {
        clear();
        text_carId.setText("自动分配");
        subtitle.setText("填写车辆信息");
        text_carId.setVisible(false);
        btn_confirm.setText("确认添加");
        btn_confirm.setOnMouseClicked(event -> {
            if (AppUtil.checkValid(needValidate)) {
                confirmAddCar();
                closeAction.run();
            }
        });
    }

    public void modifyCarModeOn() {
        subtitle.setText("修改车辆信息");
        text_carId.setVisible(true);
        btn_confirm.setText("确认修改");
        btn_confirm.setOnMouseClicked(event -> {
            System.out.println("[CarDetailController::btn_confirm]clicked");
            if (AppUtil.checkValid(needValidate)) {
                confirmModifyCar();
                closeAction.run();
            }
        });

    }

    private void confirmAddCar() {
        Car car = new Car()
                .setBrand(text_brand.getText())
                .setSeries(text_series.getText())
                .setPowerType(text_powerType.getText())
                .setPrice(Double.valueOf(text_price.getText()))
                .setNumber(Integer.valueOf(text_count.getText()))
                .setType(text_level.getText());
        CarManage.addCar(car);
    }

    private void confirmModifyCar() {
        System.out.println("[CarDetailController::confirmModifyCar]begin");
        Car car = new Car()
                .setBrand(text_brand.getText())
                .setSeries(text_series.getText())
                .setPowerType(text_powerType.getText())
                .setPrice(Double.valueOf(text_price.getText()))
                .setNumber(Integer.valueOf(text_count.getText()))
                .setType(text_level.getText())
                .setCarId(Integer.valueOf(text_carId.getText()));
        CarManage.updateCar(car);
        System.out.println("[CarDetailController::confirmModifyCar]end");
    }

    public void setCloseAction(Runnable closeAction) {
        this.closeAction = closeAction;
    }
}
