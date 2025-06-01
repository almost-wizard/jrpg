package ru.rsreu.gorobchenko.project.components.character.ai.states;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.state.EntityState;
import javafx.util.Duration;

public class DeadDisappearState extends EntityState {
    private final Entity entity;

    public DeadDisappearState(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void onEntering() {
        FXGL.animationBuilder()
                .duration(Duration.seconds(1))
                .onFinished(entity::removeFromWorld)
                .fadeOut(entity)
                .buildAndPlay();
    }
}
