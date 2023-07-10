package ui;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/4
 * description：
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception{
        CSSFX.start();

        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxml/App.fxml"));
        loader.setControllerFactory(c->new AppController(stage));
        Scene scene = new Scene(loader.load());
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("汽车销售管理系统");
        System.out.println(App.class.getResource("AMSS_icon.png").getPath());
        stage.getIcons().setAll(new Image(App.class.getResource("AMSS_icon.png").getPath().substring(1)));
        stage.setScene(scene);
        stage.show();
    }
}
