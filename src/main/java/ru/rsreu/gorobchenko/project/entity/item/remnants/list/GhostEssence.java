package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class GhostEssence extends Remnant {
    private static final int COST = 30;

    public GhostEssence() {
        super("assets/textures/remnants/ghost-essence.png", COST);
        setBigSpritePath("assets/textures/remnants/ghost-essence-big.png");
        setName("Эссенция призрака");
        setItemDescription("Говорят, призраки появляются, когда смерть не избавляет умершего от страданий. Тот, кто привлечет к себе внимание морока, немедля перестанет мечтать о смерти. Поэтому знайте, что если вы стали жертвой призрака, то все таки лучшим решением было вернуть усопшему другу все долги, а не прощать ему их");
        setParametersDescription("Эссенция призрака - находка для любой ведьмы, которая хоть чуть-чуть умеет готовить поваренную книгу");
    }

    @Override
    public Item constructCopy() {
        return new GhostEssence();
    }
}
