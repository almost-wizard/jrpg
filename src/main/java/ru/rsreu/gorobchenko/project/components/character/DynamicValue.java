package ru.rsreu.gorobchenko.project.components.character;

public class DynamicValue {
    private int currentValue;
    private int maxValue;

    public DynamicValue(int maxValue) {
        this(maxValue, maxValue);
    }

    public DynamicValue(DynamicValue other) {
        this(other.maxValue, other.maxValue);
    }

    public DynamicValue(int currentValue, int maxValue) {
        this.currentValue = currentValue;
        this.maxValue = maxValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int value) {
        this.currentValue = value;
    }

    public boolean isMinimum() {
        return getCurrentValue() <= 0;
    }

    public boolean isMaximum() {
        return getCurrentValue() >= getMaxValue();
    }

    public int getMaxValue() {
        return maxValue;
    }

    public double getPercent() {
        return (double) currentValue / (double) maxValue;
    }

    public void increment(int addon) {
        this.currentValue =  Math.clamp(this.currentValue + addon, 0, this.maxValue);
    }

    public void decrement(int addon) {
        this.currentValue =  Math.clamp(this.currentValue - addon, 0, this.maxValue);
    }

    public void multiply(int addon) {
        this.currentValue =  Math.clamp(this.currentValue * addon, 0, this.maxValue);
    }

    public void divide(int addon) {
        if (addon == 0) {
            return;
        }
        this.currentValue =  (int)Math.round(Math.clamp((double)this.currentValue / addon, 0, this.maxValue));
    }

    public void incrementMax(int addon) {
        this.maxValue =  maxValue + addon;
    }

    public void decrementMax(int addon) {
        this.maxValue =  maxValue - addon;
    }

    public void multiplyMax(int addon) {
        this.maxValue =  this.maxValue * addon;
    }

    public void divideMax(int addon) {
        if (addon == 0) {
            return;
        }
        this.maxValue = (int)Math.round((double)this.maxValue / addon);
    }
}
