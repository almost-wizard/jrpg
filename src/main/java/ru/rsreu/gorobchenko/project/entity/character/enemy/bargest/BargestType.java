package ru.rsreu.gorobchenko.project.entity.character.enemy.bargest;

import java.util.Random;

public enum BargestType {
    GHOST {
        @Override
        public String getSpritePath() {
            return BargestType.getSpritePath(this);
        }
    }, DEMONIC {
        @Override
        public String getSpritePath() {
            return BargestType.getSpritePath(this);
        }
    }, RANDOM {
        @Override
        public String getSpritePath() {
            BargestType rndmNpcType = BargestType.values()[new Random().nextInt(0, BargestType.values().length - 1)];
            return rndmNpcType.getSpritePath();
        }
    };

    public static final String BARGEST_TYPE = "bargest-type";

    public abstract String getSpritePath();

    private static String getSpritePath(BargestType type) {
        return "enemies/bargest/" + type.name().toLowerCase() + ".png";
    }
}
