package ui.controllers;

import entity.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import io.github.palexdev.mfxresources.fonts.MFXIconWrapper;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import server.UserAccess;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
public class LoginController implements Initializable {
    @FXML
    private MFXButton btn_login;
    @FXML
    private MFXIconWrapper node_icon;
    @FXML
    private MFXPasswordField text_passwd;
    @FXML
    private MFXTextField text_phone;

    Runnable enter;

    public LoginController(Runnable enter) {
        this.enter = enter;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            if (!text_phone.isFocused()) {
                text_phone.requestFocus();
            }
        });
        text_phone.setPromptText("请输入电话号码：");
        text_passwd.setPromptText("请输入密码：");
        node_icon.setIcon(new MFXFontIcon("fas-user", 80));

        /* DEBUG ONLY */
        //TODO:REMOVE
        node_icon.setOnMouseClicked(event -> {
            enter.run();
        });

        text_phone.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    text_passwd.requestFocus();
                }
            }
        });
        text_passwd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    if (checkValid()) {
                        enter.run();
                    }
                }
            }
        });
        btn_login.setOnMouseClicked(event -> {
            if (checkValid()) {
                enter.run();
            }
        });

    }

    private boolean checkValid() {
        User user = UserAccess.authenticate(text_phone.getText(), text_passwd.getText());
        if (user == null) {
            text_phone.setPromptText("请输入电话号码：");
            text_passwd.clear();
            text_passwd.setPromptText("密码错误");
            text_passwd.requestFocus();
            return false;
        } else if (user.getType().equals("InvalidUsername")) {
            text_passwd.setPromptText("请输入密码：");
            text_passwd.clear();
            text_phone.clear();
            text_phone.setPromptText("该电话号码不存在");
            text_phone.requestFocus();
            return false;
        } else if (user.getType().equals("Admin")) {
            return true;
        } else if (user.getType().equals("seller")) {
            return true;
        } else return user.getType().equals("manager");
    }

}
