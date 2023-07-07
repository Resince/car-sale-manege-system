/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/7
 * description：
 */
package ui.controllers;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import io.github.palexdev.mfxresources.fonts.MFXIconWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import ui.AppUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class PayOrderDetailController implements Initializable {
    @FXML
    private MFXIconWrapper btn_back;
    @FXML
    private MFXScrollPane pane_orderDetail;

    private final ConfirmOrderController confirmOrderController;
    private final Runnable back;

    public PayOrderDetailController(Runnable back){
        this.back=back;
        confirmOrderController=new ConfirmOrderController();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Parent orderDetail= AppUtil.loadView("fxml/ConfirmOrder.fxml",confirmOrderController);
        pane_orderDetail.setContent(orderDetail);
        btn_back.setIcon(new MFXFontIcon("fas-angle-left", 60));
        btn_back.setOnMouseClicked(event -> {
            back.run();
        });
    }
}
