package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

public class RaffardTheWhite extends Potion {
    public RaffardTheWhite() {
        super("assets/textures/potions/raffard-the-white.png", 50);
        //setBigSpritePath("assets/textures/potions/raffard-the-white-big.png");
        setName("«Зелье Раффарда Белого»");
        setItemDescription("Раффард Белый, известный маг давних времен, разработал лечебное зелье для воинов, " +
                "которые завоевывали для людей новый мир много веков назад. Классическая традиция рекомендует " +
                "использовать Ласточку, поскольку зелье Раффарда чрезвычайно токсично.");
        setParametersDescription("""
                Действие: зелье мгновенно восстанавливает большую часть утраченного здоровья.
                Срок действия: мгновенный.
                Токсичность: высокая.""");
    }

    @Override
    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        if (!super.applyToPlayer(parameters)) {
            return false;
        }
        parameters.heal(100);
        onEnd();
        return true;
    }

    @Override
    public Item constructCopy() {
        return new RaffardTheWhite();
    }
}
