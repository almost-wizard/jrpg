package ru.rsreu.gorobchenko.project.entity.inventory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.NullItem;
import ru.rsreu.gorobchenko.project.ui.button.ItemCloseViewButton;
import ru.rsreu.gorobchenko.project.ui.text.type.TextSize;
import ru.rsreu.gorobchenko.project.ui.text.type.TextType;

public class CloseItemViewComponent extends Component {
    private static final int SPRITE_VIEW_CELL_SIZE = FXGL.getAppHeight() / 3;
    private static final int NAME_VIEW_CELL_HEIGHT = FXGL.getAppHeight() / 12;
    private static final int DESCRIPTION_VIEW_CELL_HEIGHT = FXGL.getAppHeight() / 4;

    private static final int PANE_WIDTH = FXGL.getAppHeight();
    private static final int PANE_HEIGHT = NAME_VIEW_CELL_HEIGHT + 5 + SPRITE_VIEW_CELL_SIZE + 5 + DESCRIPTION_VIEW_CELL_HEIGHT;

    private final Pane viewPane = new Pane();
    private final ItemCloseViewButton itemNameCell;
    private final ItemCloseViewButton itemSpriteCell;
    private final ItemCloseViewButton itemDescriptionCell;
    private final ItemCloseViewButton itemParametersCell;

    private Item item;
    private GridModel openedInventory;

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
    }

    public CloseItemViewComponent() {
        this.item = NullItem.getInstance();

        this.itemNameCell = new ItemCloseViewButton(PANE_WIDTH, NAME_VIEW_CELL_HEIGHT);
        this.itemNameCell.setAlignment(Pos.CENTER);
        this.itemNameCell.setFont(new Font(TextType.INK_FREE.getFontName(), TextSize.M.getTextSize()));

        this.itemSpriteCell = new ItemCloseViewButton(SPRITE_VIEW_CELL_SIZE, SPRITE_VIEW_CELL_SIZE);
        this.itemSpriteCell.setLayoutY(NAME_VIEW_CELL_HEIGHT + 5);

        this.itemParametersCell = new ItemCloseViewButton(PANE_WIDTH - SPRITE_VIEW_CELL_SIZE - 5, SPRITE_VIEW_CELL_SIZE);
        this.itemParametersCell.setLayoutX(SPRITE_VIEW_CELL_SIZE + 5);
        this.itemParametersCell.setLayoutY(NAME_VIEW_CELL_HEIGHT + 5);

        this.itemDescriptionCell = new ItemCloseViewButton(PANE_WIDTH, DESCRIPTION_VIEW_CELL_HEIGHT);
        this.itemDescriptionCell.setLayoutY(NAME_VIEW_CELL_HEIGHT + 5 + SPRITE_VIEW_CELL_SIZE + 5);

        this.viewPane.getChildren().addAll(this.itemNameCell, this.itemSpriteCell, this.itemDescriptionCell, this.itemParametersCell);

        this.viewPane.setPrefWidth(PANE_WIDTH);
        this.viewPane.setPrefHeight(PANE_HEIGHT);
        this.viewPane.setLayoutX((FXGL.getAppWidth() - this.viewPane.getPrefWidth()) / 2);
        this.viewPane.setLayoutY((FXGL.getAppHeight() - this.viewPane.getPrefHeight()) / 2);

        this.setVisible(false);
    }

    @Override
    public void onAdded() {
        FXGL.getGameScene().addChild(this.viewPane);
    }

    @Override
    public void onRemoved() {
        FXGL.getGameScene().removeChild(this.viewPane);
    }

    public void open(GridModel openedInventory) {
        this.openedInventory = openedInventory;
        setVisible(true);
    }

    public void tryClose(GridModel inventory) {
        if (inventory == this.openedInventory) {
            setVisible(false);
        }
    }

    public void toggle() {
        setVisible(!getVisible());
    }

    public boolean getVisible() {
        return this.viewPane.isVisible();
    }

    public void setVisible(boolean visible) {
        this.viewPane.setVisible(visible);
    }

    public void setItem(Cell cell) {
        Item item = cell.getItem();
        this.item = item;
        ItemCloseViewButton.bindBackground(this.itemSpriteCell, item.getBigSpritePath());
        this.itemNameCell.setText(item.getName());
        this.itemDescriptionCell.setText(this.item.getItemDescription());
        this.itemParametersCell.setText(this.item.getParametersDescription());
        this.openedInventory = cell.getParentInventory();
    }

    public Item getItem() {
        return this.item;
    }
}
