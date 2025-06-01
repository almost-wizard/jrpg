package ru.rsreu.gorobchenko.project.factory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import ru.rsreu.gorobchenko.project.components.character.ai.attack.Attack;
import ru.rsreu.gorobchenko.project.components.character.parameters.CharacterParametersComponent;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyImportance;
import ru.rsreu.gorobchenko.project.entity.character.enemy.EnemyType;
import ru.rsreu.gorobchenko.project.entity.character.enemy.bargest.BargestType;
import ru.rsreu.gorobchenko.project.entity.character.player.PlayerManager;

public class EntitySpawner {
    private static int necessaryEnemiesCount = 0;

    public static int getNecessaryEnemiesCount() {
        return necessaryEnemiesCount;
    }

    public static void incrementNecessaryEnemiesCount() {
        necessaryEnemiesCount += 1;
    }

    public static void decrementNecessaryEnemiesCount() {
        necessaryEnemiesCount -= 1;
    }

    public static void resetNecessaryEnemiesCount() {
        necessaryEnemiesCount = 0;
    }

    public static void checkWin() {
        if (necessaryEnemiesCount <= 0) {
            PlayerManager.getInstance().getPlayerComponent().win();
        }
    }

    public static Entity spawnEnemy(EnemyType enemyType, Point2D coordinate, Rectangle area) {
        return EntitySpawner._spawnEnemy(EnemyImportance.IMPORTANT, enemyType, coordinate, area);
    }

    public static Entity spawnEnemy(EnemyImportance importance, EnemyType enemyType, Point2D coordinate, Rectangle area) {
        return EntitySpawner._spawnEnemy(importance, enemyType, coordinate, area);
    }

    private static Entity _spawnEnemy(EnemyImportance importance, EnemyType enemyType, Point2D coordinate, Rectangle area) {
        if (importance == EnemyImportance.IMPORTANT) {
            incrementNecessaryEnemiesCount();
        }

        SpawnData data = new SpawnData(coordinate);
        data.put("area", area);
        data.put("importance", importance);
        if (enemyType == EnemyType.BARGEST) {
            data.put(BargestType.BARGEST_TYPE, BargestType.RANDOM);
        }
        return enemyType.spawn(data);
    }

    public static Entity spawnPlayer(int x, int y) {
        return FXGL.getGameWorld().spawn(EntityType.player, new SpawnData(x, y));
    }

    public static Entity spawnNpc(int x, int y) {
        return FXGL.getGameWorld().spawn(EntityType.npc, new SpawnData(x, y));
    }

    public static Entity spawnAttackTrail(double x, double y, Attack attack, CharacterParametersComponent attackerParameters) {
        SpawnData data = new SpawnData(x, y);
        data.put(Attack.class.getName().toLowerCase(), attack);
        data.put(CharacterParametersComponent.class.getName().toLowerCase(), attackerParameters);
        return FXGL.getGameWorld().spawn(EntityType.attackTrail, data);
    }
}
