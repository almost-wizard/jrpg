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

public class ReturningState extends EntityState {
    private Entity player;

    private final Entity entity;
    private final AiParametersComponent parameters;
    private final PhysicsComponent physics;
    private final CharacterAnimationComponent animationComponent;
    private final CharacterAnimation characterAnimation;
    private final AiAttack aiAttack;

    private AiComponent aiComponent;

    public ReturningState(Entity entity, CharacterAnimation characterAnimation, AiAttack aiAttack) {
        this.entity = entity;
        this.aiAttack = aiAttack;
        this.parameters = entity.getComponent(AiParametersComponent.class);
        this.physics = entity.getComponent(PhysicsComponent.class);
        this.animationComponent = entity.getComponent(CharacterAnimationComponent.class);
        this.characterAnimation = characterAnimation;
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player == null) {
            player = PlayerManager.getInstance().getPlayer();
        }

        double distanceToStart = entity.getAnchoredPosition().distance(aiAttack.getStartPosition());

        if (distanceToStart <= parameters.getStopDistance()) {
            stop();
        } else {
            Point2D direction = aiAttack.getStartPosition().subtract(entity.getAnchoredPosition());
            move(direction.normalize());
        }
    }

    private void move(Point2D direction) {
        animationComponent.setReverted(direction.getX() > 0);

        Point2D newVelocity = direction.multiply(parameters.getSpeed());

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

        aiComponent.getState().changeState(aiComponent.getIdleState());
    }

    public void setAiComponent(AiComponent aiComponent) {
        this.aiComponent = aiComponent;
    }
}
