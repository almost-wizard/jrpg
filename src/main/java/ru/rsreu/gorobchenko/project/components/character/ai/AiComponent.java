package ru.rsreu.gorobchenko.project.components.character.ai;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.state.EntityState;
import com.almasb.fxgl.entity.state.StateComponent;
import ru.rsreu.gorobchenko.project.components.character.ai.states.AttackState;
import ru.rsreu.gorobchenko.project.components.character.parameters.AiParametersComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;
import ru.rsreu.gorobchenko.project.entity.inventory.type.MinableInventoryComponent;
import ru.rsreu.gorobchenko.project.shared.utils.EntityUtils;

public class AiComponent extends Component {
    private StateComponent state;
    private Entity player;
    private MinableInventoryComponent inventory;
    private AiParametersComponent parameters;

    private final EntityState idleState;
    private final AttackState attackState;
    private final EntityState returningState;
    private final EntityState deadMinable;
    private final EntityState deadDisappear;

    public AiComponent(EntityState idleState, AttackState attackState, EntityState returningState, EntityState diedMinable, EntityState deadDisappear) {
        this.idleState = idleState;
        this.attackState = attackState;
        this.returningState = returningState;
        this.deadMinable = diedMinable;
        this.deadDisappear = deadDisappear;
    }

    @Override
    public void onAdded() {
        state = new StateComponent();
        entity.addComponent(state);

        inventory = entity.getComponent(MinableInventoryComponent.class);
        inventory.setEnabled(false);
        parameters = entity.getComponent(AiParametersComponent.class);
    }

    @Override
    public void onUpdate(double tpf) {
        if (player == null) {
            player = PlayerManager.getInstance().getPlayer();
        }

        double distanceToPlayer = EntityUtils.distance(entity, player);

        if (parameters.isDead()) {
            if (inventory.getModel().getItemsCount() == 0) {
                state.changeState(deadDisappear);
            } else {
                state.changeState(deadMinable);
            }
        } else if (!state.isIn(returningState)) {
            if (distanceToPlayer < parameters.getTriggerDistance()) {
                state.changeState(attackState);
            } else{
                state.changeState(idleState);
            }
        }
    }

    public StateComponent getState() {
        return state;
    }

    public EntityState getReturningState() {
        return returningState;
    }

    public EntityState getIdleState() {
        return idleState;
    }

    public EntityState getDeadMinable() {
        return deadMinable;
    }

    public EntityState getDeadDisappearState() {
        return deadDisappear;
    }
}
