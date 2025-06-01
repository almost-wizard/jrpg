package ru.rsreu.gorobchenko.project.entity.character.enemy.demon;

import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.physics.BoundingShape;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.HitBoxComponent;
import ru.rsreu.gorobchenko.project.components.character.CharacterPhysicsComponent;
import ru.rsreu.gorobchenko.project.components.character.DynamicValue;
import ru.rsreu.gorobchenko.project.components.character.ai.AiComponent;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AiAttack;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AttackOwner;
import ru.rsreu.gorobchenko.project.components.character.ai.states.*;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterBuilder;
import ru.rsreu.gorobchenko.project.components.character.ui.NpcHealthBarComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyImportance;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;
import ru.rsreu.gorobchenko.project.entity.item.weapon.list.AiNonPhysical;
import ru.rsreu.gorobchenko.project.physics.filter.Filters;

public class DemonComponent extends EnemyComponent {
    private static final int WIDTH = 70;
    private static final int HEIGHT = 90;
    private final CharacterAnimation demonAnimation = new DemonAnimation();
    private AiComponent aiComponent;

    public DemonComponent(EnemyImportance importance, Rectangle area, Point2D startPosition) {
        super(importance, WIDTH, HEIGHT, area, startPosition);
    }

    @Override
    public void onAdded() {
        CharacterAnimationComponent animation = new CharacterAnimationComponent();
        animation.loopAnimation(demonAnimation.getIdle());
        entity.addComponent(animation);

        entity.setLocalAnchor(new Point2D(demonAnimation.getSpriteWidth() / 2.0, demonAnimation.getSpriteHeight() * 0.7));

        entity.addComponent(new CharacterPhysicsComponent(Filters.GHOST.getFilter()));
        entity.addComponent(new HitBoxComponent(WIDTH, HEIGHT));
        entity.addComponent(new CollidableComponent(true));

        entity.addComponent(
                new CharacterBuilder()
                        .health(new DynamicValue(1000))
                        .speed(50)
                        .triggerDistance(300)
                        .stopDistance(50)
                        .standZoneRadius(50)
                        .attacks(getAttacks())
                        .buildAi()
        );

        inventory.getModel().resetItems(Remnant.getDemonRemnants());
        entity.addComponent(inventory);

        EntityState idle = new IdleState(entity, demonAnimation);
        AiAttack aiAttack = new AiAttack(entity, demonAnimation, true, false, area, startPosition);
        AttackState attack = new AttackState(entity, demonAnimation, aiAttack);
        ReturningState returning = new ReturningState(entity, demonAnimation, attack.getAiAttack());
        EntityState deadMinable = new DeadMinableState(entity, demonAnimation, getImportance());
        EntityState deadDisappear = new DeadDisappearState(entity);

        aiComponent = new AiComponent(idle, attack, returning, deadMinable, deadDisappear);
        attack.setAiComponent(aiComponent);
        returning.setAiComponent(aiComponent);
        entity.addComponent(aiComponent);

        entity.addComponent(new NpcHealthBarComponent(this));
    }

    private Attack[] getAttacks() {
        var bbox = BoundingShape.box(demonAnimation.getSpriteWidth(), demonAnimation.getSpriteHeight());

        return new Attack[]{
                new Attack(AttackOwner.ENEMY, bbox, 0, Duration.seconds(0.7), demonAnimation.getAttack())
                        .setWeapon(new AiNonPhysical(100, 50, 1.5)),
        };
    }

    @Override
    public void die() {
        aiComponent.getState().changeState(aiComponent.getDeadMinable());
    }
}
