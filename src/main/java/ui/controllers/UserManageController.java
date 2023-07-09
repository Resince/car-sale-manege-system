package ui.controllers;

import entity.Car;
import entity.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;
import server.CarManage;
import server.UserManage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserManageController implements Initializable {
    @FXML
    private MFXButton btn_addUser;
    @FXML
    private MFXButton btn_batchImport;
    @FXML
    private MFXTableView<User> list_users;

    private final Stage stage;
    private final FileChooser fileChooser;

    public UserManageController(Stage stage) {
        this.stage = stage;
        fileChooser=new FileChooser();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_batchImport.setOnMouseClicked(event -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                //TODO
                System.out.println(file.getAbsolutePath());
            }
        });

        List<Pair<String, Function<User, ? extends Comparable>>> metaColumn=new ArrayList<>();
        metaColumn.add(Pair.of("工号", User::getUserId));
        metaColumn.add(Pair.of("姓名", User::getName));
        metaColumn.add(Pair.of("用户组", User::getType));
        metaColumn.add(Pair.of("电话号", User::getPhoneNumber));
        metaColumn.add(Pair.of("密码", User::getPassword));

        final double cWidth=570.0/metaColumn.size();
        List<MFXTableColumn<User>> columns = new ArrayList<>();
        for (Pair<String, Function<User, ? extends Comparable>> p : metaColumn) {
            MFXTableColumn<User> column = new MFXTableColumn<>(p.getLeft(), false, Comparator.comparing(p.getRight()));
            column.setRowCellFactory(car -> new MFXTableRowCell<>(p.getRight()));
            column.setPrefWidth(cWidth);
            column.setMaxWidth(cWidth);
            column.setMinWidth(cWidth);
            columns.add(column);
        }

        list_users.getTableColumns().setAll(FXCollections.observableList(columns));

        list_users.getFilters().add(new IntegerFilter<>("工号", User::getUserId));
        list_users.getFilters().add(new StringFilter<>("姓名", User::getName));
        list_users.getFilters().add(new StringFilter<>("用户组", User::getType));
        list_users.getFilters().add(new StringFilter<>("电话号", User::getPhoneNumber));
        list_users.getFilters().add(new StringFilter<>("密码", User::getPassword));
    }

    public void setUsers(List<User> userList) {
        list_users.setItems(FXCollections.observableList(userList));
    }

    public void setAddUserAction(Runnable action) {
        btn_addUser.setOnMouseClicked(event -> action.run());
    }

    public void setClickUserAction(Consumer<User> action) {
        list_users.getSelectionModel().selectionProperty().addListener((observableValue, integerObservableMap, t1) -> {
            if (!t1.isEmpty()) {
                if (t1.size() == 1)
                    action.accept(t1.values().stream().toList().get(0));
                list_users.getSelectionModel().clearSelection();
            }
        });
    }
}
