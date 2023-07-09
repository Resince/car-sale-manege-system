/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/8
 * description：
 */
package ui.controllers;

import entity.Car;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXListCell;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.DoubleFilter;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import server.CarManage;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import org.apache.commons.lang3.tuple.Pair;

public class CarManageController implements Initializable {
    @FXML
    private MFXButton btn_addCar;
    @FXML
    private MFXButton btn_batchImport;
    @FXML

    private MFXTableView<Car> list_cars;
    private final FileChooser fileChooser;

    Stage stage;

    public CarManageController(Stage stage) {
        this.stage = stage;
        fileChooser = new FileChooser();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_batchImport.setOnMouseClicked(event -> {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                System.out.println(file.getAbsolutePath());
                CarManage.addCarList(file.getAbsolutePath());
            }
        });

        List<Pair<String, Function<Car, ? extends Comparable>>> metaColumn=new ArrayList<>();
        metaColumn.add(Pair.of("序号", Car::getCarId));
        metaColumn.add(Pair.of("品牌", Car::getBrand));
        metaColumn.add(Pair.of("系列", Car::getSeries));
        metaColumn.add(Pair.of("级别", Car::getType));
        metaColumn.add(Pair.of("能源", Car::getPowerType));
        metaColumn.add(Pair.of("价格", Car::getPrice));
        metaColumn.add(Pair.of("库存量", Car::getNumber));

        List<MFXTableColumn<Car>> columns = new ArrayList<>();
        for (Pair<String, Function<Car, ? extends Comparable>> p : metaColumn) {
            MFXTableColumn<Car> column = new MFXTableColumn<>(p.getLeft(), false, Comparator.comparing(p.getRight()));
            column.setRowCellFactory(car -> new MFXTableRowCell<>(p.getRight()));
            column.setPrefWidth(80);
            column.setMaxWidth(80);
            column.setMinWidth(80);
            columns.add(column);
        }

        list_cars.getTableColumns().setAll(FXCollections.observableList(columns));

        list_cars.getFilters().add(new IntegerFilter<>("序号", Car::getCarId));
        list_cars.getFilters().add(new StringFilter<>("品牌", Car::getBrand));
        list_cars.getFilters().add(new StringFilter<>("系列", Car::getSeries));
        list_cars.getFilters().add(new StringFilter<>("级别", Car::getType));
        list_cars.getFilters().add(new StringFilter<>("能源", Car::getPowerType));
        list_cars.getFilters().add(new DoubleFilter<>("价格", Car::getPrice));
        list_cars.getFilters().add(new IntegerFilter<>("库存量", Car::getNumber));
    }

    public void setAddCarAction(Runnable action) {
        btn_addCar.setOnMouseClicked(event -> action.run());
    }

    public void setClickCarAction(Consumer<Car> action) {
        list_cars.getSelectionModel().selectionProperty().addListener((observableValue, integerObservableMap, t1) -> {
            if (!t1.isEmpty()) {
                if (t1.size() == 1)
                    action.accept(t1.values().stream().toList().get(0));
                list_cars.getSelectionModel().clearSelection();
            }
        });
    }

    public void setCars(List<Car> carList) {
        list_cars.setItems(FXCollections.observableList(carList));
    }


    /* UNUSED */
    private static class PersonCellFactory extends MFXListCell<Car> {
        public PersonCellFactory(MFXListView<Car> listView, Car car) {
            super(listView, car);
        }

        @Override
        protected void render(Car car) {
            Label[] labels = new Label[6];
            for (Label label : labels) {
                label.setPrefSize(30, 80);
                label.setAlignment(Pos.CENTER);
            }
            labels[0].setText(car.getCarId().toString());
            labels[1].setText(car.getBrand());
            labels[2].setText(car.getType());
            labels[3].setText(car.getSeries());
            labels[4].setText(car.getPrice().toString());
            labels[5].setText(car.getNumber().toString());
            HBox cell = new HBox(labels);
            cell.setPrefSize(30, 580);
            cell.setMinSize(-Double.MAX_VALUE, -Double.MAX_VALUE);
            cell.setMaxSize(-Double.MAX_VALUE, -Double.MAX_VALUE);
            super.render(car);
            getChildren().setAll(cell);
        }
    }
}


