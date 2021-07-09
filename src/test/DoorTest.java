package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Movement;
import unsw.dungeon.Player;
import unsw.dungeon.Door;
import unsw.dungeon.Key;

import org.json.JSONArray;
import org.json.JSONObject;

public class DoorTest{
    JSONObject jsonplayer = new JSONObject()
    .put("x","1")
    .put("y","1")
    .put("type","player");

    JSONObject jsondoor = new JSONObject()
    .put("x", "1")
    .put("y", "0")
    .put("id", "0")
    .put("type", "door");

    JSONObject jsonkey = new JSONObject()
    .put("x", "0")
    .put("y", "0")
    .put("id", "0")
    .put("type", "key");

    JSONObject jsonkey2 = new JSONObject()
    .put("x", "0")
    .put("y", "2")
    .put("id", "1")
    .put("type", "key");

    JSONArray jsonarray = new JSONArray()
    .put(jsonplayer)
    .put(jsonkey)
    .put(jsonkey2)
    .put(jsondoor);
    
    JSONObject jsongoal = new JSONObject()
    .put("goal", "exit");

    JSONObject jsonString = new JSONObject()
    .put("width", "3")
    .put("height", "3")
    .put("entities", jsonarray)
    .put("goal-condition", jsongoal);
    
    Dungeon dungeon = new DungeonLoader(jsonString).load();
    Player player = dungeon.getPlayer();
    Door door = (Door) dungeon.getEntity(1, 0);
    Key key = (Key) dungeon.getEntity(0, 0);
    Key key2 = (Key) dungeon.getEntity(0, 2);

    @Test 
    void InitialDoorTest(){
        assert(door.opened().get() == false);
        player.move(Movement.MOVE_UP);
        assert(player.getX() == 1 && player.getY() == 1);
    }

    @Test
    void OpenDoorTest(){
        //key.pickup(player);
        //door.open(player);
        assert(door.opened().get() == true);
        assert(player.getKey() == null);
        player.move(Movement.MOVE_UP);
        assert(player.getX() == 1 && player.getY() == 0);
    }

    @Test
    void MismatchKeyDoorIDTest(){
        //key2.pickup(player);
        //door.open(player);
        assert(door.opened().get() == false);
        assert(player.getKey() != null);
        player.move(Movement.MOVE_UP);
        assert(player.getX() == 1 && player.getY() == 1);
        player.destroyKey();
        //key.pickup(player);
        //door.open(player);
        assert(door.opened().get() == true);
    }
}
