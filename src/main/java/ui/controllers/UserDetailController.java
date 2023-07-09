package ui.controllers;

import entity.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import server.UserManage;
import ui.AppUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
    @FXML
    private MFXButton btn_confirm;
    @FXML
    private MFXRadioButton rbtn_admin;
    @FXML
    private MFXRadioButton rbtn_manager;
    @FXML
    private MFXRadioButton rbtn_seller;
    @FXML
    private Label subtitle;
    @FXML
    private MFXPasswordField text_passwd_c;
    @FXML
    private MFXTextField text_name;
    @FXML
    private MFXPasswordField text_passwd;
    @FXML
    private MFXTextField text_tel;
    @FXML
    private MFXTextField text_passwd_a;
    @FXML
    private VBox box_userGroup;
    @FXML
    private MFXButton btn_deleteUser;

    private final ToggleGroup toggleGroup;
    private List<MFXTextField> needValidate;
    private Runnable closeAction;
    private Integer curUserId;
    private final String samplePasswd = "12345678Aa*";

    public UserDetailController() {
        toggleGroup = new ToggleGroup();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toggleGroup.getToggles().setAll(rbtn_admin, rbtn_manager, rbtn_seller);
        needValidate = Arrays.asList(text_passwd_c, text_name, text_passwd, text_tel, text_passwd_a);
        for (MFXTextField textField : needValidate) {
            textField.getStyleClass().add("validated-field");
            AppUtil.addConstraint(textField, AppUtil.ConstraintType.NotNull);
            AppUtil.setValidatorListener(textField);
        }
        AppUtil.addConstraint(text_passwd_a, AppUtil.ConstraintType.AtLeast8);
        AppUtil.addConstraint(text_passwd, AppUtil.ConstraintType.AtLeast8);
        AppUtil.addConstraint(text_passwd_c, AppUtil.ConstraintType.AtLeast8);
        AppUtil.addConstraint(text_tel, AppUtil.ConstraintType.IsPhoneNum);
        text_passwd_c.getValidator().constraint(Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("两次密码输入不一致")
                .setCondition(Bindings.createBooleanBinding(
                        () -> text_passwd_c.getText().equals(text_passwd.getText()),
                        text_passwd_c.textProperty()))
                .get()
        );

        btn_deleteUser.setOnMouseClicked(event -> {
            UserManage.deleteUserById(curUserId);
            closeAction.run();
        });
    }

    private void clear() {
        toggleGroup.selectToggle(null);
        text_passwd_c.clear();
        text_name.clear();
        text_passwd.clear();
        text_tel.clear();
        text_passwd_a.clear();
    }

    public void addUserModeOn() {
        clear();
        subtitle.setText("填写用户信息");
        btn_deleteUser.setVisible(false);
        text_passwd.setVisible(true);
        text_passwd_c.setVisible(true);
        text_passwd_a.setVisible(false);
        text_passwd_a.setText(samplePasswd);
        btn_confirm.setText("确认注册");
        btn_confirm.setOnMouseClicked(event -> {
            if (AppUtil.checkValid(needValidate) & getSelectedUserGroup() != null) {
                confirmAddUser();
                closeAction.run();
            }
        });
    }

    private void confirmAddUser() {
        User user = new User().setName(text_name.getText())
                .setType(getSelectedUserGroup())
                .setPhoneNumber(text_tel.getText())
                .setPassword(text_passwd_c.getText());
        System.out.println("[UserDetailController::confirmAddUser]" + user.toString());
        UserManage.addUser(user);
    }

    public void modifyUserModeOn() {
        subtitle.setText("修改用户信息");
        btn_deleteUser.setVisible(true);
        text_passwd_a.setVisible(true);
        text_passwd.setVisible(false);
        text_passwd_c.setVisible(false);
        btn_confirm.setText("确认修改");
        btn_confirm.setOnMouseClicked(event -> {
            System.out.println("[CarDetailController::btn_confirm]clicked");
            if (AppUtil.checkValid(needValidate) & getSelectedUserGroup() != null) {
                confirmModifyUser();
                closeAction.run();
            }
        });
    }

    private void confirmModifyUser() {
        User user = new User().setName(text_name.getText())
                .setType(getSelectedUserGroup())
                .setPhoneNumber(text_tel.getText())
                .setPassword(text_passwd_a.getText())
                .setUserId(curUserId);
        UserManage.updateUser(user);
    }

    public void setCloseAction(Runnable closeAction) {
        this.closeAction = closeAction;
    }

    public void setUser(User user) {
        text_passwd.setText(samplePasswd);
        text_passwd_c.setText(samplePasswd);
        curUserId = user.getUserId();
        text_name.setText(user.getName());
        text_tel.setText(user.getPhoneNumber());
        text_passwd_a.setText(user.getPassword());
        toggleGroup.selectToggle(switch (user.getType()) {
            case "seller" -> rbtn_seller;
            case "manager" -> rbtn_manager;
            case "admin" -> rbtn_admin;
            default -> null;
        });
    }

    private String getSelectedUserGroup() {
        MFXRadioButton s_btn = (MFXRadioButton) toggleGroup.getSelectedToggle();
        if (s_btn == null) {
            System.out.println("[UserDetailController::getSelectedUserGroup]null");
            box_userGroup.pseudoClassStateChanged(AppUtil.INVALID_PSEUDO_CLASS, true);
            return null;
        }
        System.out.println("[UserDetailController::getSelectedUserGroup]not null");
        box_userGroup.pseudoClassStateChanged(AppUtil.INVALID_PSEUDO_CLASS, false);
        if (s_btn.equals(rbtn_seller))
            return "seller";
        if (s_btn.equals(rbtn_manager))
            return "manager";
        if (s_btn.equals(rbtn_admin))
            return "admin";
        return null;
    }
}
