package test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;

public class PlayerTest {

    JSONObject playerJSON = new JSONObject()
    .put("x", 1)
    .put("y", 1)
    .put("type", "player");

    JSONObject exitJSON = new JSONObject()
    .put("x", 0)
    .put("y", 0)
    .put("type", "exit");

    JSONObject jsonKey = new JSONObject()
    .put("x", "3")
    .put("y", "2")
    .put("id", "0")
    .put("type", "key");

    JSONObject jsonSword = new JSONObject()
    .put("x", "3")
    .put("y", "3")
    .put("id", "1")
    .put("type", "sword");

    JSONObject jsonTreasure = new JSONObject()
    .put("x", "3")
    .put("y", "4")
    .put("id", "2")
    .put("type", "treasure");

    JSONObject jsonPotion = new JSONObject()
    .put("x", "3")
    .put("y", "5")
    .put("id", "3")
    .put("type", "invincibility");

    JSONObject jsonEnemy = new JSONObject()
    .put("x", 4)
    .put("y", 3)
    .put("type", "enemy");

    JSONArray entitiesJSON = new JSONArray()
    .put(playerJSON)
    .put(exitJSON)
    .put(jsonKey)
    .put(jsonSword)
    .put(jsonTreasure)
    .put(jsonEnemy)
    .put(jsonPotion);

    JSONObject enemiesGoal = new JSONObject()
    .put("goal", "enemies");

    JSONObject dungeonJSON = new JSONObject()
    .put("width", "8")
    .put("height", "8")
    .put("entities", entitiesJSON)
    .put("goal-condition", enemiesGoal);
    
    Dungeon dungeon = new DungeonLoader(dungeonJSON).load();
    Player player = dungeon.getPlayer();

    JSONObject treasureGoal = new JSONObject()
    .put("goal", "treasure");
    
    JSONObject dungeonJSON2 = new JSONObject()
    .put("width", "8")
    .put("height", "8")
    .put("entities", entitiesJSON)
    .put("goal-condition", treasureGoal);
    
    Dungeon dungeon2 = new DungeonLoader(dungeonJSON2).load();
    Player player2 = dungeon2.getPlayer();

    JSONObject exitGoal = new JSONObject()
    .put("goal", "exit");
    
    JSONObject dungeonJSON3 = new JSONObject()
    .put("width", "8")
    .put("height", "8")
    .put("entities", entitiesJSON)
    .put("goal-condition", exitGoal);
    
    Dungeon dungeon3 = new DungeonLoader(dungeonJSON3).load();
    Player player3 = dungeon3.getPlayer();

    JSONObject jsonFloorSwitch = new JSONObject()
    .put("x","3")
    .put("y","1")
    .put("type","switch");

    JSONObject jsonBoulder = new JSONObject()
    .put("x","2")
    .put("y","1")
    .put("type","boulder");

    JSONArray entitiesJSON2 = new JSONArray()
    .put(playerJSON)
    .put(jsonFloorSwitch)
    .put(jsonBoulder);

    JSONObject boulderGoal = new JSONObject()
    .put("goal", "boulders");
    
    JSONObject dungeonJSON4 = new JSONObject()
    .put("width", "4")
    .put("height", "4")
    .put("entities", entitiesJSON2)
    .put("goal-condition", boulderGoal);
    
    Dungeon dungeon4 = new DungeonLoader(dungeonJSON4).load();
    Player player4 = dungeon4.getPlayer();

    @Test
    void testPlayerMovement() {
        player.move(Movement.MOVE_RIGHT);
        assert(player.getX() == 2 && player.getY() == 1);
        player.move(Movement.MOVE_LEFT);
        assert(player.getX() == 1 && player.getY() == 1);

        player.move(Movement.MOVE_UP);
        assert(player.getX() == 1 && player.getY() == 0);
        player.move(Movement.MOVE_DOWN);
        assert(player.getX() == 1 && player.getY() == 1);
    }

    @Test
    void playerExitTest() {
        assert(dungeon3.cleared().get() == false);
        player3.move(Movement.MOVE_LEFT);
        player3.move(Movement.MOVE_UP);
        assert(dungeon3.cleared().get() == true);
    }

    @Test
    void keyPickupTest() {
        assert(player.getKey() == null);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        assert(player.getKey() != null);
    }

    @Test
    void swordPickupTest() {
        //assert(player.getSword() == null);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        //assert(player.getSword() != null);
    }

    @Test
    void enemyGoalTest() {
        //assert(player.getSword() == null);
        assert(dungeon.cleared().get() == false);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        
        player.swingSword();
        assert(dungeon.cleared().get() == true);
    }

    @Test
    void treasurePickupTest() { 
        assert(player.getTreasureCount() == 0);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        assert(player.getTreasureCount() == 1);
        
    }

    @Test
    void treasureGoalTest() {
        assert(player2.getTreasureCount() == 0);
        assert(dungeon2.cleared().get() == false);
        player2.move(Movement.MOVE_DOWN);
        player2.move(Movement.MOVE_DOWN);
        player2.move(Movement.MOVE_DOWN);
        player2.move(Movement.MOVE_RIGHT);
        player2.move(Movement.MOVE_RIGHT);
        assert(dungeon2.cleared().get() == true);
    }

    @Test
    void potionPickupTest() {
        //assert(player.getInvincibleTime() == 0);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_RIGHT);
        //assert(player.getInvincibleTime() > 0);
    }

    @Test
    void boulderGoalTest() {
        assert(dungeon4.cleared().get() == false);
        // Move boulder
        player4.move(Movement.MOVE_RIGHT);
        player4.move(Movement.MOVE_RIGHT);
        assert(dungeon4.cleared().get() == true);
    }
}
