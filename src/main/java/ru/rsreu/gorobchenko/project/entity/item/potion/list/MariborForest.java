package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

public class MariborForest extends Potion {
    private static final int MAX_STAMINA_INCREASE_COEFFICIENT = 2;
    private static final Duration DURATION = Duration.minutes(3);
    private PlayerParametersComponent parameters;

    public MariborForest() {
        super("assets/textures/potions/maribor-forest.png", 40);
        setName("Эликсир «Лес Марибора»");
        setItemDescription("Согласно старинной легенде, этот напиток раньше делали дриады. От них формулу переняли друиды Мариборского леса, которые затем передали её своим собратьям в других уголках мира. Формула добралась и до магов, которые начали производить эликсир, для повсеместного использования.");
        setParametersDescription("""
                Действие: Лес Марибора заметно увеличивает максимальный показатель Энергии.
                Срок действия: длительный.
                Токсичность: средняя.""");
    }

    @Override
    public void expireTimer() {
        super.expireTimer();
        this.parameters.getStamina().divideMax(MAX_STAMINA_INCREASE_COEFFICIENT);
    }

    @Override
    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        if (!super.applyToPlayer(parameters)) {
            return false;
        }
        parameters.getStamina().multiplyMax(MAX_STAMINA_INCREASE_COEFFICIENT);
        parameters.getStamina().startRestoreTimer();
        this.parameters = parameters;
        FXGL.runOnce(this::onEnd, DURATION);
        return true;
    }

    @Override
    public Item constructCopy() {
        return new MariborForest();
    }
}
