package ru.rsreu.gorobchenko.project.entity.character.npc;

import javafx.util.Duration;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;

public class NpcAnimation extends CharacterAnimation {

    public NpcAnimation(String spriteSetPath) {
        idle = constructAnimationChannel(spriteSetPath, Duration.seconds(1), 0, 3);
        walk = constructAnimationChannel(spriteSetPath, Duration.seconds(0.8), 0, 6);
//        WALK_DOWN = constructAnimationChannel(spriteSetPath, Duration.seconds(0.8), 7, 13);
//        WALK_UP = constructAnimationChannel(spriteSetPath, Duration.seconds(0.8), 14, 20);
//        WALK_RIGHT = constructAnimationChannel(spriteSetPath, Duration.seconds(0.8), 0, 6);
    }

    @Override
    public int getFramesPerRow() {
        return 8;
    }

    @Override
    public int getSpriteWidth() {
        return 32;
    }

    @Override
    public int getSpriteHeight() {
        return 48;
    }
}
