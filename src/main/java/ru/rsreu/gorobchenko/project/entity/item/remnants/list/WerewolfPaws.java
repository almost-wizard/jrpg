package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class WerewolfPaws extends Remnant {
    private static final int COST = 20;

    public WerewolfPaws() {
        super("assets/textures/remnants/werewolf-paws.png", COST);
        setBigSpritePath("assets/textures/remnants/werewolf-paws-big.png");
        setName("Лапы Волколака");
        setItemDescription("\"Не так страшен волк, как его малюют. Зато волколак куда страшней!\"\nВолколак — это получеловек-полуволк. От обоих он берет самые худшие качества: людскую жестокость сочетает со звериной жаждой крови. Волколаками становятся по причине проклятия. Вернувшийся в человеческий облик не помнит своих поступков, инчае он бы сошел с ума.");
        setParametersDescription("Самые обычные лапы самого необычного волколака. Их можно продать ведьме за копейки");
    }

    @Override
    public Item constructCopy() {
        return new WerewolfPaws();
    }
}
