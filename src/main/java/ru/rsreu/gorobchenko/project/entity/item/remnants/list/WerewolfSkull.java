package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class WerewolfSkull extends Remnant {
    private static final int COST = 50;

    public WerewolfSkull() {
        super("assets/textures/remnants/werewolf-skull.png", COST);
        setBigSpritePath("assets/textures/remnants/werewolf-skull-big.png");
        setName("Череп Волколака");
        setItemDescription("\"Не так страшен волк, как его малюют. Зато волколак куда страшней!\"\nВолколак — это получеловек-полуволк. От обоих он берет самые худшие качества: людскую жестокость сочетает со звериной жаждой крови. Волколаками становятся по причине проклятия. Вернувшийся в человеческий облик не помнит своих поступков, инчае он бы сошел с ума.");
        setParametersDescription("Нет, делать пивной череп из Волколака - это кощунство, это хуже мародерства. Поэтому прежде, чем начать - найдите место, где вас никто не заметит");
    }

    @Override
    public Item constructCopy() {
        return new WerewolfSkull();
    }
}
