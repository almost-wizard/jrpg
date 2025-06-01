package ru.rsreu.gorobchenko.project.gameplay.daytime;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyImportance;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyType;
import ru.rsreu.gorobchenko.project.factory.EntitySpawner;
import ru.rsreu.gorobchenko.project.factory.EntityType;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;
import ru.rsreu.gorobchenko.project.shared.utils.SpawnUtils;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

import java.util.List;

public class DayTimeCycleManager {
    private static final DayTimeCycleManager instance = new DayTimeCycleManager();

    private int timeRemaining;
    private Text timeInfoText;
    private double currentAngle;
    private boolean isAnimating;

    private Pane clockPane;
    private Circle clockFace;
    private Line clockHand;

    private final Point2D pivot = new Point2D(0, 0);

    private int cyclesCount = 0;

    private DayTimeCycleManager() {
        timeRemaining = DayTimeManager.getInstance().getCurrentDayTime().getDuration();
    }

    public static DayTimeCycleManager getInstance() {
        return instance;
    }

    public void startCycle() {
        FXGL.run(() -> {
            timeRemaining -= 1;

            if (!isAnimating && timeRemaining <= 0) {
                nextDayTime();
                animateClockHand();
            }

            updateUIText();
        }, Duration.seconds(1));
    }

    public void initUI() {
        clockPane = new StackPane();
        clockPane.setPrefWidth(270);
        clockPane.setPrefHeight(150);

        clockPane.setTranslateX((FXGL.getAppWidth() - clockPane.getPrefWidth()) / 2.0);
        clockPane.setTranslateY(10);

        clockPane.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 0.3), new CornerRadii(10), new Insets(1))));

        timeInfoText = TextFactory.create("", TextType.ST_SONG, TextColor.PRIMARY_YELLOW, TextSize.M);
        timeInfoText.setTranslateY(20);

        clockFace = new Circle(50, Color.rgb(0, 0, 0, 0.6));

        clockHand = new Line(0, 0, 0, -40);
        clockHand.setStroke(Color.WHITE);
        clockHand.setStrokeWidth(3);
        clockHand.setTranslateY(-20);

        clockPane.getChildren().addAll(clockFace, clockHand, timeInfoText);

        addClockLabels();

        FXGL.addUINode(clockPane);
    }

    private void addClockLabels() {
        StackPane textPane = new StackPane();

        textPane.setMaxWidth(2 * clockFace.getRadius());
        textPane.setMaxHeight(2 * clockFace.getRadius());

        Text day = TextFactory.create("Day", TextType.GOTHIC, TextColor.PRIMARY_YELLOW, TextSize.S);
        day.setTranslateY(-clockFace.getRadius() - 10);

        Text evening = TextFactory.create("Evening", TextType.GOTHIC, TextColor.PRIMARY_YELLOW, TextSize.S);
        evening.setTranslateX(evening.getBoundsInLocal().getWidth() + 20);

        Text night = TextFactory.create("Night", TextType.GOTHIC, TextColor.PRIMARY_YELLOW, TextSize.S);
        night.setTranslateY(clockFace.getRadius() + 10);

        Text morning = TextFactory.create("Morning", TextType.GOTHIC, TextColor.PRIMARY_YELLOW, TextSize.S);
        morning.setTranslateX(-morning.getBoundsInLocal().getWidth() - 20);

        textPane.getChildren().addAll(morning, day, evening, night);

        clockPane.getChildren().add(textPane);
    }

    private void nextDayTime() {
        DayTimeManager.getInstance().next();
        timeRemaining = DayTimeManager.getInstance().getCurrentDayTime().getDuration();

        cyclesCount += 1;

        if (DayTimeManager.getInstance().getCurrentDayTime() == DayTimeType.DAY) {
            timeInfoText.setTranslateY(20);
        } else if (DayTimeManager.getInstance().getCurrentDayTime() == DayTimeType.NIGHT) {
            timeInfoText.setTranslateY(-20);
            performNightActions();
        } else  if (DayTimeManager.getInstance().getCurrentDayTime() == DayTimeType.MORNING) {
            performMorningActions();
        }

        switch (cyclesCount) {
            case 1 -> EntityUtils.removeFromWorld(EntityType.RM_WOODS_TO_LAKE_1);
            case 2 -> EntityUtils.removeFromWorld(EntityType.RM_WOODS_TO_LAKE_2);
            case 3 -> EntityUtils.removeFromWorld(EntityType.RM_WOODS_TO_LAKE_3);
        }
    }

    private void updateUIText() {
        timeInfoText.setText(Integer.toString(timeRemaining));
    }

    private void animateClockHand() {
        isAnimating = true;
        double newAngle = currentAngle + 90;

        FXGL.animationBuilder()
                .duration(Duration.seconds(1))
                .onFinished(() -> isAnimating = false)
                .rotate(clockHand)
                .from(currentAngle)
                .to(newAngle)
                .origin(pivot)
                .buildAndPlay();

        currentAngle = newAngle;
    }

    private void performNightActions() {
        List<Entity> nightSpawnAreas = FXGL.getGameWorld().getEntitiesByType(EntityType.NIGHT_GHOST_AREA);

        for (Entity spawnArea : nightSpawnAreas) {
            Rectangle area = SpawnUtils.getRectangleFrom(spawnArea);
            int enemiesCount = SpawnUtils.getPreferredEnemiesCount(area);
            Point2D[] enemySpawnCoordinates = SpawnUtils.getSpawnCoordinates(enemiesCount, area);

            for (Point2D coordinate : enemySpawnCoordinates) {
                EntitySpawner.spawnEnemy(EnemyImportance.NIGHT_TEMPORARY, EnemyType.GHOST, coordinate, area);
            }
        }
    }

    private void performMorningActions() {
        List<Entity> enemies = FXGL.getGameWorld().getEntitiesFiltered(entity -> {
            if (entity.getType() != EntityType.ENEMY) {
                return false;
            }

            EnemyComponent component = EnemyType.getEnemyClass(entity);
            if (component == null) {
                return false;
            }

            return component.getImportance() == EnemyImportance.NIGHT_TEMPORARY;
        });

        EntityUtils.killEnemies(enemies);
    }
}
