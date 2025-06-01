package ru.rsreu.gorobchenko.project.entity.character.enemy.werewolf;

import com.almasb.fxgl.entity.Entity;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AiAttack;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class WerewolfAttack extends AiAttack {

    public WerewolfAttack(Entity entity, CharacterAnimation characterAnimation, Rectangle area, Point2D startPosition) {
        super(entity, characterAnimation, true, false, area, startPosition);
        setMaxComboCount(3);
    }

    @Override
    protected Attack getCurrentAttack(boolean isMoved) {
        if (isMoved) {
            return parameters.getAttack(3);
        }
        return super.getCurrentAttack(false);
    }
}
