package ru.rsreu.gorobchenko.project.components;

import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;

public class HitBoxComponent extends Component {
    private final SpawnData data;
    private Point2D pivot;

    public HitBoxComponent(SpawnData data) {
        this.data = data;
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
    }

    public HitBoxComponent(int width, int height) {
        this.data = new SpawnData();
        data.put("width", width);
        data.put("height", height);
    }

    @Override
    public void onAdded() {
        if (data.hasKey("without-collider")) {
            return;
        }

        pivot = entity.getLocalAnchor();

        if (data.hasKey("polygon")) {
            addPolygon();
        } else if (data.hasKey("polyline")) {
            addPolyline();
        } else {
            addRectangle();
        }
    }

    private void addRectangle() {
        Point2D entityViewSize = EntityUtils.getViewSize(entity);

        Rectangle result = new Rectangle(0, 0, data.<Integer>get("width"), data.<Integer>get("height"));
        if (pivot != Point2D.ZERO) {
            result = centerColliderOnPivot(result);
        } else {
            result = centerColliderOnEntityView(result, entityViewSize);
        }

        if (entityViewSize.getX() != 0 && entityViewSize.getY() != 0) {
            addFrameView(new Rectangle(entityViewSize.getX(), entityViewSize.getY()));
        }

        addColliderView(result);

        HitBox hitBox = new HitBox(BoundingShape.polygon(
                new Point2D(result.getX(), result.getY()),
                new Point2D(result.getX() + result.getWidth(), result.getY()),
                new Point2D(result.getX() + result.getWidth(), result.getY() + result.getHeight()),
                new Point2D(result.getX(), result.getY() + result.getHeight())
        ));
        entity.getBoundingBoxComponent().addHitBox(hitBox);
    }

    private void addPolyline() {
        addColliderView(data.<Polyline>get("polyline"));

        var points = data.<Polyline>get("polyline").getPoints();
        HitBox hitBox = new HitBox(createChain(points));
        entity.getBoundingBoxComponent().addHitBox(hitBox);
    }

    private void addPolygon() {
        addColliderView(data.<Polygon>get("polygon"));

        var points = data.<Polygon>get("polygon").getPoints();
        HitBox hitBox = new HitBox(createChain(points));
        entity.getBoundingBoxComponent().addHitBox(hitBox);
    }

    private BoundingShape createChain(ObservableList<Double> points) {
        Point2D[] points2d = new Point2D[points.size() / 2];
        for (int i = 0; i < points2d.length; i += 1) {
            points2d[i] = new Point2D(points.get(i * 2), points.get(i * 2 + 1));
        }
        return BoundingShape.chain(points2d);
    }

    private void addColliderView(Shape collider) {
        if (!Config.DEBUG) {
            return;
        }
        setColliderStyle(collider);
        entity.getViewComponent().addChild(collider);
    }

    private void addFrameView(Shape view) {
        if (!Config.DEBUG) {
            return;
        }
        setAnimationFrameBorderStyle(view);
        entity.getViewComponent().addChild(view);
    }

    private static Rectangle centerColliderOnEntityView(Rectangle result, Point2D entityViewSize) {
        double x = result.getX();
        double y = result.getY();
        double width = result.getWidth();
        double height = result.getHeight();

        if (entityViewSize.getX() > width) {
            x = (entityViewSize.getX() - width) / 2;
        }
        if (entityViewSize.getY() > height) {
            y = (entityViewSize.getY() - height) / 2;
        }
        return new Rectangle(x, y, width, height);
    }

    private Rectangle centerColliderOnPivot(Rectangle result) {
        double x = pivot.getX() - result.getWidth() / 2;
        double y = pivot.getY() - result.getHeight() / 2;
        double width = result.getWidth();
        double height = result.getHeight();

        return new Rectangle(x, y, width, height);
    }

    private void setColliderStyle(Shape node) {
        node.setFill(Color.TRANSPARENT);
        node.setStroke(Color.RED);
        node.setStrokeWidth(1.5);
    }

    private void setAnimationFrameBorderStyle(Shape node) {
        node.setFill(Color.TRANSPARENT);
        node.setStroke(Color.YELLOW);
        node.setStrokeWidth(1.5);
    }
}
