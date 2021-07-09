package test;

import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.json.JSONObject;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Enemy;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;

public class EnemyTest {

    JSONObject playerJSON = new JSONObject()
    .put("x", 6)
    .put("y", 0)
    .put("type", "player");

    JSONObject enemyJSON = new JSONObject()
    .put("x", 2)
    .put("y", 0)
    .put("type", "enemy");

    JSONObject exitJSON = new JSONObject()
    .put("x", 0)
    .put("y", 0)
    .put("type", "exit");

    JSONArray entitiesJSON = new JSONArray()
    .put(playerJSON)
    .put(enemyJSON)
    .put(exitJSON);

    JSONObject goalExit = new JSONObject()
    .put("goal", "exit");

    JSONObject dungeonJSON = new JSONObject()
    .put("width", "7")
    .put("height", "1")
    .put("entities", entitiesJSON)
    .put("goal-condition", goalExit);

    Dungeon dungeon = new DungeonLoader(dungeonJSON).load();
    Player player = dungeon.getPlayer();
    Enemy enemy = (Enemy) dungeon.getEntity(2, 0);

    @Test
    void testPlayerTouchEnemy() {
        assert(enemy.getX() == 2 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 3 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 4 && enemy.getY() == 0);
        assert(player.getActive() == false);
    }

    @Test
    void testEnemyTouchPlayer() {
        assert(enemy.getX() == 2 && enemy.getY() == 0);
        player.move(Movement.MOVE_RIGHT);
        assert(enemy.getX() == 3 && enemy.getY() == 0);
        player.move(Movement.MOVE_RIGHT);
        assert(enemy.getX() == 4 && enemy.getY() == 0);
        player.move(Movement.MOVE_RIGHT);
        assert(enemy.getX() == 5 && enemy.getY() == 0);
        player.move(Movement.MOVE_RIGHT);
        assert(player.getActive() == false);
    }

    @Test
    void testEnemyTouchPlayerDeathByInvincibility() {
        //player.setInvincibleTime();
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 1 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 0 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 0 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 0 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 0 && enemy.getY() == 0);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getActive() == false);
    }
}