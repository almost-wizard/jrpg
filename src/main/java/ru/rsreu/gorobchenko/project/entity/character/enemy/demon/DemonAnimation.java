package ru.rsreu.gorobchenko.project.entity.character.enemy.demon;

import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class DemonAnimation extends CharacterAnimation {
    private static final String SPRITE_SET_PATH = "enemies/demon.png";

    public DemonAnimation() {
        idle = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.9), 0, 5);
        walk = run = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1.1), 22, 33);
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1), 44, 58));
        die = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(2), 88, 109);
    }

    @Override
    public int getFramesPerRow() {
        return 22;
    }

    @Override
    public int getSpriteWidth() {
        return 288;
    }

    @Override
    public int getSpriteHeight() {
        return 160;
    }
}
