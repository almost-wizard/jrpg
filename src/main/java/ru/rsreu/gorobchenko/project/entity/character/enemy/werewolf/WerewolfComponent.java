package ru.rsreu.gorobchenko.project.entity.character.enemy.werewolf;

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
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;
import ru.rsreu.gorobchenko.project.entity.item.weapon.list.AiNonPhysical;
import ru.rsreu.gorobchenko.project.physics.filter.Filters;

public class WerewolfComponent extends EnemyComponent {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 40;

    private static final CharacterAnimation werewolfAnimation = new WerewolfAnimation();
    private AiComponent aiComponent;

    public WerewolfComponent(EnemyImportance importance, Rectangle area, Point2D startPosition) {
        super(importance, WIDTH, HEIGHT, area, startPosition);
    }

    @Override
    public void onAdded() {
        CharacterAnimationComponent animation = new CharacterAnimationComponent();
        animation.loopAnimation(werewolfAnimation.getIdle());
        entity.addComponent(animation);

        entity.setLocalAnchor(new Point2D(werewolfAnimation.getSpriteWidth() / 2.0, werewolfAnimation.getSpriteHeight() * 0.75));

        entity.addComponent(new CharacterPhysicsComponent(Filters.NPC.getFilter()));
        entity.addComponent(new HitBoxComponent(WIDTH, HEIGHT));
        entity.addComponent(new CollidableComponent(true));

        entity.addComponent(
                new CharacterBuilder()
                        .health(new DynamicValue(400))
                        .speed(180)
                        .attackSpeed(300)
                        .triggerDistance(230)
                        .standZoneRadius(20)
                        .stopDistance(30)
                        .attacks(getAttacks())
                        .comboMultipliers(0.8, 1.0, 1.2)
                        .buildAi()
        );

        inventory.getModel().resetItems(Remnant.getWerewolfRemnants());
        entity.addComponent(inventory);

        EntityState idle = new IdleState(entity, werewolfAnimation);
        WerewolfAttack werewolfAttack = new WerewolfAttack(entity, werewolfAnimation, area, startPosition);
        AttackState attack = new AttackState(entity, werewolfAnimation, werewolfAttack);
        ReturningState returning = new ReturningState(entity, werewolfAnimation, attack.getAiAttack());
        EntityState deadMinable = new DeadMinableState(entity, werewolfAnimation, getImportance());
        EntityState deadDisappear = new DeadDisappearState(entity);

        aiComponent = new AiComponent(idle, attack, returning, deadMinable, deadDisappear);
        attack.setAiComponent(aiComponent);
        returning.setAiComponent(aiComponent);
        entity.addComponent(aiComponent);

        entity.addComponent(new NpcHealthBarComponent(this));
    }

    private Attack[] getAttacks() {
        var bbox1 = BoundingShape.box(werewolfAnimation.getSpriteWidth() / 2.0, werewolfAnimation.getSpriteHeight());
        var bbox2 = BoundingShape.box(werewolfAnimation.getSpriteWidth(), werewolfAnimation.getSpriteHeight());

        Weapon simpleWeapon = new AiNonPhysical(15, 10, 1.2);
        Weapon runWeapon = new AiNonPhysical(30, 30, 1.5);
        return new Attack[]{
                new Attack(AttackOwner.ENEMY, bbox1, 0, Duration.seconds(0.2), werewolfAnimation.getAttack(0))
                        .setWeapon(simpleWeapon),
                new Attack(AttackOwner.ENEMY, bbox1, 1, Duration.seconds(0.2), werewolfAnimation.getAttack(1))
                        .setWeapon(simpleWeapon),
                new Attack(AttackOwner.ENEMY, bbox1, 2, Duration.seconds(0.2), werewolfAnimation.getAttack(2))
                        .setWeapon(simpleWeapon),
                new Attack(AttackOwner.ENEMY, bbox2, 0, Duration.seconds(0.1), werewolfAnimation.getAttack(3))
                        .setWeapon(runWeapon),
        };
    }

    @Override
    public void die() {
        aiComponent.getState().changeState(aiComponent.getDeadMinable());
    }
}
