package ru.rsreu.gorobchenko.project.components.character.parameters;

import com.almasb.fxgl.entity.component.Component;
import ru.rsreu.gorobchenko.project.components.character.DynamicValue;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;

import java.util.List;

public class CharacterParametersComponent extends Component {
    private final DynamicValue health;
    private final double speed;

    private final List<Attack> attacks;
    private final List<Double> comboMultipliers;

    public double damageCoefficient = 1;

    protected CharacterParametersComponent(CharacterBuilder builder) {
        this.health = builder.getHealth();
        this.speed = builder.getSpeed();
        this.attacks = builder.getAttacks();
        if (builder.getComboMultipliers() == null || builder.getComboMultipliers().isEmpty()) {
            this.comboMultipliers = List.of(1.0);
        } else {
            this.comboMultipliers = builder.getComboMultipliers();
        }
    }

    public void takeDamage(int damage) {
        health.decrement(damage);
    }

    public void kill(Runnable onDead) {
        takeDamage(health.getCurrentValue());
        onDead.run();
    }

    public void heal(int heal) {
        health.increment(heal);
    }

    public boolean isDead() {
        return health.getCurrentValue() <= 0;
    }

    public DynamicValue getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    }

    public Attack getAttack() {
        return attacks.getFirst();
    }

    public Attack getAttack(int index) {
        return attacks.get(index);
    }

    public int getAttacksCount() {
        return attacks.size();
    }

    public double getComboMultiplier(int index) {
        return comboMultipliers.get(index);
    }
}
