package ru.rsreu.gorobchenko.project.entity.inventory.type;

import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.Cell;
import ru.rsreu.gorobchenko.project.entity.inventory.CloseItemViewComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.weapon.NullWeapon;
import ru.rsreu.gorobchenko.project.entity.item.weapon.Weapon;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;

import java.util.List;

public class PlayerInventoryComponent extends BaseInventoryComponent {
    private boolean isCollapsed = true;
    private final int collapsedSize;

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
    }

    public PlayerInventoryComponent(int width, int height, int collapsedSize) {
        super(width, height, width * height);
        if (collapsedSize < 1 || collapsedSize >= width * height) {
            throw new IllegalArgumentException("Collapsed grid size must be between 1 and [width * height]!");
        }

        this.collapsedSize = collapsedSize;
        this.gridModel.setWeaponCell(0);
        setVisible(true);

        repaint();
        List<Cell> cells = this.gridModel.getCells();
        for (int i = 0; i < collapsedSize && i < cells.size(); i += 1) {
            cells.get(i).setKeyCodeHint(i + 1);
        }
    }

    @Override
    public void open() {
        this.setCollapsed(false);
    }
    @Override
    public void close() {
        this.setCollapsed(true);
    }
    @Override
    public boolean isActive() {
        return !this.isCollapsed;
    }

    private void setCollapsed(boolean collapsible) {
        boolean wasActive = isActive();
        isCollapsed = collapsible;
        if (!isActive()) {
            CloseItemViewComponent closeItemViewComponent = PlayerManager.getInstance().getCloseItemView();
            closeItemViewComponent.tryClose(this.getModel());
        }
        repaint();
        toggleFocus(wasActive);
    }

    private void repaint() {
        for (int i = collapsedSize; i < this.gridModel.getLayout().getChildren().size(); i += 1) {
            this.gridModel.getLayout().getChildren().get(i).setVisible(!isCollapsed);
        }
    }

    public Weapon getWeapon() {
        Cell cell = this.gridModel.getWeaponCell();
        if (cell == null) {
            return NullWeapon.getInstance();
        }

        Item item = cell.getItem();
        if (!(item instanceof Weapon)) {
            return NullWeapon.getInstance();
        }

        return (Weapon) item;
    }

    @Override
    public void setGoodsCostEnabled(boolean goodsCostEnabled) {
        super.setGoodsCostEnabled(goodsCostEnabled);

        if (goodsCostEnabled) {
            return;
        }
        List<Cell> cells = this.gridModel.getCells();
        for (int i = 0; i < collapsedSize && i < cells.size(); i += 1) {
            cells.get(i).setKeyCodeHint(i + 1);
            cells.get(i).setKeyCodeHintColor(TextColor.WHITE.getColor());
        }
    }
}
