package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

public class NOT_FINISHED_Blizzard extends Potion {
    public NOT_FINISHED_Blizzard() {
        super("assets/textures/potions/blizzard.png", 20);
    }

    @Override
    public Item constructCopy() {
        return new NOT_FINISHED_Blizzard();
    }
}
