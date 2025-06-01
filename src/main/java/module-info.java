open module ru.rsreu.gorobchenko.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires org.jetbrains.annotations;

//    opens ru.rsreu.gorobchenko.project to javafx.fxml;
    exports ru.rsreu.gorobchenko.project;
    exports ru.rsreu.gorobchenko.project.factory;
    exports ru.rsreu.gorobchenko.project.gameplay;
    exports ru.rsreu.gorobchenko.project.gameplay.daytime;
}