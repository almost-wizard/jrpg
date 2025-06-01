package ru.rsreu.gorobchenko.project.entity.item.potion;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.ui.PlayerParametersUIComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;
import ru.rsreu.gorobchenko.project.entity.item.Tradable;

import java.util.List;

public abstract class Potion extends Item implements Tradable {
    private static final int DEFAULT_POTION_COST = 50;

    private final int intoxication;
    private final int cost;

    protected TimerAction timerAction;

    private static TimerAction intoxicationDamageTimer;

    public Potion(Item other) {
        super(other);
        if (!(other instanceof Potion)) {
            throw new IllegalArgumentException("Can't construct Potion from other Item type!");
        }
        intoxication = ((Potion)other).intoxication;
        cost = ((Potion)other).cost;
    }

    public Potion(String spritePath, int intoxication) {
        this(spritePath, intoxication, DEFAULT_POTION_COST);
    }

    public Potion(String spritePath, int intoxication, int cost) {
        super(spritePath, true, 1);
        this.intoxication = intoxication;
        this.cost = cost;
    }

    public void onEnd() {
        List<Potion> appliedPotions = PlayerManager.getInstance().getPlayerComponent().getAppliedPotions();
        appliedPotions.remove(this);
        expireTimer();
        PlayerManager.getInstance().getPlayer().getComponent(PlayerParametersUIComponent.class).updatePotions(appliedPotions);
    }

    public void expireTimer() {
        if (timerAction != null) {
            timerAction.expire();
        }
    }

    @Override
    public boolean apply(CharacterParametersComponent parameters) {
        if (parameters instanceof PlayerParametersComponent) {
            return applyToPlayer((PlayerParametersComponent) parameters);
        }
        throw new IllegalArgumentException("Player parameters expected to Potion::apply()");
    }

    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        if (!parameters.getIntoxication().hasEnoughToDrinkPotion(getIntoxication())) {
            return false;
        }

        List<Potion> appliedPotions = PlayerManager.getInstance().getPlayerComponent().getAppliedPotions();
        appliedPotions.add(this);
        PlayerManager.getInstance().getPlayer().getComponent(PlayerParametersUIComponent.class).updatePotions(appliedPotions);

        parameters.getIntoxication().takePotion(getIntoxication());

        if (parameters.getIntoxication().getCurrentValue() > 50) {
            if (intoxicationDamageTimer == null) {
                intoxicationDamageTimer = FXGL.getGameTimer().runAtInterval(() -> {
                    parameters.takeDamage(1);
                    if (parameters.getIntoxication().getCurrentValue() <= 50) {
                        intoxicationDamageTimer.pause();
                    }
                }, Duration.seconds(1));
            } else if (intoxicationDamageTimer.isPaused()) {
                intoxicationDamageTimer.resume();
            }
        }

        return true;
    }

    private int getIntoxication() {
        return this.intoxication;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public ItemType getType() {
        return ItemType.POTION;
    }
}
