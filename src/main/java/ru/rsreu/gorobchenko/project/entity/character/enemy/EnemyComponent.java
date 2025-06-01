package ru.rsreu.gorobchenko.project.entity.character.enemy;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.entity.inventory.type.MinableInventoryComponent;

public abstract class EnemyComponent extends Component {
    private final int colliderWidth;
    private final int colliderHeight;

    private final EnemyImportance importance;

    protected final MinableInventoryComponent inventory;
    protected final Rectangle area;
    protected final Point2D startPosition;

    public EnemyComponent(EnemyImportance importance, int colliderWidth, int colliderHeight, Rectangle area, Point2D startPosition) {
        this.importance = importance;
        this.colliderWidth = colliderWidth;
        this.colliderHeight = colliderHeight;
        this.area = area;
        this.startPosition = startPosition;

        this.inventory = new MinableInventoryComponent(3, 1);
    }

    public int getColliderWidth() {
        return colliderWidth;
    }

    public int getColliderHeight() {
        return colliderHeight;
    }

    public EnemyImportance getImportance() {
        return importance;
    }

    public abstract void die();
}
