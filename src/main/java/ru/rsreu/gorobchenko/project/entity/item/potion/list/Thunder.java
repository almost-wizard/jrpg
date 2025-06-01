package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import com.almasb.fxgl.dsl.FXGL;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

public class Thunder extends Potion {
    private static final int DAMAGE_INCREASE_COEFFICIENT = 2;
    private static final Duration DURATION = Duration.seconds(15);
    private PlayerParametersComponent parameters;

    public Thunder() {
        super("assets/textures/potions/thunder.png", 30);
        setName("Эликсир «Гром»");
        setItemDescription("Искатели приключений принимают этот эликсир перед боем с хорошо защищенными, выносливыми и сильными противниками. Гром вводит в боевой транс, что позволяет сконцентрироваться на атаке и наносить больший урон противнику.");
        setParametersDescription("""
                Действие: Эликсир значительно увеличивает наносимый противнику урон.
                Срок действия: длительный.
                Токсичность: средняя.""");
    }

    @Override
    public void expireTimer() {
        super.expireTimer();
        this.parameters.damageCoefficient /= DAMAGE_INCREASE_COEFFICIENT;
    }

    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        if (!super.applyToPlayer(parameters)) {
            return false;
        }
        parameters.damageCoefficient *= DAMAGE_INCREASE_COEFFICIENT;
        this.parameters = parameters;
        FXGL.runOnce(this::onEnd, DURATION);
        return true;
    }

    @Override
    public Item constructCopy() {
        return new Thunder();
    }
}
