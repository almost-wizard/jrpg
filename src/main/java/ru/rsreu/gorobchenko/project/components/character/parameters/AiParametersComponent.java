package ru.rsreu.gorobchenko.project.components.character.parameters;

public class AiParametersComponent extends CharacterParametersComponent {
    private final double attackSpeed;
    private final double triggerDistance;
    private final double standZoneRadius;
    private final double stopDistance;

    protected AiParametersComponent(CharacterBuilder builder) {
        super(builder);
        this.attackSpeed = builder.getAttackSpeed();
        this.triggerDistance = builder.getTriggerDistance();
        this.standZoneRadius = builder.getStandZoneRadius();
        this.stopDistance = builder.getStopDistance();
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }

    public double getTriggerDistance() {
        return triggerDistance;
    }

    public double getStandZoneRadius() {
        return standZoneRadius;
    }

    public double getStopDistance() {
        return stopDistance;
    }
}
