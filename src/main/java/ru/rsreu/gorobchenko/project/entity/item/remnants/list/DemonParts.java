package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class DemonParts extends Remnant {
    private static final int COST = 100;

    public DemonParts() {
        super("assets/textures/remnants/demon-parts.png", COST);
        setBigSpritePath("assets/textures/remnants/demon-parts-big.png");
        setName("Останки Балрога");
        setItemDescription("\"Огненные демоны великанского роста. Тёмный Властелин склонил их себе на службу на заре сотворения мира. Воплощения огня, тьмы и страха, они были ужаснейшими из его слуг...\" - так считают ученые. Но волшебники, в особенности служители тайного огня и повелители пламени Анора, считают иначе: \"...багровый огонь не поможет тебе, пламя Удуна! Возвращайся во тьму! Ты НЕ ПРОЙДЕШЬ!\"");
        setParametersDescription("\"Только пепел знает, что значит сгореть дотла.\" - И.А. Бродский");
    }

    @Override
    public Item constructCopy() {
        return new DemonParts();
    }
}
