package ru.rsreu.gorobchenko.project;

import javafx.util.Duration;

public class Config {
//    public static final boolean DEBUG = true;
    public static final boolean DEBUG = false;

    public static final Duration DELAY_BETWEEN_ENEMIES_SPAWN = Duration.seconds(2);

    public static class FadeTransition {
        public static final int DURATION = 2000;
        public static final int MIN_DELAY_BEFORE_GAME_INIT = 500;
        public static final int MIN_DELAY_BETWEEN_LOADING_AND_GAME = 500;
    }

    public static class Map {
        public static final int TILE_SIZE = 16;

        public static final int TILES_COUNT_IN_WIDTH = 180;
        public static final int TILES_COUNT_IN_HEIGHT = 120;

        public static final int WIDTH = TILE_SIZE * TILES_COUNT_IN_WIDTH;
        public static final int HEIGHT = TILE_SIZE * TILES_COUNT_IN_HEIGHT;
    }
}
