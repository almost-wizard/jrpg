package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.ui.PlayerParametersUIComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

import java.util.List;

public class WhiteHoney extends Potion {
    public WhiteHoney() {
        super("assets/textures/potions/white-honey.png", -999999999);
        setName("Эликсир «Белый мёд»");
        setItemDescription("Белый мёд сильно стимулирует производство очищающих ферментов. " +
                "Таким образом, он освобождает тело от эффектов отравления, хотя и мгновенно снимает " +
                "все благотворные влияния принятых ранее эликсиров.");
        setParametersDescription("""
                Действие: устраняет токсичность и снимает эффекты всех принятых эликсиров.
                Срок действия: мгновенный.
                Токсичность: отсутствует.""");
    }

    @Override
    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        super.applyToPlayer(parameters);
        List<Potion> appliedPotions = PlayerManager.getInstance().getPlayerComponent().getAppliedPotions();
        for (Potion potion : appliedPotions) {
            potion.expireTimer();
        }
        appliedPotions.clear();
        PlayerManager.getInstance().getPlayer().getComponent(PlayerParametersUIComponent.class).updatePotions(appliedPotions);
        return true;
    }

    @Override
    public Item constructCopy() {
        return new WhiteHoney();
    }
}
