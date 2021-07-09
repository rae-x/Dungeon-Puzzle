package test;

import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.json.JSONObject;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Enemy;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;

public class EnemyMovementTest {

    JSONObject playerJSON = new JSONObject()
    .put("x", 3)
    .put("y", 0)
    .put("type", "player");

    JSONObject enemyJSON = new JSONObject()
    .put("x", 3)
    .put("y", 3)
    .put("type", "enemy");

    JSONObject w1JSON = new JSONObject()
    .put("x", 1)
    .put("y", 1)
    .put("type", "wall");

    JSONObject w2JSON = new JSONObject()
    .put("x", 2)
    .put("y", 1)
    .put("type", "wall");

    JSONObject w3JSON = new JSONObject()
    .put("x", 3)
    .put("y", 1)
    .put("type", "wall");

    JSONObject w4JSON = new JSONObject()
    .put("x", 4)
    .put("y", 1)
    .put("type", "wall");

    JSONObject w5JSON = new JSONObject()
    .put("x", 5)
    .put("y", 1)
    .put("type", "wall");

    JSONObject w6JSON = new JSONObject()
    .put("x", 5)
    .put("y", 2)
    .put("type", "wall");

    JSONObject w7JSON = new JSONObject()
    .put("x", 5)
    .put("y", 3)
    .put("type", "wall");

    JSONObject w8JSON = new JSONObject()
    .put("x", 5)
    .put("y", 4)
    .put("type", "wall");

    JSONObject w9JSON = new JSONObject()
    .put("x", 5)
    .put("y", 5)
    .put("type", "wall");

    JSONObject exitJSON = new JSONObject()
    .put("x", 4)
    .put("y", 4)
    .put("type", "exit");

    JSONArray entitiesJSON = new JSONArray()
    .put(playerJSON)
    .put(enemyJSON)
    .put(w1JSON)
    .put(w2JSON)
    .put(w3JSON)
    .put(w4JSON)
    .put(w5JSON)
    .put(w6JSON)
    .put(w7JSON)
    .put(w8JSON)
    .put(w9JSON)
    .put(exitJSON);

    JSONObject goalExit = new JSONObject()
    .put("goal", "exit");

    JSONObject dungeonJSON = new JSONObject()
    .put("width", 7)
    .put("height", 7)
    .put("entities", entitiesJSON)
    .put("goal-condition", goalExit);

    Dungeon dungeon = new DungeonLoader(dungeonJSON).load();
    Player player = dungeon.getPlayer();
    Enemy enemy = (Enemy) dungeon.getEntity(3, 3);

    @Test
    void testPlayerTouchEnemy() {
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 3 && enemy.getY() == 2);
        player.move(Movement.MOVE_LEFT);
        assert(enemy.getX() == 3 && enemy.getY() == 2);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        assert(enemy.getX() == 4 && enemy.getY() == 2);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        assert(enemy.getX() == 4 && enemy.getY() == 3);
        player.move(Movement.MOVE_DOWN);
        assert(enemy.getX() == 4 && enemy.getY() == 4);
        player.move(Movement.MOVE_DOWN);
        assert(enemy.getX() == 4 && enemy.getY() == 5);
    }
}