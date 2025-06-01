package ru.rsreu.gorobchenko.project.entity.item.weapon.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;

public class DefaultSword extends Weapon {
    private static final int BASE_DAMAGE = 24;
    private static final double CRITICAL_DAMAGE_CHANCE = 0.1;
    private static final double CRITICAL_DAMAGE_MULTIPLIER = 1.5;

    public DefaultSword() {
        super("assets/textures/weapons/sword.png", BASE_DAMAGE, CRITICAL_DAMAGE_CHANCE, CRITICAL_DAMAGE_MULTIPLIER);
        setBigSpritePath("assets/textures/weapons/sword-big.png");
        setName("Обычный меч");
        setItemDescription("Туповат, но надежный, как швейварские часы. В трудный час авантюристу придется будет рассчитывать именно на этот меч");
        setParametersDescription("Данный меч имеет небольшой урон, но в битве не на жизнь, а на смерть, сослужит вам хорошую службу");
    }

    @Override
    public Item constructCopy() {
        return new DefaultSword();
    }
}
