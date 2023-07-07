package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import io.github.palexdev.mfxresources.fonts.MFXIconWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        node_icon.setIcon(new MFXFontIcon("fas-user", 80));
        btn_login.setOnMouseClicked(event -> {
            checkValid();
            enter.run();
        });
    }

    private void checkValid(){
        UserAccess.phoneNumberIsValid(text_phone.getText());
        int phone = Integer.parseInt(text_phone.getText());
        UserAccess.authenticate(phone,text_passwd.getText());
    }

}
