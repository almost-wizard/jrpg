package ru.rsreu.gorobchenko.project.gameplay;

import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AttackOwner;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyType;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.Cell;
import ru.rsreu.gorobchenko.project.entity.inventory.InventoryManager;
import ru.rsreu.gorobchenko.project.entity.inventory.type.BaseInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.MinableInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.PlayerInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.trail.AttackTrailCollisionHandler;
import ru.rsreu.gorobchenko.project.factory.EntitySpawner;
import ru.rsreu.gorobchenko.project.factory.EntityType;
import ru.rsreu.gorobchenko.project.factory.GameFactory;
import ru.rsreu.gorobchenko.project.gameplay.daytime.DayTimeCycleManager;
import ru.rsreu.gorobchenko.project.gameplay.daytime.DayTimeManager;
import ru.rsreu.gorobchenko.project.gameplay.daytime.DayTimeType;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlayer;
import ru.rsreu.gorobchenko.project.shared.music.MusicPlaylist;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

import java.util.List;


public class Game {
    private Pane helpTextPane;
    int iterationsCount = 0;

    public void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new GameFactory());
        FXGL.setLevelFromMap("jrpg-map.tmx");

        PlayerManager.getInstance().reset();

        EntitySpawner.resetNecessaryEnemiesCount();

        bindViewportToEntity(PlayerManager.getInstance().getPlayer());

        initializeDayTimeManager();
        DayTimeCycleManager.getInstance().startCycle();

        MusicPlayer.getInstance().setPlaylist(MusicPlaylist.GAME);
    }

    public void initInput() {
        FXGL.getInput().addTriggerListener(new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                switch (keyTrigger.getKey()) {
                    // Переключение между инвентарями
                    case KeyCode.Q -> {
                        InventoryManager manager = InventoryManager.getInstance();

                        List<BaseInventoryComponent> activeInventories = manager.getActiveInventories();

                        if (activeInventories.size() == 1) {
                            activeInventories.getFirst().focus();
                            return;
                        }

                        if (activeInventories.size() != 2) {
                            return;
                        }

                        BaseInventoryComponent first = activeInventories.get(0);
                        BaseInventoryComponent second = activeInventories.get(1);
                        boolean isFirstFocused = first.isFocused();
                        boolean isSecondFocused = second.isFocused();

                        if (!isFirstFocused && !isSecondFocused) {
                            first.focus();
                            return;
                        }

                        if (isFirstFocused) {
                            second.focus();
                        } else {
                            first.focus();
                        }
                    }
                    // Закрытие инвентарей при нажатии на escape
                    case KeyCode.ESCAPE -> {
                        List<BaseInventoryComponent> inventories = InventoryManager.getInstance().getActiveInventories();
                        for (BaseInventoryComponent component : inventories) {
                            component.close();
                        }
                    }
                    // Открытие инвентаря
                    case KeyCode.O -> {
                        List<MinableInventoryComponent> inventories = InventoryManager.getInstance().getMinableInventories();
                        Point2D playerPos = PlayerManager.getInstance().getPlayer().getAnchoredPosition();
                        for (MinableInventoryComponent component : inventories) {
                            if (component.getEntity().getAnchoredPosition().distance(playerPos) <= MinableInventoryComponent.INTERACTION_DISTANCE) {
                                component.toggle();
                                component.focus();
                                PlayerInventoryComponent playerInventory = PlayerManager.getInstance().getInventory();
                                playerInventory.setActive(component.isActive());
                                if (component.isTradable()) {
                                    playerInventory.setGoodsCostEnabled(component.isActive());
                                }
                                break;
                            }
                        }
                    }
                    // Выбор предмета для дальнейшего перемещения в инвентаре
                    case KeyCode.E -> {
                        List<BaseInventoryComponent> inventories = InventoryManager.getInstance().getActiveInventories();
                        for (BaseInventoryComponent component : inventories) {
                            List<Cell> cells = component.getModel().getCells();
                            for (Cell cell : cells) {
                                if (cell.isFocusWithin()) {
                                    cell.processClick();
                                    return;
                                }
                            }
                        }
                    }
                    // Переместить все предметы данного типа в следующий инвентарь
                    case KeyCode.R -> {
                        List<BaseInventoryComponent> inventories = InventoryManager.getInstance().getActiveInventories();
                        for (BaseInventoryComponent component : inventories) {
                            List<Cell> cells = component.getModel().getCells();
                            for (Cell cell : cells) {
                                if (cell.isFocusWithin()) {
                                    cell.processReplace(true);
                                    return;
                                }
                            }
                        }
                    }
                    // Переместить 1 предмет данного типа в следующий инвентарь
                    case KeyCode.G -> {
                        List<BaseInventoryComponent> inventories = InventoryManager.getInstance().getActiveInventories();
                        for (BaseInventoryComponent component : inventories) {
                            List<Cell> cells = component.getModel().getCells();
                            for (Cell cell : cells) {
                                if (cell.isFocusWithin()) {
                                    cell.processReplace(false);
                                    return;
                                }
                            }
                        }
                    }
                    // Применить предмет
                    case KeyCode.F -> {
                        List<BaseInventoryComponent> inventories = InventoryManager.getInstance().getActiveInventories();
                        for (BaseInventoryComponent component : inventories) {
                            List<Cell> cells = component.getModel().getCells();
                            for (Cell cell : cells) {
                                if (cell.isFocusWithin()) {
                                    cell.processApply();
                                    return;
                                }
                            }
                        }
                    }
                    // Осмотреть предмет
                    case KeyCode.V -> {
                        List<BaseInventoryComponent> inventories = InventoryManager.getInstance().getActiveInventories();
                        for (BaseInventoryComponent component : inventories) {
                            List<Cell> cells = component.getModel().getCells();
                            for (Cell cell : cells) {
                                if (cell.isFocusWithin()) {
                                    cell.processCloseView();
                                    return;
                                }
                            }
                        }
                    }
                    case KeyCode.H -> helpTextPane.setVisible(!helpTextPane.isVisible());
                }

                if (!Config.DEBUG) {
                    return;
                }

                switch (keyTrigger.getKey()) {
                    case KeyCode.DIGIT8 -> PlayerManager.getInstance().getPlayerComponent().die();
                    case KeyCode.DIGIT9 -> MusicPlayer.getInstance().nextInPlaylist();
                    case KeyCode.DIGIT0 -> EntityUtils.removeFromWorld(EntityType.RM_COLLIDER_TO_ARONDIT);
                    case KeyCode.F4 -> EntityUtils.killAllEnemies();
                    case KeyCode.DIGIT1 ->
                            EntitySpawner.spawnEnemy(EnemyType.BARGEST, new Point2D(900, 250), new Rectangle());
                    case KeyCode.DIGIT2 ->
                            EntitySpawner.spawnEnemy(EnemyType.WEREWOLF, new Point2D(900, 250), new Rectangle());
                    case KeyCode.DIGIT3 ->
                            EntitySpawner.spawnEnemy(EnemyType.GHOST, new Point2D(900, 250), new Rectangle());
                    case KeyCode.DIGIT4 ->
                            EntitySpawner.spawnEnemy(EnemyType.DEMON, new Point2D(800, 250), new Rectangle());
                }
            }
        });
    }

    public void initUI() {
        if (Config.DEBUG) LoadStatistics.initUI();
        DayTimeCycleManager.getInstance().initUI();

        String message1 = """
                Движение - WASD
                Уклонение - SPACE
                Атака - K (налево), L (направо)
                Инвентарь игрока - I
                Открыть внешний инвентарь - O
                Выбрать предмет в инвентаре - E
                """;

        String message2 = """
                Переместить все предметы в другой инвентарь - R
                Переместить 1 предмет в другой инвентарь - G
                Применить предмет - F
                Осмотреть предмет - V
                Переключение между инвентарями - Q
                Показать/скрыть помощь - H
                """;

        helpTextPane = new Pane();

        helpTextPane.getChildren().addAll(
                constructHelpMessagePane(message1, Pos.BOTTOM_LEFT, TextAlignment.LEFT),
                constructHelpMessagePane(message2, Pos.BOTTOM_RIGHT, TextAlignment.RIGHT)
        );

        FXGL.addUINode(helpTextPane);
    }

    private StackPane constructHelpMessagePane(String message, Pos paneAlignment, TextAlignment textAlignment) {
        StackPane outer = new StackPane();
        outer.setMinWidth(FXGL.getAppWidth());
        outer.setMinHeight(FXGL.getAppHeight());

        StackPane pane = new StackPane();
        pane.setMaxHeight(10);
        pane.setMaxWidth(10);
        pane.setAlignment(Pos.BASELINE_CENTER);

        Text text = TextFactory.create(message, TextType.ST_SONG, TextColor.PRIMARY_YELLOW, TextSize.S);
        text.setTextAlignment(textAlignment);
        text.setStroke(Color.BLACK);
        text.setStrokeWidth(0.1);

        pane.getChildren().addAll(text);
        pane.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.3), new CornerRadii(10), new Insets(-10))));

        outer.setAlignment(paneAlignment);
        outer.getChildren().add(pane);

        return outer;
    }

    public void initPhysics() {
        FXGL.getPhysicsWorld().addCollisionHandler(new AttackTrailCollisionHandler(AttackOwner.PLAYER));
        FXGL.getPhysicsWorld().addCollisionHandler(new AttackTrailCollisionHandler(AttackOwner.ENEMY));
    }

    public void loop() {
        if (Config.DEBUG) LoadStatistics.updateUI();
        if (iterationsCount < 2) {
            iterationsCount += 1;
            if (iterationsCount == 0) {
                FXGL.getInput().mockKeyPress(KeyCode.I);
            } else {
                PlayerManager.getInstance().getInventory().close();
            }
        }
    }

    private void bindViewportToEntity(Entity entity) {
        Viewport viewport = FXGL.getGameScene().getViewport();
        viewport.setLazy(true);
        viewport.setZoom(3);

        Point2D entitySize = EntityUtils.getViewSize(entity);

        viewport.bindToEntity(
                entity,
                (FXGL.getAppWidth()) / 2.0 - entitySize.getX() - 20,
                (FXGL.getAppHeight()) / 2.0 - entitySize.getY() - 20
        );

        if (!Config.DEBUG) {
            viewport.setBounds(0, 0, Config.Map.WIDTH, Config.Map.HEIGHT);
        }
    }

    private void initializeDayTimeManager() {
        var elems = FXGL.getGameScene().getContentRoot().getChildren();
        var test = DayTimeManager.getInstance().getOverlayNode();

        for (var el : elems) {
            if (el == test) {
                DayTimeManager.getInstance().forceSet(DayTimeType.DAY);
                return;
            }
        }

        elems.add(DayTimeManager.getInstance().getOverlayNode());
    }
}
