package ru.rsreu.gorobchenko.project.entity.item.remnants.list;

import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.remnants.Remnant;

public class BargestBackbone extends Remnant {
    private static final int COST = 7;

    public BargestBackbone() {
        super("assets/textures/remnants/bargest-backbone.png", COST);
        setBigSpritePath("assets/textures/remnants/bargest-backbone-big.png");
        setName("Позвоночник Баргеста");
        setItemDescription("Считается, что баргесты — призраки, материализовавшиеся в призрачных псов и преследующие живых людей. По некоторым поверьям эти чудовища являются предзнаменованием ужасных бед. Другие же легенды гласят, что баргесты — воплощение божественной кары. Однако правда в том, что баргесты всегда безжалостны по отношению к живым существам.");
        setParametersDescription("Позвоночник баргеста? Серьезно? А я всегда думал, что это призраки... Во всяком случае, я надеюсь, что смогу это кому-нибудь продать");
    }

    @Override
    public Item constructCopy() {
        return new BargestBackbone();
    }
}
