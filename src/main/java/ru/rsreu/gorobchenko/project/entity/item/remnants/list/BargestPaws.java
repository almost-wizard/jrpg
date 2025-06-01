package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class BargestPaws extends Remnant {
    private static final int COST = 5;

    public BargestPaws() {
        super("assets/textures/remnants/bargest-paws.png", COST);
        setBigSpritePath("assets/textures/remnants/bargest-paws-big.png");
        setName("Лапы Баргеста");
        setItemDescription("Считается, что баргесты — призраки, материализовавшиеся в призрачных псов и преследующие живых людей. По некоторым поверьям эти чудовища являются предзнаменованием ужасных бед. Другие же легенды гласят, что баргесты — воплощение божественной кары. Однако правда в том, что баргесты всегда безжалостны по отношению к живым существам.");
        setParametersDescription("Этому баргесту еще никогда не было так хорошо. Ведь сейчас он спит как без задних ног! (⌒‿⌒)");
    }

    @Override
    public Item constructCopy() {
        return new BargestPaws();
    }
}
