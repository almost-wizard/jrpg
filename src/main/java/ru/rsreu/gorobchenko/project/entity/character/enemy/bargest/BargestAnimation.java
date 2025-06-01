package ru.rsreu.gorobchenko.project.entity.character.enemy.bargest;

import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class BargestAnimation extends CharacterAnimation {

    public BargestAnimation(String spriteSetPath) {
        idle = constructAnimationChannel(spriteSetPath, Duration.seconds(1), 0, 7);
        lie = die = constructAnimationChannel(spriteSetPath, Duration.seconds(0.4), 64, 67);
        walk = constructAnimationChannel(spriteSetPath, Duration.seconds(1), 32, 39);
        run = constructAnimationChannel(spriteSetPath, Duration.seconds(0.5), 24, 31);
        attacks.add(constructAnimationChannel(spriteSetPath, Duration.seconds(0.4), 40, 47));
    }

    @Override
    public int getFramesPerRow() {
        return 8;
    }

    @Override
    public int getSpriteWidth() {
        return 64;
    }

    @Override
    public int getSpriteHeight() {
        return 49;
    }
}
