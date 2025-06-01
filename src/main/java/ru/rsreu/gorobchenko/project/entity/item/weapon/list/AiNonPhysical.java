package ru.rsreu.gorobchenko.project.entity.item.weapon.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;

public class AiNonPhysical extends Weapon {
    public AiNonPhysical(int damage, double criticalDamageChance, double criticalDamageMultiplier) {
        super("", damage, criticalDamageChance, criticalDamageMultiplier);
    }

    @Override
    public Item constructCopy() {
        return new AiNonPhysical(this.getDamage(), this.getCriticalDamageChance(), this.getCriticalDamageMultiplier());
    }
}
