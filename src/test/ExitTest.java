package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;
import unsw.dungeon.Exit;
import org.json.JSONArray;
import org.json.JSONObject;

public class ExitTest {

    JSONObject jsonplayer = new JSONObject()
    .put("x","1")
    .put("y","0")
    .put("type","player");
    
    JSONObject jsonexit = new JSONObject()
    .put("x", "0")
    .put("y", "0")
    .put("type", "exit");

    JSONArray jsonarray = new JSONArray()
    .put(jsonplayer)
    .put(jsonexit);
    
    JSONObject jsongoal = new JSONObject()
    .put("goal", "exit");

    JSONObject jsonString = new JSONObject()
    .put("width", "3")
    .put("height", "3")
    .put("entities", jsonarray)
    .put("goal-condition", jsongoal);
    
    Dungeon dungeon = new DungeonLoader(jsonString).load();
    Player player = dungeon.getPlayer();
    Exit exit = (Exit) dungeon.getEntity(0, 0);

    
    @Test 
    void ExitInitialTest(){
        assert(dungeon.cleared().get() == false);
    }

    @Test 
    void ExitGoalTest(){
        player.move(Movement.MOVE_LEFT);
        assert(dungeon.cleared().get() == true);
    }
}