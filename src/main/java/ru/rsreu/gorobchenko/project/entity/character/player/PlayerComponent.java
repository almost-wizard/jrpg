package ru.rsreu.gorobchenko.project.entity.character.player;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import com.almasb.fxgl.input.KeyTrigger;
import com.almasb.fxgl.input.TriggerListener;
import com.almasb.fxgl.physics.BoundingShape;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.components.HitBoxComponent;
import ru.rsreu.gorobchenko.project.components.character.CharacterPhysicsComponent;
import ru.rsreu.gorobchenko.project.components.character.DynamicValue;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.AttackOwner;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimation;
import ru.rsreu.gorobchenko.project.components.character.animation.CharacterAnimationComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterBuilder;
import ru.rsreu.gorobchenko.project.components.character.parameters.PlayerParametersComponent;
import ru.rsreu.gorobchenko.project.components.character.parameters.list.Intoxication;
import ru.rsreu.gorobchenko.project.components.character.parameters.list.Stamina;
import ru.rsreu.gorobchenko.project.components.character.ui.PlayerParametersUIComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.states.DeadPlayerState;
import ru.rsreu.gorobchenko.project.entity.character.player.states.WinPlayerState;
import ru.rsreu.gorobchenko.project.entity.inventory.CloseItemViewComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.PlayerInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.item.Coin;
import ru.rsreu.gorobchenko.project.entity.item.potion.Potion;
import ru.rsreu.gorobchenko.project.entity.item.potion.list.RaffardTheWhite;
import ru.rsreu.gorobchenko.project.entity.item.potion.list.Smallow;
import ru.rsreu.gorobchenko.project.entity.item.potion.list.WhiteHoney;
import ru.rsreu.gorobchenko.project.entity.item.weapon.list.DefaultSword;
import ru.rsreu.gorobchenko.project.physics.filter.Filters;

import java.util.ArrayList;
import java.util.List;

public class PlayerComponent extends Component {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 30;

    private final PlayerParametersComponent parameters;
    private final CharacterAnimation playerAnimation = new PlayerAnimation();
    private final PlayerInventoryComponent inventory;
    private final CloseItemViewComponent closeViewItem;

    private StateComponent state;
    private EntityState deadState;
    private EntityState winState;

    private final List<Potion> appliedPotions = new ArrayList<>();

    public PlayerComponent() {
        this.parameters = new CharacterBuilder()
                .health(new DynamicValue(Config.DEBUG ? 1000000000 : 100))
                .stamina(new Stamina(100))
                .intoxication(new Intoxication(100))
                .speed(150)
                .rollSpeed(300)
                .attacks(getAttacks())
                .comboMultipliers(1.0, 1.2, 1.5)
                .buildPlayer();
        this.inventory = new PlayerInventoryComponent(4, 3, 2);
        this.closeViewItem = new CloseItemViewComponent();
    }

    @Override
    public void onAdded() {
        entity.addComponent(new CharacterPhysicsComponent(Filters.PLAYER.getFilter()));

        CharacterAnimationComponent animation = new CharacterAnimationComponent();
        animation.loopAnimation(playerAnimation.getIdle());
        entity.addComponent(animation);

        entity.setLocalAnchor(playerAnimation.getSpriteSize().multiply(0.5));

        entity.addComponent(parameters);

        entity.addComponent(new PlayerMovementComponent(playerAnimation));
        entity.addComponent(new PlayerAttackComponent(playerAnimation));
        entity.addComponent(new HitBoxComponent(WIDTH, HEIGHT));
        entity.addComponent(new CollidableComponent(true));

        this.deadState = new DeadPlayerState(entity, playerAnimation);
        this.winState = new WinPlayerState(entity);

        this.state = new StateComponent();
        this.entity.addComponent(state);

        entity.addComponent(new PlayerParametersUIComponent());
        entity.addComponent(inventory);
        entity.addComponent(closeViewItem);

        inventory.getModel().resetItems(
                new DefaultSword(),
                new Smallow().setCount(3),
                new RaffardTheWhite(),
                new WhiteHoney().setCount(3),
                new Coin(300)
        );

        FXGL.getInput().addTriggerListener(new TriggerListener() {
            @Override
            protected void onKeyBegin(@NotNull KeyTrigger keyTrigger) {
                if (keyTrigger.getKey() == KeyCode.I) {
                    inventory.toggle();
                }
            }
        });
    }

    @Override
    public void onUpdate(double tpf) {
        if (entity == null) {
            entity = PlayerManager.getInstance().getPlayer();
        }
        if (parameters.isDead()) {
            state.changeState(deadState);
        }
    }

    private Attack[] getAttacks() {
        var bbox1 = BoundingShape.box(playerAnimation.getSpriteWidth() / 2.0, playerAnimation.getSpriteHeight());
        var bbox2 = BoundingShape.box(playerAnimation.getSpriteWidth(), playerAnimation.getSpriteHeight());

        return new Attack[]{
                new Attack(AttackOwner.PLAYER, bbox1, 0, Duration.seconds(0.2), playerAnimation.getAttack(0)),
                new Attack(AttackOwner.PLAYER, bbox1, 1, Duration.seconds(0.2), playerAnimation.getAttack(1)),
                new Attack(AttackOwner.PLAYER, bbox2, 2, Duration.seconds(0.2), playerAnimation.getAttack(2)),
        };
    }

    public List<Potion> getAppliedPotions() {
        return appliedPotions;
    }

    public void die() {
        state.changeState(deadState);
    }

    public void win() {
        if (!state.isIn(winState)) {
            state.changeState(winState);
        }
    }
}
