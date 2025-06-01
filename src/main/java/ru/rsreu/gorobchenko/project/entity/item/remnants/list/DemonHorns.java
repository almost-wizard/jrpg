package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class DemonHorns extends Remnant {
    private static final int COST = 150;

    public DemonHorns() {
        super("assets/textures/remnants/demon-horns.png", COST);
        setBigSpritePath("assets/textures/remnants/demon-horns-big.png");
        setName("Рога Балрога");
        setItemDescription("\"Огненные демоны великанского роста. Тёмный Властелин склонил их себе на службу на заре сотворения мира. Воплощения огня, тьмы и страха, они были ужаснейшими из его слуг...\" - так считают ученые. Но волшебники, в особенности служители тайного огня и повелители пламени Анора, считают иначе: \"...багровый огонь не поможет тебе, пламя Удуна! Возвращайся во тьму! Ты НЕ ПРОЙДЕШЬ!\"");
        setParametersDescription("— Клевые ушки!\n— Это рога.");
    }

    @Override
    public Item constructCopy() {
        return new DemonHorns();
    }
}
