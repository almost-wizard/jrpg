package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class DemonSkull extends Remnant {
    private static final int COST = 200;

    public DemonSkull() {
        super("assets/textures/remnants/demon-skull.png", COST);
        setBigSpritePath("assets/textures/remnants/demon-skull-big.png");
        setName("Череп Балрога");
        setItemDescription("\"Огненные демоны великанского роста. Тёмный Властелин склонил их себе на службу на заре сотворения мира. Воплощения огня, тьмы и страха, они были ужаснейшими из его слуг...\" - так считают ученые. Но волшебники, в особенности служители тайного огня и повелители пламени Анора, считают иначе: \"...багровый огонь не поможет тебе, пламя Удуна! Возвращайся во тьму! Ты НЕ ПРОЙДЕШЬ!\"");
        setParametersDescription("Этот череп мог бы стать отличным сосудом для пива. Главное - найти умелого мастера, который возьмется за такую работу. Или самому стать пьяным мастером");
    }

    @Override
    public Item constructCopy() {
        return new DemonSkull();
    }
}
