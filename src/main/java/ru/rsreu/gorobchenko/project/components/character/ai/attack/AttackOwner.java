package ru.rsreu.gorobchenko.project.components.character.ai.attack;

import ru.rsreu.gorobchenko.project.factory.EntityType;

public enum AttackOwner {
    PLAYER,
    ENEMY;

    public static final String ATTACK_TRAIL_TYPE = "attack-trail-type";

    public AttackOwner getOwnerEntityType() {
        return this;
    }

    public EntityType getTargetEntityType() {
        return this == PLAYER ? EntityType.ENEMY : EntityType.PLAYER;
    }
}
