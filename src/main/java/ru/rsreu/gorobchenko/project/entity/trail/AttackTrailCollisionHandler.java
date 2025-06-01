package ru.rsreu.gorobchenko.project.entity.trail;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import ru.rsreu.gorobchenko.project.components.character.parameters.AiParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AttackOwner;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerMovementComponent;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;

public class AttackTrailCollisionHandler extends CollisionHandler {
    private final AttackOwner attackOwner;

    public AttackTrailCollisionHandler(AttackOwner attackOwner) {
        super(attackOwner.getOwnerEntityType(), attackOwner.getTargetEntityType());
        this.attackOwner = attackOwner;
    }

    @Override
    protected void onCollisionBegin(Entity attackTrail, Entity targetEntity) {
        AttackTrailComponent trail = attackTrail.getComponent(AttackTrailComponent.class);

        Weapon weapon;
        CharacterParametersComponent target;
        CharacterParametersComponent attacker = trail.getAttackerParameters();

        if (attackOwner == AttackOwner.ENEMY) {
            if (targetEntity.getComponent(PlayerMovementComponent.class).isRolling()) {
                return;
            }
            weapon = trail.getAttack().getWeapon();
            target = targetEntity.getComponent(PlayerParametersComponent.class);
        } else {
            if (targetEntity.getComponent(AiParametersComponent.class).isDead()) {
                return;
            }

            weapon = PlayerManager.getInstance().getInventory().getWeapon();
            target = targetEntity.getComponent(AiParametersComponent.class);
        }

        weapon.apply(attacker, target, trail.getAttack());
        trail.getEntity().removeFromWorld();
    }
}
