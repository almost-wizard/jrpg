package ru.rsreu.gorobchenko.project.entity.character.npc;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.StaticColliderComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.TraderInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;

public class BlacksmithComponent extends Component {
    private static final int SPRITE_SIZE = 32;
    private static final int FRAMES_PER_ROW = 8;
    private static final Duration ANIM_DURATION = Duration.seconds(1);

    private static final String SPRITE_SET_PATH = "characters/working-blacksmith.png";
    private static final int WIDTH = 20;
    private static final int HEIGHT = 14;

    private final AnimatedTexture texture;
    private final TraderInventoryComponent inventoryComponent;

    public BlacksmithComponent() {
        Image image = FXGL.image(SPRITE_SET_PATH);

        AnimationChannel channel = new AnimationChannel(image, FRAMES_PER_ROW, SPRITE_SIZE, SPRITE_SIZE, ANIM_DURATION, 0, FRAMES_PER_ROW - 1);

        texture = new AnimatedTexture(channel);
        texture.loop();

        inventoryComponent = new TraderInventoryComponent(ItemType.WEAPON);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        entity.addComponent(new StaticColliderComponent(WIDTH, HEIGHT));
        entity.addComponent(inventoryComponent);
    }
}
    