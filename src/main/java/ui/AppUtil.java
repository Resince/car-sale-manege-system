package ui;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.css.PseudoClass;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.util.List;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

/**
 * Created with IntelliJ IDEA.
 * author： Shuowei Hou
 * date： 2023/7/6
 * description：
 */
public class AppUtil {
    public enum ConstraintType {
        NotNull,
        IsPhoneNum,
        AtLeast8,
        NoSpecialChar,
        MustSpecialChar,
        IsSID,
        IsPositiveNumeric,
        IsNonNegativeInt,
    }

    public static Parent loadView(String fxmlName, Object controller) {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxmlName));
        System.out.println(App.class.getResource(fxmlName));
        if (controller != null)
            loader.setControllerFactory(c -> controller);
        try {
            return loader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void addConstraint(MFXTextField textField, ConstraintType type) {
        final String[] specialCharacters = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");
        String re_phoneNum = "^1[3456789]\\d{9}$";
        String re_SID = "^([1-6][1-9]|50)\\d{4}(18|19|20)\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
        String re_positiveNumeric = "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";
        String re_nonNegativeInt = "^\\d+$";

        BooleanExpression condition = switch (type) {
            case NotNull -> textField.textProperty().length().greaterThan(0);
            case IsPhoneNum -> Bindings.createBooleanBinding(
                    () -> textField.getText().matches(re_phoneNum),
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
            case IsSID -> Bindings.createBooleanBinding(
                    () -> textField.getText().matches(re_SID),
                    textField.textProperty()
            );
            case IsPositiveNumeric -> Bindings.createBooleanBinding(
                    () -> textField.getText().matches(re_positiveNumeric),
                    textField.textProperty()
            );
            case IsNonNegativeInt -> Bindings.createBooleanBinding(
                    () -> textField.getText().matches(re_nonNegativeInt),
                    textField.textProperty()
            );
        };

        String msg = switch (type) {
            case NotNull -> "不允许为空";
            case IsPhoneNum -> "非法电话号码";
            case AtLeast8 -> "至少8位";
            case NoSpecialChar -> "不允许特殊字符";
            case MustSpecialChar -> "必须包含特殊字符";
            case IsSID -> "非法身份证号";
            case IsPositiveNumeric -> "必须为正数";
            case IsNonNegativeInt -> "必须为非负整数";
        };

        textField.getValidator().constraint(Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage(msg)
                .setCondition(condition)
                .get()
        );
    }

    public static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");

    public static void setValidatorListener(MFXTextField node) {
        node.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                node.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        });
        node.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {    //focus lost
                List<Constraint> constraints = node.validate();
                if (!constraints.isEmpty()) {
                    node.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                }
            }
        });
    }

    public static boolean checkValid(List<MFXTextField> needValidate) {
        boolean res = true;
        for (MFXTextField node : needValidate) {
            if (!node.validate().isEmpty()) {
                System.out.println("[invalid]" + node.validate().get(0).getMessage());
                node.pseudoClassStateChanged(AppUtil.INVALID_PSEUDO_CLASS, true);
                res = false;
            }
        }
        return res;
    }
}
