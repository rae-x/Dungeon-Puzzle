package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.FloorSwitch;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;
import unsw.dungeon.Boulder;
import org.json.JSONArray;
import org.json.JSONObject;

public class FloorSwitchTest {
    //
    //
    // SBP
    JSONObject jsonfloorswitch = new JSONObject()
    .put("x","1")
    .put("y","0")
    .put("type","switch");

    JSONObject jsonplayer = new JSONObject()
    .put("x","3")
    .put("y","0")
    .put("type","player");

    JSONObject jsonboulder = new JSONObject()
    .put("x","2")
    .put("y","0")
    .put("type","boulder");

    JSONArray jsonArray = new JSONArray()
    .put(jsonfloorswitch)
    .put(jsonplayer)
    .put(jsonboulder);

    JSONObject jsongoal = new JSONObject()
    .put("goal", "boulders");

    JSONObject jsonString = new JSONObject()
    .put("width", "3")
    .put("height", "3")
    .put("entities", jsonArray)
    .put("goal-condition", jsongoal);

    Dungeon dungeon = new DungeonLoader(jsonString).load();
    Player player = dungeon.getPlayer();
    Boulder boulder = (Boulder) dungeon.getEntity(2, 0);
    FloorSwitch floorswitch = (FloorSwitch) dungeon.getFloorSwitch(1, 0);

    @Test 
    void InitialFloorSwitchTest(){
        //assert(floorswitch.getEnabled() == false);
    }

    @Test
    void BoulderOnSwitchTest(){
        //assert(floorswitch.getEnabled() == false);
        player.move(Movement.MOVE_LEFT);
        //assert(floorswitch.getEnabled() == true);
    }

    @Test
    void BoulderOffSwitchTest(){
        //assert(floorswitch.getEnabled() == false);
        player.move(Movement.MOVE_LEFT);
        //assert(floorswitch.getEnabled() == true);
        player.move(Movement.MOVE_LEFT);
        player.move(Movement.MOVE_LEFT);
        //assert(floorswitch.getEnabled() == false);
    }
}