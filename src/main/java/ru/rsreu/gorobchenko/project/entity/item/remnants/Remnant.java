package ru.rsreu.gorobchenko.project.entity.item.remnants;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;
import ru.rsreu.gorobchenko.project.entity.item.Tradable;
import ru.rsreu.gorobchenko.project.entity.item.remnants.list.*;

public abstract class Remnant extends Item implements Tradable {
    private final int cost;

    public Remnant(Item other) {
        super(other);
        if (! (other instanceof Remnant)) {
            throw new IllegalArgumentException("Can't construct Potion from other Item type!");
        }
        cost = ((Remnant)other).getCost();
    }

    protected Remnant(String smallSpritePath, int cost) {
        super(smallSpritePath, true, 1);
        this.cost = cost;
    }

    public static Remnant[] getBargestRemnants() {
        return new Remnant[]{new BargestBackbone(), new BargestSkull(), new BargestPaws()};
    }

    public static Remnant[] getGhostRemnants() {
        return new Remnant[]{new GhostEssence()};
    }

    public static Remnant[] getWerewolfRemnants() {
        return new Remnant[]{new WerewolfBackbone(), new WerewolfSkull(), new WerewolfPaws()};
    }

    public static Remnant[] getDemonRemnants() {
        return new Remnant[]{new DemonHorns(), new DemonParts(), new DemonSkull()};
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public ItemType getType() {
        return ItemType.REMNANT;
    }
}
