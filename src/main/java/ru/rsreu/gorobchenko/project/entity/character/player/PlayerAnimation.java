package ru.rsreu.gorobchenko.project.entity.character.player;

import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class PlayerAnimation extends CharacterAnimation {
    private static final String SPRITE_SET_PATH = "characters/adventurer/adventurer.png";

    public PlayerAnimation() {
        idle = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1), 0, 3);
        run = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.8), 8, 13);
        roll = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 18, 21);
//        hurt = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.3), 59, 61);
        die = constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(1.3), 62, 68);

        /*  1  */
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.4), 42, 46));
        /*  2  */
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.6), 47, 53));
        /*  3  */
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 54, 59));
        /*  spin  */
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.6), 96, 102));
        /*  ground  */
        attacks.add(constructAnimationChannel(SPRITE_SET_PATH, Duration.seconds(0.5), 104, 108));
    }

    @Override
    public int getFramesPerRow() {
        return 7;
    }

    @Override
    public int getSpriteWidth() {
        return 50;
    }

    @Override
    public int getSpriteHeight() {
        return 37;
    }
}
