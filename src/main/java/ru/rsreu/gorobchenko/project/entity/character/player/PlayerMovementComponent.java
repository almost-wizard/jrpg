package ru.rsreu.gorobchenko.project.entity.character.player;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import org.jetbrains.annotations.NotNull;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.CharacterPhysicsComponent;

import static com.almasb.fxgl.dsl.FXGL.getInput;

public class PlayerMovementComponent extends Component {
    private Point2D vector = new Point2D(0, 0);
    private Point2D lastVector = new Point2D(0, 0);

    private boolean isRolling = false;

    private PhysicsComponent physics;
    private CharacterAnimationComponent animation;
    private PlayerParametersComponent parameters;
    private PlayerAttackComponent attack;

    private final CharacterAnimation playerAnimation;

    public PlayerMovementComponent(CharacterAnimation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    @Override
    public void onAdded() {
        physics = entity.getComponent(CharacterPhysicsComponent.class).getPhysics();
        animation = entity.getComponent(CharacterAnimationComponent.class);
        parameters = entity.getComponent(PlayerParametersComponent.class);

        getInput().addTriggerListener(new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                switch (keyTrigger.getKey()) {
                    case W -> up(true);
                    case S -> down(true);
                    case A -> left(true);
                    case D -> right(true);
                    case SPACE -> roll();
                }
            }

            @Override
            protected void onKeyEnd(@NotNull KeyTrigger keyTrigger) {
                switch (keyTrigger.getKey()) {
                    case W -> up(false);
                    case S -> down(false);
                    case A -> left(false);
                    case D -> right(false);
                }
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }

        if (isRolling || parameters.isDead()) {
            return;
        }

        if (!vector.multiply(parameters.getSpeed()).equals(physics.getLinearVelocity())) {
            physics.setLinearVelocity(vector.multiply(parameters.getSpeed()));
        }

        Point2D vector = physics.getLinearVelocity();
        if (physics.isMoving()) {
            if (vector.getY() != 0 && vector.getX() == 0) {
                animation.loopAnimation(playerAnimation.getRun());
            }
            if (vector.getX() != 0) {
                animation.loopAnimation(playerAnimation.getRun()).setReverted(vector.getX() < 0);
            }
        } else {
            if (lastVector.getY() != 0 && lastVector.getX() == 0) {
                animation.loopAnimation(playerAnimation.getIdle());
            }
            if (lastVector.getX() != 0) {
                animation.loopAnimation(playerAnimation.getIdle()).setReverted(lastVector.getX() < 0);
            }
        }

        lastVector = vector;
    }

    public void up(boolean isGoes) {
        vector = new Point2D(vector.getX(), isGoes ? -1 : 0).normalize();
    }

    public void down(boolean isGoes) {
        vector = new Point2D(vector.getX(), isGoes ? 1 : 0).normalize();
    }

    public void left(boolean isGoes) {
        vector = new Point2D(isGoes ? -1 : 0, vector.getY()).normalize();
    }

    public void right(boolean isGoes) {
        vector = new Point2D(isGoes ? 1 : 0, vector.getY()).normalize();
    }

    public boolean isRolling() {
        return isRolling;
    }

    private void roll() {
        if (isRolling || parameters.isDead() || !parameters.getStamina().hasEnoughToRoll() || isAttacking()) {
            return;
        }

        this.isRolling = true;

        parameters.getStamina().decrement(40);
        parameters.getStamina().startRestoreTimer();

        physics.setLinearVelocity(vector.multiply(parameters.getRollSpeed()));
        animation.playAnimation(playerAnimation.getRoll(), () -> {
            isRolling = false;
            animation.loopAnimation(playerAnimation.getIdle());
        });
    }

    private boolean isAttacking() {
        if (attack == null) {
            attack = PlayerManager.getInstance().getPlayer().getComponent(PlayerAttackComponent.class);
        }
        return attack.isAttacking();
    }
}
