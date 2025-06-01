package ru.rsreu.gorobchenko.project.entity.item;

public class Coin extends Item {
    public Coin() {
        this(1);
    }

    public Coin(int count) {
        super("/assets/textures/common/coins.png", true, count);
        setName("Деньги");
        setItemDescription("Ничего примечательного, стандартная единица размена");
        setParametersDescription("- Если на Новый Год под подушку положить тысячу рублей, в миллион превратятся?\n- Нет. Надо их закопать на Поле Чудес в Стране Дураков.");
    }

    public Coin(Item item) {
        super(item);
    }

    @Override
    public ItemType getType() {
        return ItemType.COIN;
    }

    @Override
    public Item constructCopy() {
        return new Coin(this);
    }
}
