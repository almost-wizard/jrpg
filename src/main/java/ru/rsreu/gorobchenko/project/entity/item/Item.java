package ru.rsreu.gorobchenko.project.entity.item;

import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;

public abstract class Item {
    private final String smallSpritePath;
    private String bigSpritePath;
    private final boolean isCollectable;

    private int count;
    private String name;
    private String itemDescription;
    private String parametersDescription;

    public Item(Item other) {
        this.smallSpritePath = other.smallSpritePath;
        this.bigSpritePath = other.bigSpritePath;
        this.isCollectable = other.isCollectable;
        this.count = other.count;
        this.name = other.name;
        this.itemDescription = other.itemDescription;
        this.parametersDescription = other.parametersDescription;
    }

    protected Item(String smallSpritePath, boolean isCollectable, int count) {
        this.smallSpritePath = smallSpritePath;
        this.bigSpritePath = "";
        this.isCollectable = isCollectable;
        this.count = count;
    }

    public String getSmallSpritePath() {
        return smallSpritePath;
    }

    public String getBigSpritePath() {
        if (bigSpritePath.isEmpty()) {
            return smallSpritePath;
        }
        return bigSpritePath;
    }

    public void setBigSpritePath(String bigSpritePath) {
        this.bigSpritePath = bigSpritePath;
    }

    public boolean isCollectable() {
        return isCollectable;
    }

    public boolean isTradable() {
        return this instanceof Tradable;
    }

    public boolean isCoin() {
        return this instanceof Coin;
    }

    public int getCount() {
        return count;
    }

    public Item setCount(int count) {
        this.count = count;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getParametersDescription() {
        return parametersDescription;
    }

    public void setParametersDescription(String parametersDescription) {
        this.parametersDescription = parametersDescription;
    }

    public boolean sameItem(Item item) {
        return item.getClass().getName().equals(this.getClass().getName()) && isCollectable();
    }

    public boolean apply(CharacterParametersComponent parameters) {
        return false;
    }

    public abstract Item constructCopy();

    public ItemType getType() {
        return null;
    }
}
