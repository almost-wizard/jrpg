package ru.rsreu.gorobchenko.project.ui.dialog;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ru.rsreu.gorobchenko.project.ui.button.ButtonFactory;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class DialogFactoryService extends com.almasb.fxgl.ui.DialogFactoryService {
    @Override
    public Pane messageDialog(String s, Runnable runnable) {
        return null;
    }

    @Override
    public Pane messageDialog(String s) {
        return null;
    }

    @Override
    public Pane confirmationDialog(String s, Consumer<Boolean> consumer) {
        Text text = TextFactory.create(s, TextSize.M);

        Button btnYes = ButtonFactory.createMenuButton("Yes");
        btnYes.setOnAction(e -> consumer.accept(true));

        Button btnNo = ButtonFactory.createMenuButton("No");
        btnNo.setOnAction(e -> consumer.accept(false));

        Node[] var7 = new Node[]{btnYes, btnNo};
        HBox hbox = new HBox(var7);
        hbox.setAlignment(Pos.CENTER);
        hbox.setBackground(Background.fill(Color.TRANSPARENT));
        Node[] var8 = new Node[]{text, hbox};
        VBox vbox = new VBox(50.0, var8);
        vbox.setBackground(Background.fill(Color.TRANSPARENT));
        vbox.setAlignment(Pos.CENTER);
        return this.wrap(vbox);
    }

    private final Pane wrap(Node n) {
        StackPane wrapper = new StackPane(n);
        wrapper.setMinWidth(600.0);
        wrapper.setPadding(new Insets(20.0));
        return wrapper;
    }

    @Override
    public <T> Pane choiceDialog(String s, Consumer<T> consumer, T t, T... ts) {
        return null;
    }

    @Override
    public Pane inputDialog(String s, Consumer<String> consumer) {
        return null;
    }

    @Override
    public Pane inputDialog(String s, Predicate<String> predicate, Consumer<String> consumer) {
        return null;
    }

    @Override
    public Pane inputDialogWithCancel(String s, Predicate<String> predicate, Consumer<String> consumer) {
        return null;
    }

    @Override
    public Pane errorDialog(Throwable throwable, Runnable runnable) {
        return null;
    }

    @Override
    public Pane errorDialog(Throwable throwable) {
        return null;
    }

    @Override
    public Pane errorDialog(String s) {
        return null;
    }

    @Override
    public Pane errorDialog(String s, Runnable runnable) {
        return null;
    }

    @Override
    public Pane progressDialog(String s, ReadOnlyDoubleProperty readOnlyDoubleProperty, Runnable runnable) {
        return null;
    }

    @Override
    public Pane progressDialogIndeterminate(String s, Runnable runnable) {
        return null;
    }

    @Override
    public Pane customDialog(String s, Node node, Runnable runnable, Button... buttons) {
        return null;
    }
}
