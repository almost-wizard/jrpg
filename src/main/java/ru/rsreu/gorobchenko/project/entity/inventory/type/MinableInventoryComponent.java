package ru.rsreu.gorobchenko.project.entity.inventory.type;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;
import ru.rsreu.gorobchenko.project.ui.button.ButtonFactory;
import ru.rsreu.gorobchenko.project.ui.button.CustomButton;

public class MinableInventoryComponent extends BaseInventoryComponent {
    public static final double INTERACTION_DISTANCE = 50;

    private boolean enabled = true;
    private Entity player;
    private PlayerInventoryComponent playerInventory;
    private CustomButton openHint;

    public MinableInventoryComponent(int width, int height) {
        super(width, height, width * height);

        this.getView().setLayoutY(FXGL.getAppHeight() / 2.0);
    }

    @Override
    public void onAdded() {
        super.onAdded();

        openHint = ButtonFactory.createKeyHintButton(KeyCode.O);
        setHintTranslate(openHint);
        openHint.setVisible(false);
        entity.getViewComponent().addChild(openHint);
    }

    private void setHintTranslate(CustomButton openHint) {
        openHint.setTranslateY(- openHint.getPrefHeight() - 2);

        int width;
        if (entity.getProperties().exists("width")) {
            width = entity.getProperties().<Integer>getValue("width");
        } else {
            width = (int)Math.round(EntityUtils.getViewSize(entity).getX());
        }
        openHint.setTranslateX((width - openHint.getPrefWidth()) / 2);
    }

    @Override
    public void onUpdate(double tpf) {
        if (!isEnabled()) {
            return;
        }

        if (player == null) {
            player = PlayerManager.getInstance().getPlayer();
        }

        if (playerInventory == null) {
            playerInventory = PlayerManager.getInstance().getInventory();
        }

        double distance = entity.getAnchoredPosition().distance(player.getAnchoredPosition());
        openHint.setVisible(distance <= INTERACTION_DISTANCE);
        if (distance > INTERACTION_DISTANCE && isActive()) {
            close();
        }
    }

    @Override
    public void toggle() {
        if (!isEnabled()) {
            return;
        }
        super.toggle();
        playerInventory.setActive(isActive());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
