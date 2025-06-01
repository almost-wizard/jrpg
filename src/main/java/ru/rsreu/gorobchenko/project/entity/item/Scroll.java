package ru.rsreu.gorobchenko.project.entity.item;

public class Scroll extends Item {
    public Scroll(String title, String messageMain, String messageSign) {
        super("assets/textures/common/scroll-small.png", false, 1);
        setBigSpritePath("assets/textures/common/scroll.png");
        setName(title);
        setItemDescription(messageSign);
        setParametersDescription(messageMain);
    }

    @Override
    public Item constructCopy() {
        return new Scroll(this.getName(), this.getItemDescription(), this.getParametersDescription());
    }
}
