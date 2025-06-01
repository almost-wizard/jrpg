package ru.rsreu.gorobchenko.project.components.character.ai.states;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyImportance;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyType;
import ru.rsreu.gorobchenko.project.entity.character.enemy.demon.DemonComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.MinableInventoryComponent;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.AiParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.factory.EntitySpawner;
import ru.rsreu.gorobchenko.project.factory.EntityType;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;

public class DeadMinableState extends EntityState {

    private final Entity entity;
    private final PhysicsComponent physics;
    private final CharacterAnimationComponent animationComponent;
    private final CharacterAnimation characterAnimation;
    private final CharacterParametersComponent parameters;
    private final EnemyImportance importance;

    public DeadMinableState(Entity entity, CharacterAnimation characterAnimation, EnemyImportance importance) {
        this.entity = entity;
        this.physics = entity.getComponent(PhysicsComponent.class);
        this.animationComponent = entity.getComponent(CharacterAnimationComponent.class);
        this.parameters = entity.getComponent(AiParametersComponent.class);
        this.characterAnimation = characterAnimation;
        this.importance = importance;
    }

    @Override
    public void onEntering() {
        entity.getComponent(MinableInventoryComponent.class).setEnabled(true);
        physics.setLinearVelocity(Point2D.ZERO);
        parameters.kill(
                () -> animationComponent.playAnimation(
                        characterAnimation.getDie(), () -> {
                            if (importance == EnemyImportance.IMPORTANT) {
                                EntitySpawner.decrementNecessaryEnemiesCount();
                            }

                            EnemyComponent component = EnemyType.getEnemyClass(entity);

                            if (component != null) {
                                if (component instanceof DemonComponent) {
                                    // Demon near the Arondit has been beaten, so we need to remove collider
                                    EntityUtils.removeFromWorld(EntityType.RM_COLLIDER_TO_ARONDIT);
                                }
                            }

                            EntitySpawner.checkWin();
                        }
                )
        );
    }
}
