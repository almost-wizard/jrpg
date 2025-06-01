package ru.rsreu.gorobchenko.project.components.character.ui;

import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.components.character.parameters.AiParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyComponent;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;
import ru.rsreu.gorobchenko.project.ui.ProgressBar;

public class NpcHealthBarComponent extends Component {
    private Rectangle healthBar;

    private int lastHealth;
    private CharacterParametersComponent parameters;

    private final EnemyComponent enemyComponent;

    public NpcHealthBarComponent(EnemyComponent enemyComponent) {
        this.enemyComponent = enemyComponent;
    }

    @Override
    public void onAdded() {
        healthBar = ProgressBar.NPC_HEALTH.constructBar();

        Point2D entitySize = EntityUtils.getViewSize(entity);

        double x =  (entitySize.getX() - healthBar.getWidth()) / 2.0;
        double y = entitySize.getY() - enemyComponent.getColliderHeight() - healthBar.getHeight() - 10;

        healthBar.setX(x);
        healthBar.setY(y);

        Rectangle healthBarBg = ProgressBar.NPC_HEALTH.constructBg();
        healthBarBg.setX(x);
        healthBarBg.setY(y);

        parameters = entity.getComponent(AiParametersComponent.class);
        lastHealth = parameters.getHealth().getCurrentValue();

        entity.getViewComponent().addChild(healthBarBg);
        entity.getViewComponent().addChild(healthBar);
    }

    private double getCurHealthBarWidth() {
        return parameters.getHealth().getPercent() * ProgressBar.NPC_HEALTH.getSize().getWidth();
    }

    @Override
    public void onUpdate(double tpf) {
        if (parameters.getHealth().getCurrentValue() != lastHealth) {
            healthBar.setWidth(getCurHealthBarWidth());
            lastHealth = parameters.getHealth().getCurrentValue();
        }
    }
}
