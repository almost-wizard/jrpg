package ru.rsreu.gorobchenko.project.entity.character.npc;

import java.util.Random;

public enum NpcType {
    MALE {
        @Override
        String getSpritePath() {
            return getPath(this, new Random().nextInt(1, 17));
        }
    },
    FEMALE {
        @Override
        String getSpritePath() {
            return getPath(this, new Random().nextInt(1, 9));
        }
    },
    RANDOM {
        @Override
        String getSpritePath() {
            NpcType rndmNpcType = NpcType.values()[new Random().nextInt(0, 2)];
            return rndmNpcType.getSpritePath();
        }
    };

    abstract String getSpritePath();

    private static String getPath(NpcType npcType, int index) {
        return "characters/npc/" + npcType.toString().toLowerCase() + "/" + index + ".png";
    }
}
