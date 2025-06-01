package ru.rsreu.gorobchenko.project.entity.item.weapon;

import ru.rsreu.gorobchenko.project.entity.item.Item;

public class NullWeapon extends Weapon {
    private static final NullWeapon instance = new NullWeapon();

    public static NullWeapon getInstance() {
        return instance;
    }

    private NullWeapon() {
        super("", 0, 0, 0);
    }

    @Override
    public Item constructCopy() {
        return this;
    }
}
