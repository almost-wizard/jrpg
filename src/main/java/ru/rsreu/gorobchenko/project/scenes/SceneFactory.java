package ru.rsreu.gorobchenko.project.scenes;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.StartupScene;
import org.jetbrains.annotations.NotNull;

public class SceneFactory extends com.almasb.fxgl.app.scene.SceneFactory {
    @NotNull
    @Override
    public FXGLMenu newMainMenu() {
        return new MainMenu();
    }

    @NotNull
    @Override
    public IntroScene newIntro() {
        return new Intro();
    }

    @NotNull
    @Override
    public LoadingScene newLoadingScene() {
        return new Loading();
    }

    @NotNull
    @Override
    public StartupScene newStartup(int width, int height) {
        return new EmptyStartup(width, height);
    }
}
