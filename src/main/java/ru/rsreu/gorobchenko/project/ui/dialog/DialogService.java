package ru.rsreu.gorobchenko.project.ui.dialog;

import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.DialogBox;
import com.almasb.fxgl.ui.MDIWindow;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayDeque;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DialogService extends com.almasb.fxgl.ui.DialogService {
    private static final DialogFactoryService dialogFactory = new DialogFactoryService();
    private SubScene dialogScene;
    @NotNull
    private final ArrayDeque states = new ArrayDeque();
    private MDIWindow window;

    @Override
    public void showMessageBox(String s) {

    }

    @Override
    public void showMessageBox(String s, Runnable runnable) {

    }

    @Override
    public void showConfirmationBox(String s, Consumer<Boolean> consumer) {

//        Pane dialog = dialogFactory.confirmationDialog(s, consumer);
//        this.show("Confirm", dialog);
    }

//    public final boolean isShowing() {
//        SubScene var10000 = this.dialogScene;
//        if (var10000 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("dialogScene");
//            var10000 = null;
//        }
//
//        return var10000.getContentRoot().getScene() != null;
//    }
//
//    private void show(String title, Pane content) {
//        if (this.isShowing()) {
//            ArrayDeque var10000 = this.states;
//            DialogData var10001 = new DialogData(title, content);
//            MDIWindow var10003 = this.window;
//            if (var10003 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("window");
//                var10003 = null;
//            }
//
//            String var4 = var10003.getTitle();
//            MDIWindow var10004 = this.window;
//            if (var10004 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("window");
//                var10004 = null;
//            }
//
//            var10001.<init> (var4, var10004.getContentPane());
//            var10000.push(var10001);
//        }
//
//        MDIWindow var3 = this.window;
//        if (var3 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("window");
//            var3 = null;
//        }
//
//        var3.setTitle(title);
//        var3 = this.window;
//        if (var3 == null) {
//            Intrinsics.throwUninitializedPropertyAccessException("window");
//            var3 = null;
//        }
//
//        var3.setContentPane(content);
//        this.show();
//    }
//
//    private final void show() {
//        if (!this.isShowing()) {
//            SceneService var10000 = this.sceneService;
//            if (var10000 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("sceneService");
//                var10000 = null;
//            }
//
//            SubScene var10001 = this.dialogScene;
//            if (var10001 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("dialogScene");
//                var10001 = null;
//            }
//
//            var10000.pushSubScene(var10001);
//            SubScene var1 = this.dialogScene;
//            if (var1 == null) {
//                Intrinsics.throwUninitializedPropertyAccessException("dialogScene");
//                var1 = null;
//            }
//
//            var1.getContentRoot().requestFocus();
//        }
//    }

    @Override
    public <T> void showChoiceBox(String s, Consumer<T> consumer, T t, T... ts) {

    }

    @Override
    public void showInputBox(String s, Consumer<String> consumer) {

    }

    @Override
    public void showInputBox(String s, Predicate<String> predicate, Consumer<String> consumer) {

    }

    @Override
    public void showInputBoxWithCancel(String s, Predicate<String> predicate, Consumer<String> consumer) {

    }

    @Override
    public void showErrorBox(Throwable throwable) {

    }

    @Override
    public void showErrorBox(String s, Runnable runnable) {

    }

    @Override
    public void showBox(String s, Node node, Button... buttons) {

    }

    @Override
    public DialogBox showProgressBox(String s) {
        return null;
    }

    @Override
    public void showProgressBox(String s, ReadOnlyDoubleProperty readOnlyDoubleProperty, Runnable runnable) {

    }

    private static final class DialogData {
        @NotNull
        private final String title;
        @NotNull
        private final Pane contentPane;

        public DialogData(@NotNull String title, @NotNull Pane contentPane) {
            Intrinsics.checkNotNullParameter(title, "title");
            Intrinsics.checkNotNullParameter(contentPane, "contentPane");
            this.title = title;
            this.contentPane = contentPane;
        }

        @NotNull
        public final String getTitle() {
            return this.title;
        }

        @NotNull
        public final Pane getContentPane() {
            return this.contentPane;
        }
    }
}
