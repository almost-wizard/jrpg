package ru.rsreu.gorobchenko.project.components.character.ai.attack;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;

public class Attack {
    private final AttackOwner owner;
    private final BoundingShape boundingShape;
    private final int comboIndex;
    private final Duration delayBtwSpawn;
    private final AnimationChannel animationChannel;

    private Weapon weapon;

    public Attack(AttackOwner owner, BoundingShape boundingShape, int comboIndex, Duration delayBtwSpawn, AnimationChannel animationChannel) {
        this.owner = owner;
        this.boundingShape = boundingShape;
        this.comboIndex = comboIndex;
        this.delayBtwSpawn = delayBtwSpawn;
        this.animationChannel = animationChannel;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Attack setWeapon(Weapon weapon) {
        this.weapon = weapon;
        return this;
    }

    public AttackOwner getOwner() {
        return this.owner;
    }

    public BoundingShape getBoundingShape() {
        return this.boundingShape;
    }

    public int getComboIndex() {
        return this.comboIndex;
    }

    public Duration getDelayBtwSpawn() {
        return this.delayBtwSpawn;
    }

    public AnimationChannel getAnimationChannel() {
        return this.animationChannel;
    }
}
