package ru.rsreu.gorobchenko.project.entity.item.weapon;

import com.almasb.fxgl.core.math.FXGLMath;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;

public abstract class Weapon extends Item {
    private final int damage;
    private final double criticalDamageChance;
    private final double criticalDamageMultiplier;

    protected Weapon(String spritePath, int damage, double criticalDamageChance, double criticalDamageMultiplier) {
        super(spritePath, false, 1);
        this.damage = damage;
        this.criticalDamageChance = criticalDamageChance;
        this.criticalDamageMultiplier = criticalDamageMultiplier;
    }

    public void apply(CharacterParametersComponent attacker, CharacterParametersComponent target, Attack attack) {
        double comboDamageMultiplier = attacker.getComboMultiplier(attack.getComboIndex());
        double damage = this.damage * comboDamageMultiplier * attacker.damageCoefficient;

        double criticalDamage = 0;
        if (FXGLMath.randomBoolean(criticalDamageChance)) {
            criticalDamage = damage * this.criticalDamageMultiplier - damage;
        }

//        System.out.println("[LOG - DAMAGE]: attacker - " + attack.getOwner().toString() + "; damage - " + damage + "; critical damage - " + criticalDamage + "; combo multiplier - " + comboDamageMultiplier);

        target.takeDamage((int)Math.round(damage));
        target.takeDamage((int)Math.round(criticalDamage));
    }

    public int getDamage() {
        return damage;
    }

    public double getCriticalDamageChance() {
        return criticalDamageChance;
    }

    public double getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }
}
