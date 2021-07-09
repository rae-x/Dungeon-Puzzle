package test;

import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.json.JSONObject;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;

public class PortalPlayerTest {
    JSONObject portal1JSON = new JSONObject()
    .put("x", 1)
    .put("y", 0)
    .put("id", 0)
    .put("type", "portal");

    JSONObject portal2JSON = new JSONObject()
    .put("x", 2)
    .put("y", 1)
    .put("id", 0)
    .put("type", "portal");

    JSONObject playerJSON = new JSONObject()
    .put("x", 0)
    .put("y", 0)
    .put("type", "player");

    JSONObject exitJSON = new JSONObject()
    .put("x", 2)
    .put("y", 2)
    .put("type", "exit");

    JSONArray entitiesJSON = new JSONArray()
    .put(portal1JSON)
    .put(portal2JSON)
    .put(playerJSON)
    .put(exitJSON);

    JSONObject goalExit = new JSONObject()
    .put("goal", "exit");

    JSONObject dungeonJSON = new JSONObject()
    .put("width", "3")
    .put("height", "3")
    .put("entities", entitiesJSON)
    .put("goal-condition", goalExit);

    Dungeon dungeon = new DungeonLoader(dungeonJSON).load();
    Player player = dungeon.getPlayer();

    @Test
    void testPortal() {
        player.move(Movement.MOVE_RIGHT);
        assert(player.getX() == 2 && player.getY() == 1);
        player.move(Movement.MOVE_LEFT);
        player.move(Movement.MOVE_RIGHT);
        assert(player.getX() == 1 && player.getY() == 0);
    }
}