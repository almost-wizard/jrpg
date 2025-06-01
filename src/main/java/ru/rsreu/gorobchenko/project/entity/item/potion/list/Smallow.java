package ru.rsreu.gorobchenko.project.entity.item.potion.list;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.time.TimerAction;
import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.entity.item.Item;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;

public class Smallow extends Potion {
    private TimerAction onEndTimer;

    public Smallow() {
        super("assets/textures/potions/smallow.png", 20);
//        setBigSpritePath("assets/textures/potions/smallow-big.png");
        setName("Эликсир «Ласточка»");
        setItemDescription("Нет птицы прекраснее ласточки, предвестницы весны и возрождения жизни. " +
                "Даже темные маги-ренегаты, которые разработали рецепты для эликсиров, поддались " +
                "очарованию образа этой птицы и дали ее имя эликсиру, который ускоряет регенерацию организма.");
        setParametersDescription("""
                Действие: Ласточка ускоряет восстановление здоровья.
                Срок действия: краткий.
                Токсичность: небольшая.""");
    }

    @Override
    public void expireTimer() {
        super.expireTimer();
        onEndTimer.expire();
    }

    @Override
    public boolean applyToPlayer(PlayerParametersComponent parameters) {
        if (!super.applyToPlayer(parameters)) {
            return false;
        }
        this.timerAction = FXGL.run(() -> parameters.heal(1), Duration.seconds(0.1), 50);
        this.onEndTimer = FXGL.runOnce(this::onEnd, Duration.seconds(5));
        return true;
    }

    @Override
    public Item constructCopy() {
        return new Smallow();
    }
}
