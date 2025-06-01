package ru.rsreu.gorobchenko.project.entity.item;

public class NullItem extends Item {
    private static final NullItem instance = new NullItem();

    public static NullItem getInstance() {
        return instance;
    }

    protected NullItem() {
        super("", false, 1);
    }

    @Override
    public Item constructCopy() {
        return this;
    }
}
