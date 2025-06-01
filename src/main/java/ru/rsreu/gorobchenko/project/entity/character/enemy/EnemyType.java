package ru.rsreu.gorobchenko.project.entity.character.enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import ru.rsreu.gorobchenko.project.entity.character.enemy.bargest.BargestComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.demon.DemonComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.ghost.GhostComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.werewolf.WerewolfComponent;
import ru.rsreu.gorobchenko.project.factory.EntityType;

import java.util.List;

public enum EnemyType {
    BARGEST {
        @Override
        public Entity spawn(SpawnData spawnData) {
            return FXGL.getGameWorld().spawn(EntityType.Enemies.bargest, spawnData);
        }
    },
    WEREWOLF {
        @Override
        public Entity spawn(SpawnData spawnData) {
            return FXGL.getGameWorld().spawn(EntityType.Enemies.werewolf, spawnData);
        }
    },
    GHOST {
        @Override
        public Entity spawn(SpawnData spawnData) {
            return FXGL.getGameWorld().spawn(EntityType.Enemies.ghost, spawnData);
        }
    },
    DEMON {
        @Override
        public Entity spawn(SpawnData spawnData) {
            return FXGL.getGameWorld().spawn(EntityType.Enemies.demon, spawnData);
        }
    };

    public abstract Entity spawn(SpawnData spawnData);

    public static List<Class<? extends EnemyComponent>> getAllClasses() {
        return List.of(
                BargestComponent.class,
                WerewolfComponent.class,
                GhostComponent.class,
                DemonComponent.class
        );
    }

    public static EnemyComponent getEnemyClass(Entity entity) {
        var list = EnemyType.getAllClasses();

        for (var l : list) {
            if (entity.hasComponent(l)) {
                return entity.getComponent(l);
            }
        }

        return null;
    }
}
