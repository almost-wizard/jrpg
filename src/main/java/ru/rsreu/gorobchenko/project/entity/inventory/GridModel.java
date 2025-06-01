package ru.rsreu.gorobchenko.project.entity.inventory;

import javafx.scene.layout.GridPane;
import ru.rsreu.gorobchenko.project.entity.item.Coin;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.NullItem;

import java.util.List;

public class GridModel {
    private final int width;
    private final int height;
    private final int maxDrawingCellsCount;

    private final GridPane layout;

    private Cell selectedCell;
    private Cell weaponCell;

    private int currentItemsCount = 0;

    private boolean goodsCostEnabled;

    public GridModel(int width, int height, int maxDrawingCellsCount) {
        this.width = width;
        this.height = height;
        this.maxDrawingCellsCount = maxDrawingCellsCount;

        this.layout = new GridPane();

        int currentMaxRows = getCurrentMaxRows();
        for (int y = 0; y < currentMaxRows; y += 1) {
            for (int x = 0; x < getWidth(); x += 1) {
                Cell cell = new Cell(NullItem.getInstance(), this);
                this.layout.add(cell, x, y);
            }
        }
    }

    public void resetItems(Item... items) {
        resetItems(List.of(items));
    }

    public void resetItems(List<Item> items) {
        List<Cell> cells = getCells();
        for (int i = 0; i < cells.size() && i < items.size(); i += 1) {
            Cell cell = cells.get(i);
            if (!cell.getItem().equals(items.get(i))) {
                cell.updateItem(items.get(i));
            }
        }
        updateItemsCount();
    }

    public boolean add(Item item) {
        List<Cell> cells = this.getCells();
        for (Cell cell : cells) {
            if (cell.getItem().sameItem(item) && cell.canUpdate(item)) {
                cell.addToCell(cell, item);
                return true;
            }
        }
        for (Cell cell : cells) {
            if (cell.getItem().equals(NullItem.getInstance()) && cell.canUpdate(item)) {
                cell.updateItem(item);
                return true;
            }
        }
        return false;
    }

    public boolean add(Item item, int count) {
        List<Cell> cells = this.getCells();
        for (Cell cell : cells) {
            if (cell.getItem().sameItem(item) && cell.canUpdate(item)) {
                cell.addToCell(cell, item, count);
                return true;
            }
        }
        for (Cell cell : cells) {
            if (cell.getItem().equals(NullItem.getInstance()) && cell.canUpdate(item)) {
                cell.updateItem(item, count);
                return true;
            }
        }
        return false;
    }

    public int getCurrentMaxRows() {
        int maxCells = getMaxDrawingCellsCount();
        if (maxCells == -1) {
            return getHeight();
        }
        return (maxCells / getWidth()) + (maxCells % getWidth() == 0 ? 0 : 1);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMaxDrawingCellsCount() {
        return maxDrawingCellsCount;
    }

    public GridPane getLayout() {
        return layout;
    }

    public List<Cell> getCells() {
        return getLayout().getChildren().stream()
                .filter(node -> node instanceof Cell)
                .map(node -> (Cell) node)
                .toList();
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public boolean isWeaponCell(Cell cell) {
        return getWeaponCell() == cell;
    }

    public Cell getWeaponCell() {
        return weaponCell;
    }

    public void setWeaponCell(int linearIndex) {
        List<Cell> cells = getCells();
        if (linearIndex < 0 || linearIndex >= cells.size()) {
            return;
        }

        this.weaponCell = cells.get(linearIndex);
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    public void updateItemsCount() {
        currentItemsCount = 0;
        List<Cell> cells = getCells();
        for (Cell cell : cells) {
            if (!cell.getItem().equals(NullItem.getInstance())) {
                currentItemsCount += 1;
            }
        }
    }

    public int getItemsCount() {
        return currentItemsCount;
    }

    public boolean hasFreeSpace(Item item) {
        List<Cell> cells = getCells();
        for (Cell cell : cells) {
            if (cell.getItem().sameItem(item) || cell.getItem() == NullItem.getInstance()) {
                return true;
            }
        }
        return false;
    }

    public void removeCoins(int count) {
        List<Cell> cells = getCells();
        for (Cell cell : cells) {
            if (cell.getItem().isCoin()) {
                count = cell.removeFromItem(count);
                if (count <= 0) {
                    return;
                }
            }
        }
    }

    public void addCoins(int cost) {
        this.add(new Coin(), cost);
    }

    public boolean isGoodsCostEnabled() {
        return goodsCostEnabled;
    }

    public void setGoodsCostEnabled(boolean goodsCostEnabled) {
        this.goodsCostEnabled = goodsCostEnabled;
    }
}
