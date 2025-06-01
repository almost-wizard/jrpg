package ru.rsreu.gorobchenko.project.entity.inventory;

import ru.rsreu.gorobchenko.project.entity.inventory.type.BaseInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.MinableInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.type.PlayerInventoryComponent;

import java.util.ArrayList;
import java.util.List;

public class InventoryManager {
    private static final InventoryManager instance = new InventoryManager();

    private final List<BaseInventoryComponent> inventoryComponentsOnScene = new ArrayList<>();

    private InventoryManager() {

    }

    public void addInventoryOnScene(final BaseInventoryComponent inventoryComponent) {
        inventoryComponentsOnScene.add(inventoryComponent);
    }

    public void removeInventoryFromScene(final BaseInventoryComponent inventoryComponent) {
        inventoryComponentsOnScene.remove(inventoryComponent);
    }

    public List<BaseInventoryComponent> getInventoriesOnScene() {
        return inventoryComponentsOnScene;
    }

    public List<BaseInventoryComponent> getInventoriesOnSceneExcludePlayer() {
        BaseInventoryComponent playerInventory = PlayerManager.getInstance().getInventory();
        return inventoryComponentsOnScene.stream()
                .filter(i -> i != playerInventory)
                .toList();
    }

    public List<MinableInventoryComponent> getMinableInventories() {
        return inventoryComponentsOnScene.stream()
                .filter(i -> i instanceof MinableInventoryComponent)
                .map(i -> (MinableInventoryComponent)i)
                .toList();
    }

    public List<BaseInventoryComponent> getActiveInventories() {
        return inventoryComponentsOnScene.stream()
                .filter(BaseInventoryComponent::isActive)
                .toList();
    }

    public void closeAllInventories() {
        List<BaseInventoryComponent> inventories = getInventoriesOnScene();
        for (BaseInventoryComponent inventoryComponent : inventories) {
            if (inventoryComponent instanceof PlayerInventoryComponent) {
                inventoryComponent.focus();
                inventoryComponent.open();
                inventoryComponent.close();
                inventoryComponent.unFocus();
            }
        }
    }

    public static InventoryManager getInstance() {
        return instance;
    }
}
