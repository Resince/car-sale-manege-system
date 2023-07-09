package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class UserManageController implements Initializable {
    @FXML
    private MFXButton btn_addUser;
    @FXML
    private MFXButton btn_batchImport;
    @FXML
    private MFXTableView<?> list_users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
