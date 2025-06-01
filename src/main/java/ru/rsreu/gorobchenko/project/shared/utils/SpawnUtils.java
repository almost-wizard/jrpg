package ru.rsreu.gorobchenko.project.shared.utils;

import com.almasb.fxgl.core.collection.PropertyMap;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class SpawnUtils {
    public static Rectangle getRectangleFrom(SpawnData data) {
        return new Rectangle(data.getX(), data.getY(), (double)data.<Integer>get("width"), (double)data.<Integer>get("height"));
    }
    public static Rectangle getRectangleFrom(Entity entity) {
        PropertyMap map = entity.getProperties();
        return new Rectangle(entity.getX(), entity.getY(), (double)map.<Integer>getValue("width"), (double)map.<Integer>getValue("height"));
    }

    public static int getPreferredEnemiesCount(Rectangle area) {
        double square = area.getWidth() * area.getHeight();
        return Math.max((int)(square / 256 / 100), 1);
    }

    public static Point2D[] getSpawnCoordinates(int enemiesCount, Rectangle area) {
        Point2D[] coordinates = new Point2D[enemiesCount];
        for (int i = 0; i < enemiesCount; i++) {
            double randomX = Math.random() * area.getWidth() + area.getX();
            double randomY = Math.random() * area.getHeight() + area.getY();
            coordinates[i] = new Point2D(randomX, randomY);
        }
        return coordinates;
    }
}
