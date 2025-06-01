package ru.rsreu.gorobchenko.project.entity.inventory.type;

import com.almasb.fxgl.core.math.FXGLMath;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.Cell;
import ru.rsreu.gorobchenko.project.entity.inventory.GridModel;
import ru.rsreu.gorobchenko.project.entity.item.Coin;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.ItemType;

import java.util.List;

public class TraderInventoryComponent extends MinableInventoryComponent {
    private static final int TRADER_INVENTORY_WIDTH = 5;
    private static final int TRADER_INVENTORY_HEIGHT = 4;

    private final ItemType itemType;

    public TraderInventoryComponent(ItemType itemType) {
        super(TRADER_INVENTORY_WIDTH, TRADER_INVENTORY_HEIGHT);

        this.itemType = itemType;
        this.updateGoods();
        this.setGoodsCostEnabled(true);
    }

    @Override
    public void close() {
        super.close();
        PlayerManager.getInstance().getInventory().setGoodsCostEnabled(false);
    }

    public void updateGoods() {
        GridModel model = this.getModel();
        List<Item> items = new java.util.ArrayList<>(itemType.getAllTradable().stream().toList());
        Coin coins = new Coin();
        items.add(coins);
        model.resetItems(items);

        List<Cell> goods = model.getCells();
        for (Cell cell : goods) {
            if (cell.getItem().isCollectable()) {
                cell.getItem().setCount(FXGLMath.random(1, 10));
                cell.updateCountTextView();
            }
        }

        coins.setCount(5000);
        for (Cell cell : goods) {
            cell.updateCountTextView();
        }
    }
}
