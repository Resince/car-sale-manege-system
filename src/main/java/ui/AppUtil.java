package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/6
 * description：
 */
public class AppUtil {
    public static Parent loadView(String fxmlName, Callback<Class<?>, Object> controllerFactory){
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlName));
        if(controllerFactory!=null)
            loader.setControllerFactory(controllerFactory);
        try {
            return loader.load();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
