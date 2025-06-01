package ru.rsreu.gorobchenko.project;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import ru.rsreu.gorobchenko.project.gameplay.Game;
import ru.rsreu.gorobchenko.project.ui.dialog.DialogFactoryService;
import ru.rsreu.gorobchenko.project.scenes.SceneFactory;

public class Runner extends GameApplication {
    private boolean isGameStarted;

    private final Game game = new Game();

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeightFromRatio((double) 16 / 9);
        settings.setPreserveResizeRatio(true);

        settings.setTitle("JRPG");
        settings.setVersion("1.0");
        settings.setDeveloperMenuEnabled(false);
        settings.setAppIcon("icon.png");
        settings.setDefaultCursor(new CursorInfo("weapons/sword.png", 0, 0));
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(!Config.DEBUG);
        settings.setIntroEnabled(false);
        if (!Config.DEBUG) {
            settings.setIntroEnabled(true);
            settings.setMainMenuEnabled(true);
        }
        settings.setGameMenuEnabled(true);

        settings.setSceneFactory(new SceneFactory());
        settings.setEngineServiceProvider(com.almasb.fxgl.ui.DialogFactoryService.class, DialogFactoryService.class);
//        settings.setEngineServiceProvider(com.almasb.fxgl.ui.DialogService.class, DialogService.class);
    }

    @Override
    protected void initGame() {
        try {
            Thread.sleep(Config.FadeTransition.MIN_DELAY_BEFORE_GAME_INIT);
        } catch (InterruptedException ignored) {

        }

        FXGL.set("isGameInitialized", false);
        game.initGame();
        FXGL.set("isGameInitialized", true);

        isGameStarted = false;

        try {
            Thread.sleep(Config.FadeTransition.DURATION);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void initInput() {
        game.initInput();
    }

    @Override
    protected void initUI() {
        game.initUI();
    }

    @Override
    protected void initPhysics() {
        game.initPhysics();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (!isGameStarted) {
            isGameStarted = true;
            try {
                Thread.sleep(Config.FadeTransition.MIN_DELAY_BETWEEN_LOADING_AND_GAME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        game.loop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
