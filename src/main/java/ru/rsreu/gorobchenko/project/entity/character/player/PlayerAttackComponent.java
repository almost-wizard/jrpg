package ru.rsreu.gorobchenko.project.entity.character.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.trail.AttackTrailComponent;

import static com.almasb.fxgl.dsl.FXGL.getInput;

public class PlayerAttackComponent extends Component {
    private final CharacterAnimation playerAnimation;

    private CharacterAnimationComponent animation;
    private PhysicsComponent physics;
    private PlayerParametersComponent parameters;
    private PlayerMovementComponent movement;

    public PlayerAttackComponent(CharacterAnimation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    private boolean isAttacking = false;

    private final Duration comboDuration = Duration.seconds(2);
    private final LocalTimer comboTimer = FXGL.newLocalTimer();
    private int currentComboIndex = 0;

    private enum AttackSide {LEFT, RIGHT}

    @Override
    public void onAdded() {
        animation = entity.getComponent(CharacterAnimationComponent.class);
        physics = entity.getComponent(PhysicsComponent.class);
        parameters = entity.getComponent(PlayerParametersComponent.class);
        movement = entity.getComponent(PlayerMovementComponent.class);

        getInput().addTriggerListener(new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                switch (keyTrigger.getKey()) {
                    case K -> attack(AttackSide.LEFT);
                    case L -> attack(AttackSide.RIGHT);
                }
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
        if (isPaused()) {
            return;
        }
        if (physics.isMoving() && isAttacking) {
            isAttacking = false;
        }
        if (comboTimer.elapsed(comboDuration)) {
            currentComboIndex = 0;
        }
    }

    private void attack(AttackSide side) {
        if (isAttacking || physics.isMoving() || movement.isRolling() || parameters.isDead()) {
            return;
        }

        animation.setReverted(side == AttackSide.LEFT);

        isAttacking = true;

        animation.playAnimation(playerAnimation.getAttack(currentComboIndex), () -> {
            isAttacking = false;
            animation.loopAnimation(playerAnimation.getIdle());

            currentComboIndex += 1;
            if (currentComboIndex > parameters.getAttacksCount() - 1) {
                currentComboIndex = 0;
            }
            comboTimer.capture();
        });

        Attack currentAttack = parameters.getAttack(currentComboIndex);
        currentAttack.setWeapon(PlayerManager.getInstance().getInventory().getWeapon());
        FXGL.runOnce(() -> {
            Entity attackTrail = AttackTrailComponent.spawnAttackTrail(PlayerManager.getInstance().getPlayer(), currentAttack, side == AttackSide.RIGHT, playerAnimation.getSpriteWidth(), parameters);
            FXGL.runOnce(attackTrail::removeFromWorld, Duration.seconds(0.2));
        }, currentAttack.getDelayBtwSpawn());
    }

    public boolean isAttacking() {
        return isAttacking;
    }
}
