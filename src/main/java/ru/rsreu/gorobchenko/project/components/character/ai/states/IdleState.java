package ru.rsreu.gorobchenko.project.components.character.ai.states;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;

public class IdleState extends EntityState {
    private final PhysicsComponent physics;
    private final CharacterAnimationComponent animationComponent;
    private final CharacterAnimation characterAnimation;

    public IdleState(Entity entity, CharacterAnimation characterAnimation) {
        this.physics = entity.getComponent(PhysicsComponent.class);
        this.animationComponent = entity.getComponent(CharacterAnimationComponent.class);
        this.characterAnimation = characterAnimation;
    }

    @Override
    public void onEntering() {
        physics.setLinearVelocity(Point2D.ZERO);
        animationComponent.loopAnimation(characterAnimation.getIdle());
    }
}
