package ru.rsreu.gorobchenko.project.entity.item.mutagen;

import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;

public abstract class Mutagen extends Item {
    public Mutagen(String spritePath) {
        super(spritePath, false, 1);
    }

    @Override
    public boolean apply(CharacterParametersComponent parameters) {
        if (parameters instanceof PlayerParametersComponent) {
            return applyToPlayer((PlayerParametersComponent) parameters);
        }
        throw new IllegalArgumentException("Player parameters expected to Mutagen::apply()");
    }

    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        return true;
    }

    @Override
    public ItemType getType() {
        return ItemType.MUTAGEN;
    }
}
