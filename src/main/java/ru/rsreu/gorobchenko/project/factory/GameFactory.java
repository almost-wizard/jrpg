package ru.rsreu.gorobchenko.project.factory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.Config;
import ru.rsreu.gorobchenko.project.components.StaticColliderComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyImportance;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyType;
import ru.rsreu.gorobchenko.project.entity.character.enemy.bargest.BargestComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.bargest.BargestType;
import ru.rsreu.gorobchenko.project.entity.character.enemy.demon.DemonComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.ghost.GhostComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.werewolf.WerewolfComponent;
import ru.rsreu.gorobchenko.project.entity.character.npc.BlacksmithComponent;
import ru.rsreu.gorobchenko.project.entity.character.npc.NpcComponent;
import ru.rsreu.gorobchenko.project.entity.character.npc.WitchComponent;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.BaseInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.inventory.type.MinableInventoryComponent;
import ru.rsreu.gorobchenko.project.entity.item.Scroll;
import ru.rsreu.gorobchenko.project.entity.item.mutagen.list.DeadTreasure;
import ru.rsreu.gorobchenko.project.entity.item.potion.list.*;
import ru.rsreu.gorobchenko.project.entity.item.weapon.list.Arondit;
import ru.rsreu.gorobchenko.project.entity.trail.AttackTrailComponent;
import ru.rsreu.gorobchenko.project.shared.utils.SpawnUtils;


public class GameFactory implements EntityFactory {
    @Spawns(EntityType.npc)
    public Entity newNpc(SpawnData data) {
        return FXGL.entityBuilder(data).type(EntityType.NPC).with(new NpcComponent()).buildAndAttach();
    }

    @Spawns(EntityType.attackTrail)
    public Entity newAttackTrail(SpawnData data) {
        return FXGL.entityBuilder(data).with(new AttackTrailComponent(data)).buildAndAttach();
    }

    @Spawns(EntityType.playerSpawn)
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data).type(EntityType.PLAYER).with(new PlayerComponent()).build();
    }

    @Spawns(EntityType.blacksmithSpawn)
    public Entity newNpcVillageBlacksmith(SpawnData data) {
        return FXGL.entityBuilder().type(EntityType.BLACKSMITH).with(new BlacksmithComponent()).build();
    }

    @Spawns(EntityType.witchSpawn)
    public Entity newNpcVillageWitch(SpawnData data) {
        return FXGL.entityBuilder().type(EntityType.BLACKSMITH).with(new WitchComponent()).build();
    }

    @Spawns(EntityType.nightGhostArea)
    public Entity newNightGhostArea(SpawnData data) {
        return FXGL.entityBuilder().type(EntityType.NIGHT_GHOST_AREA).build();
    }

    @Spawns(EntityType.werewolfArea)
    public Entity newWerewolfArea(SpawnData data) {
        Rectangle area = SpawnUtils.getRectangleFrom(data);
        FXGL.runOnce(() -> {
            Point2D center = new Point2D(area.getX() + area.getWidth() / 2, area.getY() + area.getHeight() / 2);
            EntitySpawner.spawnEnemy(EnemyType.WEREWOLF, center, area);
        }, Config.DELAY_BETWEEN_ENEMIES_SPAWN);
        return FXGL.entityBuilder().build();
    }

    @Spawns(EntityType.demonArea)
    public Entity newDemonArea(SpawnData data) {
        Rectangle area = SpawnUtils.getRectangleFrom(data);
        FXGL.runOnce(() -> {
            Point2D center = new Point2D(area.getX() + area.getWidth() / 2, area.getY() + area.getHeight() / 2);
            EntitySpawner.spawnEnemy(EnemyImportance.NOT_IMPORTANT, EnemyType.DEMON, center, area);
        }, Config.DELAY_BETWEEN_ENEMIES_SPAWN);
        return FXGL.entityBuilder().build();
    }

    @Spawns(EntityType.bargestArea)
    public Entity newBargestArea(SpawnData data) {
        Rectangle area = SpawnUtils.getRectangleFrom(data);
        FXGL.runOnce(() -> {
            int preferredEnemiesCount = SpawnUtils.getPreferredEnemiesCount(area);
            Point2D[] spawnCoordinates = SpawnUtils.getSpawnCoordinates(preferredEnemiesCount, area);
            for (Point2D coordinate : spawnCoordinates) {
                EntitySpawner.spawnEnemy(EnemyType.BARGEST, coordinate, area);
            }
        }, Config.DELAY_BETWEEN_ENEMIES_SPAWN);
        return FXGL.entityBuilder().buildAndAttach();
    }

    @Spawns(EntityType.ghostArea)
    public Entity newGhostArea(SpawnData data) {
        Rectangle area = SpawnUtils.getRectangleFrom(data);
        FXGL.runOnce(() -> {
            int preferredEnemiesCount = SpawnUtils.getPreferredEnemiesCount(area);
            Point2D[] spawnCoordinates = SpawnUtils.getSpawnCoordinates(preferredEnemiesCount, area);
            for (Point2D coordinate : spawnCoordinates) {
                EntitySpawner.spawnEnemy(EnemyType.GHOST, coordinate, area);
            }
        }, Config.DELAY_BETWEEN_ENEMIES_SPAWN);
        return FXGL.entityBuilder().buildAndAttach();
    }

    @Spawns(EntityType.mixedArea)
    public Entity newMixedArea(SpawnData data) {
        Rectangle area = SpawnUtils.getRectangleFrom(data);
        FXGL.runOnce(() -> {
            int preferredEnemiesCount = SpawnUtils.getPreferredEnemiesCount(area);
            Point2D[] spawnCoordinates = SpawnUtils.getSpawnCoordinates(preferredEnemiesCount, area);

            for (Point2D coordinate : spawnCoordinates) {
                int randomEnemy = (int) (Math.random() * 2);

                switch (randomEnemy) {
                    case 0 -> EntitySpawner.spawnEnemy(EnemyType.BARGEST, coordinate, area);
                    case 1 -> EntitySpawner.spawnEnemy(EnemyType.GHOST, coordinate, area);
                }
            }
        }, Config.DELAY_BETWEEN_ENEMIES_SPAWN);

        return FXGL.entityBuilder(data).buildAndAttach();
    }


    @Spawns(EntityType.Enemies.bargest)
    public Entity newBargest(SpawnData data) {
        Rectangle area = new Rectangle();
        EnemyImportance importance = data.get("importance");
        if (data.hasKey("area")) {
            area = data.get("area");
        }
        Point2D position = new Point2D(data.getX(), data.getY());
        BargestType bargestType = data.get(BargestType.BARGEST_TYPE);
        BargestComponent component = new BargestComponent(importance, bargestType, area, position);
        return FXGL.entityBuilder(data).type(EntityType.ENEMY).with(component).buildAndAttach();
    }

    @Spawns(EntityType.Enemies.ghost)
    public Entity newGhost(SpawnData data) {
        Rectangle area = new Rectangle();
        EnemyImportance importance = data.get("importance");
        if (data.hasKey("area")) {
            area = data.get("area");
        }
        Point2D position = new Point2D(data.getX(), data.getY());
        GhostComponent component = new GhostComponent(importance, area, position);
        return FXGL.entityBuilder(data).type(EntityType.ENEMY).with(component).buildAndAttach();
    }

    @Spawns(EntityType.Enemies.werewolf)
    public Entity newWerewolf(SpawnData data) {
        Rectangle area = new Rectangle();
        EnemyImportance importance = data.get("importance");
        if (data.hasKey("area")) {
            area = data.get("area");
        }
        Point2D position = new Point2D(data.getX(), data.getY());
        WerewolfComponent component = new WerewolfComponent(importance, area, position);
        return FXGL.entityBuilder(data).type(EntityType.ENEMY).with(component).buildAndAttach();
    }

    @Spawns(EntityType.Enemies.demon)
    public Entity newDemon(SpawnData data) {
        Rectangle area = new Rectangle();
        EnemyImportance importance = data.get("importance");
        if (data.hasKey("area")) {
            area = data.get("area");
        }
        Point2D position = new Point2D(data.getX(), data.getY());
        DemonComponent component = new DemonComponent(importance, area, position);
        return FXGL.entityBuilder(data).type(EntityType.ENEMY).with(component).buildAndAttach();
    }

    @Spawns(EntityType.chest)
    public static Entity newChest(SpawnData data) {
        BaseInventoryComponent grid = new MinableInventoryComponent(4, 4);
        grid.getModel().resetItems(
                // TODO loot generation
                new Owl().setCount(4),
                new WhiteHoney().setCount(2),
                new RaffardTheWhite().setCount(3),
                new MariborForest(),
                new MariborForest().setCount(2),
                new MariborForest().setCount(13)
        );
        return FXGL.entityBuilder(data).type(EntityType.CHEST).with(grid).build();
    }

    @Spawns(EntityType.arondit)
    public Entity newArondit(SpawnData data) {
        BaseInventoryComponent grid = new MinableInventoryComponent(4, 4);
        grid.getModel().resetItems(
                new Arondit(),
                new Scroll(
                        "Странный свиток",
                        "Прими меч в знак моей симпатии. Это был меч великого воина, он века лежал в глубинах, ожидая тебя",
                        "<Подпись неразборчива>"
                )
        );
        return FXGL.entityBuilder().type(EntityType.CHEST).with(grid).build();
    }

    @Spawns(EntityType.pirateChest)
    public Entity newPiratesChest(SpawnData data) {
        BaseInventoryComponent grid = new MinableInventoryComponent(4, 4);
        grid.getModel().resetItems(
                new DeadTreasure(),
                new Scroll(
                        "Пропахший ромом свиток",
                        "Мертвецы никогда не рассказывают сказок? Какой абсурд! Я сам лично проделал немалый путь, пока добрался до этих земель. В частности я - тот пират, который вернулся из мира мертвых. И все ради чего?? Ради того, чтобы погибнуть от лап какого-то странного зверя...",
                        "Что же, не скучайте. По крайней мере, капитан всегда остается со своим кораблем"
                )
        );
        return FXGL.entityBuilder().type(EntityType.CHEST).with(grid).build();
    }

    @Spawns(EntityType.ghostChest)
    public Entity newGhostChest(SpawnData data) {
        BaseInventoryComponent grid = new MinableInventoryComponent(4, 4);
        grid.getModel().resetItems(
                new MariborForest().setCount(20),
                new Owl().setCount(20),
                new RaffardTheWhite().setCount(20),
                new Smallow().setCount(20),
                new Thunder().setCount(20),
                new WhiteHoney().setCount(20),
                new Scroll(
                        "Странный свиток",
                        "Какая ирония... Они искали его всю свою земную жизнь, а в итоге что? В итоге оно поработило их. Оно жестоко, оно коварно, у него есть только один Властелин... ",
                        "\"Одно кольцо, чтоб править всеми,\n" +
                                "Оно главнее всех,\n" +
                                "Оно соберёт всех вместе\n" +
                                "И заключит во тьме.\""
                )
        );
        return FXGL.entityBuilder().type(EntityType.CHEST).with(grid).build();
    }

    @Spawns(EntityType.Removable.colliderToArondit)
    public Entity newRmColliderToArondit(SpawnData data) {
        return FXGL.entityBuilder(data).type(EntityType.RM_COLLIDER_TO_ARONDIT).with(new StaticColliderComponent(data)).build();
    }

    @Spawns(EntityType.Removable.WoodsToLake.t1)
    public Entity newRmToLakeWoods1(SpawnData data) {
        return FXGL.entityBuilder(data).type(EntityType.RM_WOODS_TO_LAKE_1).with(new StaticColliderComponent(data)).build();
    }

    @Spawns(EntityType.Removable.WoodsToLake.t2)
    public Entity newRmToLakeWoods2(SpawnData data) {
        return FXGL.entityBuilder(data).type(EntityType.RM_WOODS_TO_LAKE_2).with(new StaticColliderComponent(data)).build();
    }

    @Spawns(EntityType.Removable.WoodsToLake.t3)
    public Entity newRmToLakeWoods3(SpawnData data) {
        return FXGL.entityBuilder(data).type(EntityType.RM_WOODS_TO_LAKE_3).with(new StaticColliderComponent(data)).build();
    }

    @Spawns(EntityType.collider)
    public static Entity newCollider(SpawnData data) {
        return FXGL.entityBuilder(data).with(new StaticColliderComponent(data)).build();
    }

    @Spawns(EntityType.environment)
    public Entity newEnvironment(SpawnData data) {
        return FXGL.entityBuilder(data).build();
    }
}
