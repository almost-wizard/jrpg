package ru.rsreu.gorobchenko.project.components.character.animation;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class CharacterAnimation {
    protected AnimationChannel idle;
    protected AnimationChannel lie;
    protected AnimationChannel walk;
    protected AnimationChannel run;
    protected AnimationChannel roll;
    protected AnimationChannel die;
    protected final List<AnimationChannel> attacks = new ArrayList<>();

    public AnimationChannel getIdle() {
        return idle;
    }

    public AnimationChannel getLie() {
        return lie;
    }

    public AnimationChannel getWalk() {
        return walk;
    }

    public AnimationChannel getRun() {
        return run;
    }

    public AnimationChannel getRoll() {
        return roll;
    }

    public AnimationChannel getDie() {
        return die;
    }

    public AnimationChannel getAttack(int index) {
        return attacks.get(index);
    }

    public AnimationChannel getAttack() {
        return attacks.getFirst();
    }

    public abstract int getFramesPerRow();

    public abstract int getSpriteWidth();

    public abstract int getSpriteHeight();

    public Point2D getSpriteSize() {
        return new Point2D(getSpriteWidth(), getSpriteHeight());
    }

    protected AnimationChannel constructAnimationChannel(String spriteSetPath, Duration duration, int startFrame, int endFrame) {
        Image image = FXGL.image(spriteSetPath);
        return new AnimationChannel(
                image,
                getFramesPerRow(),
                getSpriteWidth(),
                getSpriteHeight(),
                duration,
                startFrame,
                endFrame
        );
    }
}
