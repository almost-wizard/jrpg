package ru.rsreu.gorobchenko.project.components.character.animation;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;

public class CharacterAnimationComponent extends Component {
    private AnimatedTexture texture;

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
    }

    public void setTexture(AnimationChannel channel) {
        this.texture = new AnimatedTexture(channel);
        texture.loop();
    }

    public void setReverted(boolean reverted) {
        texture.setScaleX(reverted ? -1.0 : 1.0);
    }

    public CharacterAnimationComponent loopAnimation(AnimationChannel channel) {
        if (texture == null) {
            texture = new AnimatedTexture(channel);
            texture.loop();
        } else if (texture.getAnimationChannel() != channel) {
            texture.loopAnimationChannel(channel);
        }
        return this;
    }

    public void playAnimation(AnimationChannel channel) {
        texture.playAnimationChannel(channel);
    }

    public void playAnimation(AnimationChannel channel, Runnable onFinish) {
        texture.playAnimationChannel(channel);
        texture.setOnCycleFinished(() -> {
            onFinish.run();
            texture.setOnCycleFinished(() -> {
            });
        });
    }
}
