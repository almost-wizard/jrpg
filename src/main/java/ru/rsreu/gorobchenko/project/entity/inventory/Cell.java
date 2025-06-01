package ru.rsreu.gorobchenko.project.entity.inventory;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.type.BaseInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.PlayerInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.NullItem;
import ru.rsreu.gorobchenko.project.entity.item.Tradable;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;
import ru.rsreu.gorobchenko.project.ui.button.InventoryCellButton;
import ru.rsreu.gorobchenko.project.ui.text.TextFactory;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

import java.util.List;

public class Cell extends Pane {
    public static final int SIZE = 50;

    protected final GridModel gridModel;
    private final Text keyCodeHint;

    private Item item;
    private final InventoryCellButton button;

    protected Cell(Item item, GridModel gridModel) {
        this.button = new InventoryCellButton(item, SIZE);

        this.item = item;
        this.gridModel = gridModel;

        this.button.setText(InventoryCellButton.constructText(item));
        InventoryCellButton.bindBackground(this.button, item.getSmallSpritePath());

        this.keyCodeHint = TextFactory.create("", TextType.GOTHIC, TextColor.WHITE, TextSize.S);
        this.keyCodeHint.setLayoutX(5);
        this.keyCodeHint.setLayoutY(SIZE - 8);
        this.getChildren().addAll(this.button, keyCodeHint);
    }

    @Override
    public void requestFocus() {
        this.button.requestFocus();
    }

    public boolean isButtonFocused() {
        return button.isFocused();
    }

    public void setKeyCodeHint(int i) {
        this.keyCodeHint.setText(Integer.toString(i));
    }

    public void resetKeyCodeHint() {
        this.keyCodeHint.setText("");
    }

    public void setKeyCodeHintColor(Color color) {
        this.keyCodeHint.setFill(color);
    }

    public void updateCountTextView() {
        this.button.setText(InventoryCellButton.constructText(this.getItem()));
    }

    public boolean canUpdate(Item item) {
        return !this.gridModel.isWeaponCell(this) || item instanceof Weapon;
    }

    private void updateItem() {
        if (item.getCount() <= 0) {
            this.updateItem(NullItem.getInstance());
        } else {
            this.updateItem(item);
        }
    }

    public void updateItem(Item item) {
        this.item = item;
        this.button.setText(InventoryCellButton.constructText(item));
        InventoryCellButton.bindBackground(this.button, item.getSmallSpritePath());

        if (item instanceof Tradable good && this.gridModel.isGoodsCostEnabled()) {
            this.setKeyCodeHint(good.getCost());
            this.setKeyCodeHintColor(TextColor.PRIMARY_YELLOW.getColor());
        } else if (item == NullItem.getInstance()) {
            this.resetKeyCodeHint();
        }
    }

    public void updateItem(Item item, int count) {
        item = item.constructCopy();
        item.setCount(count);
        updateItem(item);
    }

    public void removeOneItem() {
        this.item.setCount(this.item.getCount() - 1);
        if (this.item.getCount() == 0) {
            this.updateItem(NullItem.getInstance());
            this.gridModel.updateItemsCount();
        } else {
            this.updateCountTextView();
        }
    }

    public int removeFromItem(int count) {
        int remains = this.item.getCount() - count;
        if (remains <= 0) {
            this.updateItem(NullItem.getInstance());
            this.gridModel.updateItemsCount();
            return Math.abs(remains);
        }

        this.item.setCount(remains);
        this.updateCountTextView();
        return 0;
    }

    public void addToCell(Cell target, Cell addon) {
        addToCell(target, addon.getItem());
        addon.updateItem(NullItem.getInstance());
    }

    public void addToCell(Cell target, Item item) {
        if (!target.getItem().sameItem(item)) {
            return;
        }
        target.getItem().setCount(target.getItem().getCount() + item.getCount());
        target.updateCountTextView();
        this.gridModel.updateItemsCount();
    }

    public void addToCell(Cell target, Item item, int count) {
        if (!target.getItem().sameItem(item)) {
            return;
        }
        target.getItem().setCount(target.getItem().getCount() + count);
        target.updateCountTextView();
        this.gridModel.updateItemsCount();
    }

    public void swapCells(Cell cell1, Cell cell2) {
        Item item1 = cell1.getItem();
        Item item2 = cell2.getItem();
        if (!cell1.canUpdate(item2) || !cell2.canUpdate(item1)) {
            return;
        }
        cell1.updateItem(item2);
        cell2.updateItem(item1);
    }

    public void processClick() {
        Cell clickedCell = this;

        if (gridModel.getSelectedCell() == null) {
            gridModel.setSelectedCell(clickedCell);
            this.button.setSelected(true);
        } else {
            if (gridModel.getSelectedCell() != clickedCell) {
                if (gridModel.getSelectedCell().getItem().sameItem(clickedCell.getItem())) {
                    addToCell(clickedCell, gridModel.getSelectedCell());
                    this.gridModel.updateItemsCount();
                } else {
                    swapCells(gridModel.getSelectedCell(), clickedCell);
                }
            }
            gridModel.getSelectedCell().button.setSelected(false);
            gridModel.setSelectedCell(null);
        }
    }

    public void processReplace(boolean all) {
        InventoryManager manager = InventoryManager.getInstance();

        List<BaseInventoryComponent> activeInventories = manager.getActiveInventories();
        if (activeInventories.size() != 2) {
            return;
        }

        BaseInventoryComponent sender = null;
        BaseInventoryComponent receiver = null;
        for (BaseInventoryComponent component : activeInventories) {
            if (component.getModel() == this.gridModel) {
                sender = component;
            }
            if (component.getModel() != this.gridModel) {
                receiver = component;
            }
        }

        if (sender == null || receiver == null) {
            return;
        }

        if (sender.isTradable() || receiver.isTradable()) {
            if (!buy(sender, receiver, all)) {
                return;
            }
        }

        if (all) {
            if (!receiver.getModel().add(this.getItem())) {
                return;
            }
            this.updateItem(NullItem.getInstance());
        } else {
            if (!receiver.getModel().add(this.getItem(), 1)) {
                return;
            }
            this.getItem().setCount(this.getItem().getCount() - 1);
            this.updateItem();
        }
        sender.getModel().updateItemsCount();
        receiver.getModel().updateItemsCount();
    }

    private boolean buy(BaseInventoryComponent seller, BaseInventoryComponent buyer, boolean all) {
        if (!this.getItem().isTradable()) {
            return false;
        }

        Tradable good = (Tradable) this.getItem();
        int buyerCoins = buyer.getCoinsCount();

        int totalCost = good.getCost();
        if (all) {
            totalCost *= this.getItem().getCount();
        }

        if (totalCost > buyerCoins) {
            return false;
        }

        if (!buyer.getModel().hasFreeSpace(this.getItem())) {
            return false;
        }

        buyer.getModel().removeCoins(totalCost);
        seller.getModel().addCoins(totalCost);
        return true;
    }

    public void processApply() {
        PlayerInventoryComponent playerInventory = PlayerManager.getInstance().getInventory();
        if (playerInventory.getModel() != this.gridModel) {
            return;
        }
        if (getItem().apply(PlayerManager.getInstance().getParameters())) {
            removeOneItem();
        }
    }

    public void processCloseView() {
        CloseItemViewComponent closeItemViewComponent = PlayerManager.getInstance().getCloseItemView();
        if (!closeItemViewComponent.getVisible() && getItem() != NullItem.getInstance()) {
            closeItemViewComponent.setItem(getCell());
            closeItemViewComponent.open(gridModel);
        } else {
            if (getItem() == NullItem.getInstance() || getItem() == closeItemViewComponent.getItem()) {
                closeItemViewComponent.tryClose(gridModel);
            } else {
                closeItemViewComponent.setItem(getCell());
            }
        }
    }

    public GridModel getParentInventory() {
        return this.gridModel;
    }

    public Item getItem() {
        return item;
    }

    protected Cell getCell() {
        return this;
    }
}
