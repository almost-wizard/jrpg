package ru.rsreu.gorobchenko.project.components.character.ai.states;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import ru.rsreu.gorobchenko.project.components.character.ai.AiComponent;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AiAttack;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.AiParametersComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;
import ru.rsreu.gorobchenko.project.shared.utils.VectorUtils;

public class AttackState extends EntityState {
    private Entity player;

    private final Entity entity;
    private final PhysicsComponent physics;
    private final CharacterAnimationComponent animationComponent;
    private final CharacterAnimation characterAnimation;
    private final AiParametersComponent parameters;
    private final AiAttack aiAttack;

    private static int enemiesInAttackCount = 0;
    private AiComponent aiComponent;

    public AttackState(Entity entity, CharacterAnimation characterAnimation, AiAttack aiAttack) {
        this.entity = entity;
        this.physics = entity.getComponent(PhysicsComponent.class);
        this.animationComponent = entity.getComponent(CharacterAnimationComponent.class);
        this.characterAnimation = characterAnimation;
        this.parameters = entity.getComponent(AiParametersComponent.class);
        this.aiAttack = aiAttack;
    }

    @Override
    public void onEntering() {
        enemiesInAttackCount += 1;
    }

    @Override
    public void onExited() {
        enemiesInAttackCount -= 1;
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player == null) {
            player = PlayerManager.getInstance().getPlayer();
        }

        boolean isPlayerInsideArea = aiAttack.getArea().contains(player.getAnchoredPosition());

        double distanceToPlayer = EntityUtils.distance(entity, player);
        double distanceToStart = Math.abs(aiAttack.getStartPosition().distance(entity.getAnchoredPosition()));

        if (aiAttack.withFixedArea() && !isPlayerInsideArea && (
                distanceToStart > 2 * parameters.getTriggerDistance() ||
                        distanceToPlayer > parameters.getTriggerDistance()
        )) {
            returnToStart();
            return;
        }

        if (aiAttack.withStopping() && aiAttack.isAttackingNow()) {
            return;
        }

        followPlayer(distanceToPlayer);
    }

    private void returnToStart() {
        aiComponent.getState().changeState(aiComponent.getReturningState());
    }

    private void followPlayer(double distance) {
        if (physics.isMoving()) {
            followPlayerIf(distance > parameters.getStopDistance());
        } else {
            followPlayerIf(distance > parameters.getStopDistance() + parameters.getStandZoneRadius());
        }
    }

    private void followPlayerIf(boolean followCondition) {
        Point2D direction = EntityUtils.direction(player, entity);
        animationComponent.setReverted(direction.getX() > 0);

        if (followCondition) {
            move(direction);
        } else {
            boolean isMoved = physics.isMoving();

            stop();

            if (aiAttack.isAttackCooldowned() && !aiAttack.isAttackingNow()) {
                aiAttack.attack(direction, isMoved);
            }
        }
    }

    private void move(Point2D direction) {
        Point2D yaw = Point2D.ZERO;
        if (enemiesInAttackCount > 1) {
            double randomYaw = (Math.random() - 0.5) * 0.5;
            yaw = VectorUtils.perpendicular(direction).multiply(randomYaw);
        }
        Point2D newVelocity = direction.add(yaw).multiply(parameters.getSpeed());

        if (!newVelocity.equals(physics.getLinearVelocity())) {
            physics.setLinearVelocity(newVelocity);
        }

        if (!aiAttack.isAttackingNow()) {
            animationComponent.loopAnimation(characterAnimation.getRun());
        }
    }

    private void stop() {
        physics.setLinearVelocity(Point2D.ZERO);

        if (!aiAttack.isAttackingNow()) {
            animationComponent.loopAnimation(characterAnimation.getIdle());
        }
    }

    public AiAttack getAiAttack() {
        return aiAttack;
    }

    public void setAiComponent(AiComponent aiComponent) {
        this.aiComponent = aiComponent;
    }
}
