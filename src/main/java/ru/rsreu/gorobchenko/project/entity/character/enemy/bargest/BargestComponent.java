package ru.rsreu.gorobchenko.project.entity.character.enemy.bargest;

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

public class BargestComponent extends EnemyComponent {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 30;

    private final CharacterAnimation bargestAnimation;
    private AiComponent aiComponent;

    public BargestComponent(EnemyImportance importance, BargestType bargestType, Rectangle area, Point2D startPosition) {
        super(importance, WIDTH, HEIGHT, area, startPosition);

        this.bargestAnimation = new BargestAnimation(bargestType.getSpritePath());
    }

    @Override
    public void onAdded() {
        CharacterAnimationComponent animation = new CharacterAnimationComponent();
        animation.loopAnimation(bargestAnimation.getIdle());
        entity.addComponent(animation);

        entity.setLocalAnchor(new Point2D(bargestAnimation.getSpriteWidth() / 2.0, bargestAnimation.getSpriteHeight() * 0.6));

        entity.addComponent(new CharacterPhysicsComponent(Filters.NPC.getFilter()));
        entity.addComponent(new HitBoxComponent(this.getColliderWidth(), this.getColliderHeight()));
        entity.addComponent(new CollidableComponent(true));

        entity.addComponent(
                new CharacterBuilder()
                        .health(new DynamicValue(100))
                        .speed(200)
                        .attackSpeed(300)
                        .triggerDistance(300)
                        .standZoneRadius(50)
                        .stopDistance(50)
                        .attacks(getAttacks())
                        .buildAi()
        );

        inventory.getModel().resetItems(Remnant.getBargestRemnants());
        entity.addComponent(inventory);

        EntityState idle = new IdleState(entity, bargestAnimation);
        AiAttack aiAttack = new AiAttack(entity, bargestAnimation, true, true, area, startPosition);
        AttackState attack = new AttackState(entity, bargestAnimation, aiAttack);
        ReturningState returning = new ReturningState(entity, bargestAnimation, attack.getAiAttack());
        EntityState deadMinable = new DeadMinableState(entity, bargestAnimation, getImportance());
        EntityState deadDisappear = new DeadDisappearState(entity);

        aiComponent = new AiComponent(idle, attack, returning, deadMinable, deadDisappear);
        attack.setAiComponent(aiComponent);
        returning.setAiComponent(aiComponent);
        entity.addComponent(aiComponent);

        entity.addComponent(new NpcHealthBarComponent(this));
    }

    private Attack[] getAttacks() {
        var bbox = BoundingShape.box(bargestAnimation.getSpriteWidth(), bargestAnimation.getSpriteHeight());

        return new Attack[]{
                new Attack(AttackOwner.ENEMY, bbox, 0, Duration.seconds(0.1), bargestAnimation.getAttack())
                        .setWeapon(new AiNonPhysical(10, 10, 1.2)),
        };
    }

    @Override
    public void die() {
        aiComponent.getState().changeState(aiComponent.getDeadMinable());
    }
}
