package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

public class Owl extends Potion {
    private static final int STAMINA_INCREASE_COEFFICIENT = 3;
    private static final Duration DURATION = Duration.minutes(3);
    private PlayerParametersComponent parameters;

    public Owl() {
        super("assets/textures/potions/owl.png", 40);
        setName("Эликсир «Филин»");
        setItemDescription("Когда путник готовится к всенощному бдению у ложа проклятого человека или к длительному бою, он готовит себе Филина, чтобы неимоверно повысить свою Энергию.");
        setParametersDescription("""
                Действие: Филин заметно повышает скорость восстановление Энергии.
                Срок действия: длительный.
                Токсичность: средняя.""");
    }

    @Override
    public void expireTimer() {
        super.expireTimer();
        this.parameters.getStamina().getRestoreCoefficient().divide(STAMINA_INCREASE_COEFFICIENT);
    }

    @Override
    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        if (!super.applyToPlayer(parameters)) {
            return false;
        }
        parameters.getStamina().getRestoreCoefficient().multiply(STAMINA_INCREASE_COEFFICIENT);
        this.parameters = parameters;
        FXGL.runOnce(
                () -> {
                    parameters.getStamina().getRestoreCoefficient().divide(STAMINA_INCREASE_COEFFICIENT);
                    onEnd();
                },
                DURATION
        );
        return true;
    }

    @Override
    public Item constructCopy() {
        return new Owl();
    }
}
