package ru.rsreu.gorobchenko.project.entity.item.mutagen.list;

import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.mutagen.Mutagen;
import ru.rsreu.gorobchenko.project.shared.utils.CutsceneUtils;

public class DeadTreasure extends Mutagen {
    public DeadTreasure() {
        super("assets/textures/potions/mutagen.png");
        setName("???");
        setItemDescription("???");
        setParametersDescription("???");
    }

    @Override
    public Item constructCopy() {
        return new DeadTreasure();
    }

    @Override
    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        parameters.damageCoefficient *= 2;
        CutsceneUtils.start("dead-treasure.txt");
        return true;
    }
}
