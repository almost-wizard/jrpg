package ru.rsreu.gorobchenko.project.shared.utils;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.ViewComponent;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyType;
import ru.rsreu.gorobchenko.project.factory.EntityType;

import java.util.List;

public class EntityUtils {
    public static double distance(Entity e1, Entity e2) {
        Point2D e1Center = e1.getPosition().add(getViewSize(e1).multiply(0.5));
        Point2D e2Center = e2.getPosition().add(getViewSize(e2).multiply(0.5));
        return e1Center.distance(e2Center);
    }

    public static Point2D direction(Entity e1, Entity e2) {
        return e1.getAnchoredPosition().subtract(e2.getAnchoredPosition()).normalize();
    }

    public static Point2D getViewSize(Entity entity) {
        if (!entity.hasComponent(ViewComponent.class)) {
            return Point2D.ZERO;
        }

        ViewComponent view = entity.getComponent(ViewComponent.class);
        return getViewSize(view.getChildren());
    }

    public static Point2D getViewSize(List<Node> children) {
        double maxX = 0.0;
        double maxY = 0.0;

        for (var c : children) {
            if (c.getBoundsInLocal().getWidth() > maxX) {
                maxX = c.getBoundsInLocal().getWidth();
            }
            if (c.getBoundsInLocal().getHeight() > maxY) {
                maxY = c.getBoundsInLocal().getHeight();
            }
        }

        return new Point2D(maxX, maxY);
    }

    public static void removeFromWorld(Enum<?> entitiesType) {
        List<Entity> entities = FXGL.getGameWorld().getEntitiesByType(entitiesType);
        for (Entity e : entities) {
            e.removeFromWorld();
        }
    }

    public static void killEnemies(List<Entity> entities) {
        for (Entity entity : entities) {
            EnemyComponent component = EnemyType.getEnemyClass(entity);
            if (component == null) {
                continue;
            }

            component.die();
        }
    }

    public static void killAllEnemies() {
        List<Entity> enemies = FXGL.getGameWorld().getEntitiesByType(EntityType.ENEMY);

        for (Entity entity : enemies) {
            EnemyComponent component = EnemyType.getEnemyClass(entity);
            if (component == null) {
                continue;
            }

            component.die();
        }
    }
}
