package ru.rsreu.gorobchenko.project.components.character.ai.attack;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.time.LocalTimer;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.AiParametersComponent;
import ru.rsreu.gorobchenko.project.entity.trail.AttackTrailComponent;

public class AiAttack {
    protected final boolean withStopping;
    protected final boolean isRun;
    protected int maxComboCount = 1;
    protected int currentAttackInCombo = 0;

    private boolean isAttacking = false;
    protected final LocalTimer attackDelayTimer = FXGL.newLocalTimer();
    private static final Duration attackDelay = Duration.seconds(1.2);

    protected final Entity entity;
    protected final CharacterAnimation characterAnimation;
    protected final PhysicsComponent physics;
    protected final AiParametersComponent parameters;
    protected final CharacterAnimationComponent animationComponent;

    protected final Rectangle area;
    protected final Point2D areaCenter;
    private final Point2D startPosition;

    public AiAttack(Entity entity, CharacterAnimation characterAnimation, boolean withStopping, boolean isRun, Rectangle area, Point2D startPosition) {
        this.entity = entity;
        this.characterAnimation = characterAnimation;
        this.physics = entity.getComponent(PhysicsComponent.class);
        this.parameters = entity.getComponent(AiParametersComponent.class);
        this.animationComponent = entity.getComponent(CharacterAnimationComponent.class);

        this.withStopping = withStopping;
        this.isRun = isRun;
        this.area = area;
        this.areaCenter = new Point2D(area.getX() + area.getWidth() / 2, area.getY() + area.getHeight() / 2);
        this.startPosition = startPosition;
    }

    public boolean withFixedArea() {
        return Double.compare(area.getWidth() - area.getX(), 0) != 0 && Double.compare(area.getHeight() - area.getY(), 0) != 0;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setMaxComboCount(int maxCombo) {
        this.maxComboCount = maxCombo;
    }

    public boolean isAttackingNow() {
        return isAttacking;
    }

    public boolean isAttackCooldowned() {
        return attackDelayTimer.elapsed(attackDelay);
    }

    public final boolean withStopping() {
        return withStopping;
    }

    public final boolean isRunAttack() {
        return isRun;
    }

    public void attack(Point2D direction, boolean isMoved) {
        isAttacking = true;

        Attack attack = getCurrentAttack(isMoved);

        if (isMoved || isRunAttack()) {
            physics.setLinearVelocity(direction.multiply(parameters.getAttackSpeed()));
        }

        animationComponent.playAnimation(attack.getAnimationChannel(), () -> afterAttackAnimationEnd(isMoved));

        FXGL.runOnce(() -> {
            Entity attackTrail = AttackTrailComponent.spawnAttackTrail(entity, attack, direction.getX() > 0, characterAnimation.getSpriteWidth(), parameters);
            FXGL.runOnce(attackTrail::removeFromWorld, Duration.seconds(0.2));
        }, attack.getDelayBtwSpawn());
    }

    private void afterAttackAnimationEnd(boolean isMoved) {
        isAttacking = false;

        if (isMoved) {
            currentAttackInCombo = 0;
            attackDelayTimer.capture();
        } else if (maxComboCount > 1) {
            currentAttackInCombo += 1;
            if (currentAttackInCombo > maxComboCount - 1) {
                currentAttackInCombo = 0;
                attackDelayTimer.capture();
            }
        } else {
            currentAttackInCombo = 0;
            attackDelayTimer.capture();
        }
    }

    protected Attack getCurrentAttack(boolean isMoved) {
        return parameters.getAttack(currentAttackInCombo);
    }

    public Point2D getStartPosition() {
        return startPosition;
    }
}
