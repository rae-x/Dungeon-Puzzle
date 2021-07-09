package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Player;
import unsw.dungeon.Wall;
import unsw.dungeon.Boulder;
import unsw.dungeon.Sword;
import unsw.dungeon.Movement;
import org.json.JSONArray;
import org.json.JSONObject;

public class BoulderTest{
    // F
    // B
    // O
    //construct two walls next to the player,put a boulder above player.
    JSONObject jsonfloorswitch = new JSONObject()
    .put("x","1")
    .put("y","2")
    .put("type","switch");

    JSONObject jsonboulder = new JSONObject()
    .put("x","1")
    .put("y","1")
    .put("type","boulder");

    JSONObject jsonboulder2 = new JSONObject()
    .put("x","2")
    .put("y","0")
    .put("type","boulder");

    JSONObject jsonsword = new JSONObject()
    .put("x","3")
    .put("y","0")
    .put("type","sword");

    JSONObject jsonplayer = new JSONObject()
    .put("x","1")
    .put("y","0")
    .put("type","player");
    
    JSONObject jsonwall = new JSONObject()
    .put("x","2")
    .put("y","1")
    .put("type", "wall");

    JSONArray jsonArray = new JSONArray()
    .put(jsonplayer)
    .put(jsonboulder)
    .put(jsonboulder2)
    .put(jsonsword)
    .put(jsonwall)
    .put(jsonfloorswitch);

    JSONObject jsongoal = new JSONObject()
    .put("goal", "boulders");

    JSONObject jsonString = new JSONObject()
    .put("width", "4")
    .put("height", "4")
    .put("entities", jsonArray)
    .put("goal-condition", jsongoal);

    Dungeon dungeon = new DungeonLoader(jsonString).load();
    Player player = dungeon.getPlayer();
    Boulder boulder = (Boulder) dungeon.getEntity(1, 1);
    Boulder boulder2 = (Boulder) dungeon.getEntity(2, 0);
    Sword sword = (Sword) dungeon.getEntity(3, 0);
    Wall wall = (Wall) dungeon.getEntity(2, 1);

    @Test 
    //test for pushing boulder 
    void PushBoulderTest(){
        player.move(Movement.MOVE_DOWN);
        assert(player.getX() == 1 && player.getY() == 0);
        assert(boulder.getX() == 1 && boulder.getY() == 2);
    }

    @Test 
    //test for pushing a blocked boulder 
    void BoulderBlockedTest(){
        player.move(Movement.MOVE_LEFT);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        assert(player.getX() == 0 && player.getY() == 1);
        assert(boulder.getX() == 1 && boulder.getY() == 1);
    }

    @Test 
    //test for pushing a boulder towards an item
    void BoulderItemCollisionTest(){
        player.move(Movement.MOVE_RIGHT);
        assert(player.getX() == 1 && player.getY() == 0);
        assert(boulder2.getX() == 3 && boulder2.getY() == 0);
    }

    @Test 
    //test for pushing a boulder up
    void PushBouldeUpTest(){
        player.move(Movement.MOVE_LEFT);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_DOWN);
        player.move(Movement.MOVE_RIGHT);
        player.move(Movement.MOVE_UP);
        assert(player.getX() == 1 && player.getY() == 2);
        assert(boulder.getX() == 1 && boulder.getY() == 0);
    }
}
