package ru.rsreu.gorobchenko.project.entity.trail;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.factory.EntitySpawner;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;

public class AttackTrailComponent extends Component {
    private final Attack attack;
    private final CharacterParametersComponent attackerParameters;

    public AttackTrailComponent(SpawnData data) {
        this.attack = data.get(Attack.class.getName().toLowerCase());
        this.attackerParameters = data.get(CharacterParametersComponent.class.getName().toLowerCase());
    }

    @Override
    public void onAdded() {
        entity.getBoundingBoxComponent().addHitBox(new HitBox(attack.getBoundingShape()));

        entity.setType(attack.getOwner());

        if (Config.DEBUG) {
            Dimension2D size = attack.getBoundingShape().getSize();
            Rectangle shape = new Rectangle(size.getWidth(), size.getHeight());
            shape.setFill(Color.TRANSPARENT);
            shape.setStroke(Color.RED);
            entity.getViewComponent().addChild(shape);
        }

        entity.addComponent(new CollidableComponent(true));
    }

    public static Entity spawnAttackTrail(Entity entity, Attack attack, boolean isRight, double spiteWidth, CharacterParametersComponent attackerParameters) {
        Point2D position = entity.getPosition();

        if (isRight && attack.getBoundingShape().getSize().getWidth() != spiteWidth) {
            double addX = EntityUtils.getViewSize(entity).getX() / 2;
            position = new Point2D(position.getX() + addX, position.getY());
        }

        return EntitySpawner.spawnAttackTrail(position.getX(), position.getY(), attack, attackerParameters);
    }

    public Attack getAttack() {
        return attack;
    }

    public CharacterParametersComponent getAttackerParameters() {
        return attackerParameters;
    }
}
