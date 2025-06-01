package ru.rsreu.gorobchenko.project.entity.inventory.type;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.Cell;
import ru.rsreu.gorobchenko.project.entity.inventory.CloseItemViewComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.GridModel;
import ru.rsreu.gorobchenko.project.entity.inventory.InventoryManager;
import ru.rsreu.gorobchenko.project.entity.item.Tradable;
import ru.rsreu.gorobchenko.project.ui.text.type.TextColor;

import java.util.List;

public class BaseInventoryComponent extends Component {
    private static final int CELL_INSET_SIZE = 5;

    protected final GridModel gridModel;

    protected BaseInventoryComponent(int gridWidth, int gridHeight, int maxDrawingCellsCount) {
        this.gridModel = new GridModel(gridWidth, gridHeight, maxDrawingCellsCount);

        this.gridModel.getLayout().setViewOrder(-12313);
        this.gridModel.getLayout().setHgap(CELL_INSET_SIZE);
        this.gridModel.getLayout().setVgap(CELL_INSET_SIZE);
        this.gridModel.getLayout().setPadding(new Insets(CELL_INSET_SIZE, CELL_INSET_SIZE, CELL_INSET_SIZE, CELL_INSET_SIZE));
        this.gridModel.getLayout().setVisible(false);

        int displaySize = Cell.SIZE + CELL_INSET_SIZE;
        this.gridModel.getLayout().setPrefWidth(gridWidth * displaySize);
        this.gridModel.getLayout().setPrefHeight(gridHeight * displaySize);
    }

    @Override
    public void onAdded() {
        FXGL.getGameScene().addChild(this.gridModel.getLayout());
        InventoryManager.getInstance().addInventoryOnScene(this);
    }

    @Override
    public void onRemoved() {
        FXGL.getGameScene().removeChild(this.gridModel.getLayout());
        InventoryManager.getInstance().removeInventoryFromScene(this);
    }

    public GridModel getModel() {
        return this.gridModel;
    }

    public GridPane getView() {
        return this.gridModel.getLayout();
    }

    public void open() {
        this.setVisible(true);
    }

    public void close() {
        CloseItemViewComponent closeItemViewComponent = PlayerManager.getInstance().getCloseItemView();
        closeItemViewComponent.tryClose(this.getModel());

        this.setVisible(false);
    }

    public void setActive(boolean isActive) {
        if (isActive) {
            open();
        } else {
            close();
        }
    }

    public void toggle() {
        if (isActive()) {
            close();
        } else {
            open();
        }
    }

    public boolean isActive() {
        return this.getView().isVisible();
    }

    public void focus() {
        if (!isFocused()) {
            this.gridModel.getLayout().getChildren().getFirst().requestFocus();
        }
    }

    public void unFocus() {
        this.gridModel.getLayout().requestFocus();
    }

    public void toggleFocus(boolean wasActive) {
        if (isActive()) {
            if (!wasActive) {
                this.focus();
            }
        } else {
            this.unFocus();
            List<BaseInventoryComponent> activeInventories = InventoryManager.getInstance().getActiveInventories();
            if (activeInventories.isEmpty()) {
                return;
            }
            BaseInventoryComponent inventory = activeInventories.getFirst();
            if (!inventory.isFocused()) {
                inventory.focus();
            }
        }
    }

    public boolean isFocused() {
        List<Cell> cells = this.getModel().getCells();
        for (Cell cell : cells) {
            if (cell.isButtonFocused()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisible() {
        return this.gridModel.getLayout().isVisible();
    }

    public void setVisible(boolean visible) {
        boolean wasActive = this.isActive();
        this.gridModel.getLayout().setVisible(visible);
        this.toggleFocus(wasActive);
    }

    public boolean isTradable() {
        return this instanceof TraderInventoryComponent;
    }

    public int getCoinsCount() {
        List<Cell> cells = this.getModel().getCells();
        int result = 0;
        for (Cell cell : cells) {
            if (cell.getItem().isCoin()) {
                result += cell.getItem().getCount();
            }
        }
        return result;
    }

    public void setGoodsCostEnabled(boolean goodsCostEnabled) {
        List<Cell> cells = this.getModel().getCells();
        for (Cell cell : cells) {
            if (cell.getItem() instanceof Tradable good && goodsCostEnabled) {
                cell.setKeyCodeHint(good.getCost());
                cell.setKeyCodeHintColor(TextColor.PRIMARY_YELLOW.getColor());
            } else {
                cell.resetKeyCodeHint();
            }
        }
        this.getModel().setGoodsCostEnabled(goodsCostEnabled);
    }
}
