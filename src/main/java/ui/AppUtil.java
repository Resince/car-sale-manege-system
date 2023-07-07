package ui;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;

import java.util.HashMap;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/6
 * description：
 */
public class AppUtil {
    public static Parent loadView(String fxmlName, Callback<Class<?>, Object> controllerFactory) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlName));
        if (controllerFactory != null)
            loader.setControllerFactory(controllerFactory);
        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    static Constraint.Builder constraintBuilder = Constraint.Builder.build().setSeverity(Severity.ERROR);

    public static void addConstraint(MFXTextField textField, ConstraintType type) {
        final String[] specialCharacters = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");
        BooleanExpression condition = switch (type) {
            case NotNull -> textField.textProperty().length().greaterThan(0);
            case IsPhoneNum -> Bindings.createBooleanBinding(
                    () -> textField.getText().matches("^1[3456789]\\\\d{9}$"),
                    textField.textProperty()
            );
            case AtLeast8 -> textField.textProperty().length().greaterThanOrEqualTo(8);
            case NoSpecialChar -> Bindings.createBooleanBinding(
                    () -> !containsAny(textField.getText(), "", specialCharacters),
                    textField.textProperty()
            );
            case MustSpecialChar -> Bindings.createBooleanBinding(
                    () -> containsAny(textField.getText(), "", specialCharacters),
                    textField.textProperty()
            );
        };
        textField.getValidator().constraint(constraintBuilder.setCondition(condition).get());
    }
}

enum ConstraintType {
    NotNull,
    IsPhoneNum,
    AtLeast8,
    NoSpecialChar,
    MustSpecialChar,
}