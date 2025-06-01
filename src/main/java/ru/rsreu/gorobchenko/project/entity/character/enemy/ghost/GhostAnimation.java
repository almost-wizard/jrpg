package ru.rsreu.gorobchenko.project.entity.character.enemy.ghost;

import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class GhostAnimation extends CharacterAnimation {
    private static final String SPRITE_SET_PATH = "enemies/ghost.png";

    public GhostAnimation() {
        idle = walk = run = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1), 0, 11);
        die = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.8), 34, 50);
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.7), 17, 33));
    }

    @Override
    public int getFramesPerRow() {
        return 17;
    }

    @Override
    public int getSpriteWidth() {
        return 64;
    }

    @Override
    public int getSpriteHeight() {
        return 64;
    }
}
