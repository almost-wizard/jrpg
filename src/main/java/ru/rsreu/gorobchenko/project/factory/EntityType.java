package ru.rsreu.gorobchenko.project.factory;

public enum EntityType {
    PLAYER,
    BLACKSMITH,
    NPC,

    NIGHT_GHOST_AREA,
    ENEMY,

    COLLECTABLE,
    CHEST,

    RM_WOODS_TO_LAKE_1,
    RM_WOODS_TO_LAKE_2,
    RM_WOODS_TO_LAKE_3,
    RM_COLLIDER_TO_ARONDIT,
    ;

    // Characters
    public static final String player = "player";
    public static final String npc = "npc";

    public static final String attackTrail = "attack-trail";

    // Enemies
    public static class Enemies {
        public static final String bargest = "bargest";
        public static final String ghost = "ghost";
        public static final String werewolf = "werewolf";
        public static final String demon = "demon";
    }

    public static final String playerSpawn = "player-spawn";
    public static final String blacksmithSpawn = "blacksmith-spawn";
    public static final String witchSpawn = "witch-spawn";

    public static final String bargestArea = "bargest-area";
    public static final String werewolfArea = "werewolf-area";
    public static final String ghostArea = "ghost-area";
    public static final String mixedArea = "mixed-area";
    public static final String demonArea = "demon-area";

    public static final String nightGhostArea = "night-ghost-area";

    // Interacting objects
    public static class Interactable {

    }
    public static final String arondit = "arondit";
    public static final String chest = "chest";
    public static final String pirateChest = "pirate-chest";
    public static final String ghostChest = "ghost-chest";

    // Removable objects
    public static class Removable {
        public static class WoodsToLake {
            public static final String t1 = "rm-tree-to-lake-1";
            public static final String t2 = "rm-tree-to-lake-2";
            public static final String t3 = "rm-tree-to-lake-3";
        }

        public static final String colliderToArondit = "rm-collider-to-arondit";
    }

    // Other
    public static final String collider = "collider";
    public static final String environment = "environment";
}
