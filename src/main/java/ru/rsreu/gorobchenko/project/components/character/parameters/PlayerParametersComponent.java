package ru.rsreu.gorobchenko.project.components.character.parameters;

import ru.rsreu.gorobchenko.project.components.character.parameters.list.Intoxication;
import ru.rsreu.gorobchenko.project.components.character.parameters.list.Stamina;

public class PlayerParametersComponent extends CharacterParametersComponent {
    private final double rollSpeed;

    private final Stamina stamina;
    private final Intoxication intoxication;

    protected PlayerParametersComponent(CharacterBuilder builder) {
        super(builder);
        this.rollSpeed = builder.getRollSpeed();
        this.stamina = builder.getStamina();
        this.intoxication = builder.getIntoxication();
    }

    public double getRollSpeed() {
        return rollSpeed;
    }

    public Stamina getStamina() {
        return stamina;
    }

    public Intoxication getIntoxication() {
        return intoxication;
    }
}
