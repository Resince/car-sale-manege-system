package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailController implements Initializable {
    @FXML
    private MFXButton btn_confirm;
    @FXML
    private MFXRadioButton rbtn_admin;
    @FXML
    private MFXRadioButton rbtn_carAdmin;
    @FXML
    private MFXRadioButton rbtn_seller;
    @FXML
    private Label subtitle;
    @FXML
    private MFXPasswordField text_confirmPasswd;
    @FXML
    private MFXTextField text_name;
    @FXML
    private MFXPasswordField text_passwd;
    @FXML
    private MFXTextField text_tel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
