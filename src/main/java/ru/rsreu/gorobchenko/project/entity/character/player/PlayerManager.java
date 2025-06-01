package ru.rsreu.gorobchenko.project.entity.character.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.CloseItemViewComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.PlayerInventoryComponent;
import ru.rsreu.gorobchenko.project.factory.EntityType;

public class PlayerManager {
    private static PlayerManager instance = new PlayerManager();

    private final Entity player;

    private PlayerManager() {
        this.player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
    }

    public Entity getPlayer() {
        return player;
    }

    public PlayerComponent getPlayerComponent() {
        return player.getComponent(PlayerComponent.class);
    }

    public PlayerParametersComponent getParameters() {
        return player.getComponent(PlayerParametersComponent.class);
    }

    public CloseItemViewComponent getCloseItemView() {
        return player.getComponent(CloseItemViewComponent.class);
    }

    public PlayerInventoryComponent getInventory() {
        if (player.hasComponent(PlayerInventoryComponent.class)) {
            return player.getComponent(PlayerInventoryComponent.class);
        }
        return null;
    }

    public static PlayerManager getInstance() {
        return instance;
    }

    public void reset() {
        instance = new PlayerManager();
    }
}
