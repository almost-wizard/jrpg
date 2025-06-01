package ru.rsreu.gorobchenko.project.entity.character.npc;

import com.almasb.fxgl.entity.component.Component;
import ru.rsreu.gorobchenko.project.entity.inventory.type.TraderInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;

public class WitchComponent extends Component {
    private final TraderInventoryComponent inventoryComponent;

    public WitchComponent() {
        inventoryComponent = new TraderInventoryComponent(ItemType.POTION);
    }

    @Override
    public void onAdded() {
        entity.addComponent(new NpcComponent(NpcType.FEMALE));
        entity.addComponent(inventoryComponent);
    }
}
