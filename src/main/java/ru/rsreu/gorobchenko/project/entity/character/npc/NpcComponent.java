package ru.rsreu.gorobchenko.project.entity.character.npc;

import com.almasb.fxgl.entity.component.Component;
import ru.rsreu.gorobchenko.project.components.character.CharacterPhysicsComponent;
import ru.rsreu.gorobchenko.project.components.HitBoxComponent;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.physics.filter.Filters;

public class NpcComponent extends Component {
    private final String spriteSetPath;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 32;

    public NpcComponent() {
        this.spriteSetPath = NpcType.RANDOM.getSpritePath();
    }

    public NpcComponent(NpcType type) {
        this.spriteSetPath = type.getSpritePath();
    }

    @Override
    public void onAdded() {
        CharacterAnimationComponent animation = new CharacterAnimationComponent();
        CharacterAnimation characterAnimation = new NpcAnimation(spriteSetPath);
        animation.loopAnimation(characterAnimation.getIdle());

        entity.addComponent(animation);
        entity.addComponent(new CharacterPhysicsComponent(Filters.NPC.getFilter()));
        entity.addComponent(new HitBoxComponent(WIDTH, HEIGHT));
    }
}
