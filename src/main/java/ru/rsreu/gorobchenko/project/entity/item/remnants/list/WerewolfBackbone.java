package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class WerewolfBackbone extends Remnant {
    private static final int COST = 40;

    public WerewolfBackbone() {
        super("assets/textures/remnants/werewolf-backbone.png", COST);
        setBigSpritePath("assets/textures/remnants/werewolf-backbone-big.png");
        setName("Позвоночник Волколака");
        setItemDescription("\"Не так страшен волк, как его малюют. Зато волколак куда страшней!\"\nВолколак — это получеловек-полуволк. От обоих он берет самые худшие качества: людскую жестокость сочетает со звериной жаждой крови. Волколаками становятся по причине проклятия. Вернувшийся в человеческий облик не помнит своих поступков, инчае он бы сошел с ума.");
        setParametersDescription("Если вы из тех авантюристов, что предпочитают питаться салом из утопцев, запивая его водой из лужи, то останки волколака отлично подойдут вам в качестве закуски к очень крепкому.. чаю");
    }

    @Override
    public Item constructCopy() {
        return new WerewolfBackbone();
    }
}
