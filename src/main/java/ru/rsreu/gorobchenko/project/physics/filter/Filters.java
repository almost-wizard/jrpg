package ru.rsreu.gorobchenko.project.physics.filter;

public enum Filters {
    NO {
        @Override
        public com.almasb.fxgl.physics.box2d.dynamics.Filter getFilter() {
            return constructFilter(NO);
        }
    },
    PLAYER {
        @Override
        public com.almasb.fxgl.physics.box2d.dynamics.Filter getFilter() {
            return constructFilter(WALL);
        }
    },
    NPC {
        @Override
        public com.almasb.fxgl.physics.box2d.dynamics.Filter getFilter() {
            return constructFilter(WALL);
        }
    },
    GHOST {
        @Override
        public com.almasb.fxgl.physics.box2d.dynamics.Filter getFilter() {
            return constructFilter(NO);
        }
    },
    WALL {
        @Override
        public com.almasb.fxgl.physics.box2d.dynamics.Filter getFilter() {
            return constructFilter(PLAYER, NPC);
        }
    };

    public abstract com.almasb.fxgl.physics.box2d.dynamics.Filter getFilter();

    public com.almasb.fxgl.physics.box2d.dynamics.Filter constructFilter(Filters... filters) {
        var result = new com.almasb.fxgl.physics.box2d.dynamics.Filter();
        result.categoryBits = this.ordinal();

        int maskBits = filters[0].ordinal();
        for (int i = 1; i < filters.length; i += 1) {
            maskBits |= filters[i].ordinal();
        }
        result.maskBits = maskBits;

        return result;
    }

}
