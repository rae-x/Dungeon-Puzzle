package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;
import unsw.dungeon.Boulder;

import org.json.JSONArray;
import org.json.JSONObject;

public class WallTest{
    // X
    // B
    //XOX
    //construct two walls next to the player,put a boulder above player.
    JSONObject jsonplayer = new JSONObject()
    .put("x","1")
    .put("y","0")
    .put("type","player");
    
    JSONObject jsonwall = new JSONObject()
    .put("x", "0")
    .put("y", "0")
    .put("type", "wall");

    JSONObject jsonwall2 = new JSONObject()
    .put("x", "2")
    .put("y", "0")
    .put("type", "wall");

    JSONObject jsonwall3 = new JSONObject()
    .put("x", "1")
    .put("y", "2")
    .put("type", "wall");

    JSONObject jsonboulder = new JSONObject()
    .put("x", "1")
    .put("y", "1")
    .put("type", "boulder");

    JSONArray jsonarray = new JSONArray()
    .put(jsonplayer)
    .put(jsonwall)
    .put(jsonwall2)
    .put(jsonwall3)
    .put(jsonboulder);
    
    JSONObject jsongoal = new JSONObject()
    .put("goal", "boulders");

    JSONObject jsonString = new JSONObject()
    .put("width", "3")
    .put("height", "3")
    .put("entities", jsonarray)
    .put("goal-condition", jsongoal);
    
    Dungeon dungeon = new DungeonLoader(jsonString).load();
    Player player = dungeon.getPlayer();
    Boulder boulder = (Boulder) dungeon.getEntity(1, 1);

    @Test 
    //test for wall's initial properties
    void InitialWallTest(){
        //assuming the player has walls on left and right..
        player.move(Movement.MOVE_LEFT);
        player.move(Movement.MOVE_RIGHT);
        assert(player.getX() == 1 && player.getY() == 0);
    }

    @Test 
    //test for walls blocking the boulder pushed by player
    void WallnBoulderTest(){
        player.move(Movement.MOVE_UP);
        //the boulder is not pushed since theres a wall blocking it.
        assert(player.getX() == 1 && player.getY() == 0);
        assert(boulder.getX() == 1 && boulder.getY() == 1);
    }
}
