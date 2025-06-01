package ru.rsreu.gorobchenko.project.entity.item.weapon.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;

public class Arondit extends Weapon {
    private static final int BASE_DAMAGE = 40;
    private static final double CRITICAL_DAMAGE_CHANCE = 0.5;
    private static final double CRITICAL_DAMAGE_MULTIPLIER = 3;

    public Arondit() {
        super("assets/textures/weapons/arondit.png", BASE_DAMAGE, CRITICAL_DAMAGE_CHANCE, CRITICAL_DAMAGE_MULTIPLIER);
        setBigSpritePath("assets/textures/weapons/arondit-big.png");
        setName("Арондит?");
        setItemDescription("Почти всё время он терпеливо ждёт следующего владельца, скрывшись от чужих глаз. Говорят, найдёт меч лишь достойный");
        setParametersDescription("Этот острый как бритва клинок идеально сбалансирован и на удивление лёгок, что позволяет владельцу с легкостью рассекать врагов на части");
    }

    @Override
    public Item constructCopy() {
        return new Arondit();
    }
}
