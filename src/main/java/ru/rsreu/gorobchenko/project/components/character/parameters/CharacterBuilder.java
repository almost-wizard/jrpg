package ru.rsreu.gorobchenko.project.components.character.parameters;

import ru.rsreu.gorobchenko.project.components.character.DynamicValue;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.parameters.list.Intoxication;
import ru.rsreu.gorobchenko.project.components.character.parameters.list.Stamina;

import java.util.List;

public class CharacterBuilder {
    private DynamicValue health;
    private double speed;
    private List<Attack> attacks;
    private List<Double> comboMultipliers;

    private double attackSpeed;
    private double triggerDistance;
    private double standZoneRadius;
    private double stopDistance;

    private double rollSpeed;
    private Stamina stamina;
    private Intoxication intoxication;

    public DynamicValue getHealth() {
        return health;
    }

    public double getSpeed() {
        return speed;
    }

    public List<Attack> getAttacks() {
        return attacks;
    }

    public List<Double> getComboMultipliers() {
        return comboMultipliers;
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

    public double getRollSpeed() {
        return rollSpeed;
    }

    public Stamina getStamina() {
        return stamina;
    }

    public Intoxication getIntoxication() {
        return intoxication;
    }

    public CharacterBuilder health(DynamicValue health) {
        this.health = health;
        return this;
    }

    public CharacterBuilder speed(double speed) {
        this.speed = speed;
        return this;
    }

    public CharacterBuilder attacks(Attack... attacks) {
        this.attacks = List.of(attacks);
        return this;
    }

    public CharacterBuilder comboMultipliers(Double... comboMultipliers) {
        this.comboMultipliers = List.of(comboMultipliers);
        return this;
    }

    public CharacterBuilder attackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
        return this;
    }

    public CharacterBuilder triggerDistance(double triggerDistance) {
        this.triggerDistance = triggerDistance;
        return this;
    }

    public CharacterBuilder standZoneRadius(double standZoneRadius) {
        this.standZoneRadius = standZoneRadius;
        return this;
    }

    public CharacterBuilder stopDistance(double stopDistance) {
        this.stopDistance = stopDistance;
        return this;
    }

    public CharacterBuilder rollSpeed(double rollSpeed) {
        this.rollSpeed = rollSpeed;
        return this;
    }

    public CharacterBuilder intoxication(Intoxication intoxication) {
        this.intoxication = intoxication;
        return this;
    }

    public CharacterBuilder stamina(Stamina stamina) {
        this.stamina = stamina;
        return this;
    }

    public CharacterParametersComponent buildCharacter() {
        return new CharacterParametersComponent(this);
    }

    public PlayerParametersComponent buildPlayer() {
        return new PlayerParametersComponent(this);
    }

    public AiParametersComponent buildAi() {
        return new AiParametersComponent(this);
    }
}
