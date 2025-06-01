package ru.rsreu.gorobchenko.project.components.character.ui;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;
import ru.rsreu.gorobchenko.project.ui.ProgressBar;
import ru.rsreu.gorobchenko.project.ui.button.ButtonFactory;
import ru.rsreu.gorobchenko.project.ui.button.InventoryCellButton;

import java.util.List;

public class PlayerParametersUIComponent extends Component {
    private Rectangle staminaBar;
    private Rectangle healthBar;
    private Rectangle intoxicationBar;

    private Pane appliedPotionsPane;

    private double lastStaminaPercent;
    private double lastHealthPercent;
    private double lastIntoxicationPercent;

    private PlayerParametersComponent parameters;

    @Override
    public void onAdded() {
        staminaBar = ProgressBar.STAMINA.constructBar();
        Rectangle staminaBarBg = ProgressBar.STAMINA.constructBg();
        lastStaminaPercent = parameters.getStamina().getPercent();

        double healthY = ProgressBar.STAMINA.getSize().getHeight() + 5;
        healthBar = ProgressBar.PLAYER_HEALTH.constructBar();
        healthBar.setY(healthY);
        Rectangle healthBarBg = ProgressBar.PLAYER_HEALTH.constructBg();
        healthBarBg.setY(healthY);
        lastHealthPercent = parameters.getHealth().getPercent();

        double intoxicationX = ProgressBar.PLAYER_HEALTH.getSize().getWidth() / 2.0;
        double intoxicationY = ProgressBar.STAMINA.getSize().getHeight() + 5 + ProgressBar.PLAYER_HEALTH.getSize().getHeight() + 5;
        intoxicationBar = ProgressBar.INTOXICATION.constructBar();
        intoxicationBar.setX(intoxicationX);
        intoxicationBar.setY(intoxicationY);
        intoxicationBar.setWidth(getCurIntoxicationBarWidth());
        Rectangle intoxicationBarBg = ProgressBar.INTOXICATION.constructBg();
        intoxicationBarBg.setX(intoxicationX);
        intoxicationBarBg.setY(intoxicationY);
        lastIntoxicationPercent = parameters.getIntoxication().getPercent();

        parameters = entity.getComponent(PlayerParametersComponent.class);

        appliedPotionsPane = new Pane();
        appliedPotionsPane.setLayoutY(intoxicationX + ProgressBar.INTOXICATION.getSize().getHeight() + 5);

        Pane view = new Pane();
        view.setLayoutX(FXGL.getAppWidth() - healthBarBg.getWidth() - 10);
        view.setLayoutY(10);

        view.getChildren().addAll(staminaBarBg, staminaBar, healthBarBg, healthBar, intoxicationBarBg, intoxicationBar, appliedPotionsPane);

        appliedPotionsPane.setLayoutX(view.getBoundsInLocal().getWidth() - 50);

        FXGL.getGameScene().addUINode(view);
    }

    public void updatePotions(List<Potion> potions) {
        appliedPotionsPane.getChildren().clear();
        for (int i = 0; i < potions.size(); i += 1) {
            Potion potion = potions.get(i);
            InventoryCellButton button = ButtonFactory.createInventoryCellButton(potion, 50);
            button.setText("");
            button.setFocusTraversable(false);
            button.setLayoutY(i * 55);
            appliedPotionsPane.getChildren().add(button);
        }
    }

    private double getCurStaminaBarWidth() {
        return parameters.getStamina().getPercent() * ProgressBar.STAMINA.getSize().getWidth();
    }

    private double getCurHealthBarWidth() {
        return parameters.getHealth().getPercent() * ProgressBar.PLAYER_HEALTH.getSize().getWidth();
    }

    private double getCurIntoxicationBarWidth() {
        return parameters.getIntoxication().getPercent() * ProgressBar.INTOXICATION.getSize().getWidth();
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }

        if (Double.compare(parameters.getStamina().getPercent(), lastStaminaPercent) != 0) {
            staminaBar.setWidth(getCurStaminaBarWidth());
            lastStaminaPercent = parameters.getStamina().getPercent();
        }
        if (Double.compare(parameters.getHealth().getPercent(), lastHealthPercent) != 0) {
            healthBar.setWidth(getCurHealthBarWidth());
            lastHealthPercent = parameters.getHealth().getPercent();
        }
        if (Double.compare(parameters.getIntoxication().getPercent(), lastIntoxicationPercent) != 0) {
            intoxicationBar.setWidth(getCurIntoxicationBarWidth());
            lastIntoxicationPercent = parameters.getIntoxication().getPercent();
        }
    }
}
