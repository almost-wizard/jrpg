package ru.rsreu.gorobchenko.project.entity.character.enemy.werewolf;

import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class WerewolfAnimation extends CharacterAnimation {
    private static final String SPRITE_SET_PATH = "enemies/werewolf.png";

    public WerewolfAnimation() {
        idle = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1), 0, 7);
        walk = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1.2), 11, 21);
        run = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.6), 22, 30);
        // 1
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 44, 49));
        // 2
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 55, 58));
        // 3
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 66, 70));
        // run attack
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 33, 39));
        die = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.4), 77, 78);
    }

    @Override
    public int getFramesPerRow() {
        return 11;
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
