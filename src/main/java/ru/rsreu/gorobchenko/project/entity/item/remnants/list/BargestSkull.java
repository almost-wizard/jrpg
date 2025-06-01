package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class BargestSkull extends Remnant {
    private static final int COST = 10;

    public BargestSkull() {
        super("assets/textures/remnants/bargest-skull.png", COST);
        setBigSpritePath("assets/textures/remnants/bargest-skull-big.png");
        setName("Череп Баргеста");
        setItemDescription("Считается, что баргесты — призраки, материализовавшиеся в призрачных псов и преследующие живых людей. По некоторым поверьям эти чудовища являются предзнаменованием ужасных бед. Другие же легенды гласят, что баргесты — воплощение божественной кары. Однако правда в том, что баргесты всегда безжалостны по отношению к живым существам.");
        setParametersDescription("Череп баргеста, пугающий своей устрашающей аурой. Легенды гласят, что каждый, кто прикоснётся к нему, чувствует приближение неминуемой смерти...\n\nИспугались? Просто продайте эту мерзость и не забивайте голову бабушкиными сказками");
    }

    @Override
    public Item constructCopy() {
        return new BargestSkull();
    }
}
