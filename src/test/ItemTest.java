package test;

import org.junit.jupiter.api.Test;
import unsw.dungeon.Dungeon;
import unsw.dungeon.DungeonLoader;
import unsw.dungeon.Sword;
import unsw.dungeon.Player;
import unsw.dungeon.Enemy;
import unsw.dungeon.Key;
import unsw.dungeon.Treasure;
import unsw.dungeon.Potion;
import unsw.dungeon.Movement;
import static org.junit.jupiter.api.Assertions.assertEquals;    
import org.json.JSONObject;
import org.json.JSONArray;

public class ItemTest{
    //Test the behaviours of sword, potion, key, treasure..
    // 
    // S2
    //TSP
    //KPE

    JSONObject jsonkey = new JSONObject()
    .put("x", "0")
    .put("y", "0")
    .put("id", "0")
    .put("type", "key");

    JSONObject jsonkey2 = new JSONObject()
    .put("x", "2")
    .put("y", "2")
    .put("id", "1")
    .put("type", "key");

    JSONObject jsonplayer = new JSONObject()
    .put("x", "1")
    .put("y", "0")
    .put("type", "player");

    JSONObject jsonsword = new JSONObject()
    .put("x", "1")
    .put("y", "1")
    .put("type", "sword");
    
    JSONObject jsonsword2 = new JSONObject()
    .put("x", "1")
    .put("y", "2")
    .put("type", "sword");

    JSONObject jsonenemy = new JSONObject()
    .put("x", "0")
    .put("y", "2")
    .put("type", "enemy");

    JSONObject jsonpotion = new JSONObject()
    .put("x", "2")
    .put("y", "1")
    .put("type", "invincibility");

    JSONObject jsonpotion2 = new JSONObject()
    .put("x", "3")
    .put("y", "1")
    .put("type", "invincibility");

    JSONObject jsontreasure = new JSONObject()
    .put("x", "0")
    .put("y", "1")
    .put("type", "treasure");
    
    JSONArray jsonArray = new JSONArray()
    .put(jsonkey)
    .put(jsonkey2)
    .put(jsonplayer)
    .put(jsonsword)
    .put(jsonsword2)
    .put(jsonenemy)
    .put(jsonpotion)
    .put(jsonpotion2)
    .put(jsontreasure);
    
    JSONObject jsongoal = new JSONObject()
    .put("goal","treasure");

    JSONObject jsonString = new JSONObject()
    .put("width", "5")
    .put("height", "5")
    .put("entities",jsonArray)
    .put("goal-condition", jsongoal);    
    
    Dungeon dungeon = new DungeonLoader(jsonString).load();
    Player player = dungeon.getPlayer();
    Sword sword = (Sword) dungeon.getEntity(1, 1);
    Sword sword2 = (Sword) dungeon.getEntity(1, 2);
    Enemy enemy = (Enemy) dungeon.getEntity(0, 2);
    Key key = (Key) dungeon.getEntity(0, 0);
    Key key2 = (Key) dungeon.getEntity(2, 2);
    Treasure treasure = (Treasure) dungeon.getEntity(0, 1);
    Potion potion = (Potion) dungeon.getEntity(2, 1);
    Potion potion2 = (Potion) dungeon.getEntity(3, 1);

    @Test 
    //test for sword's initial properties
    public void InitialSwordTest(){
        //assert(player.getSword() == null);
        //assert(sword.getDurability() == 5);
    }

    @Test 
    //test for player can only have one sword
    public void OneSwordTest(){
        //sword.pickup(player);
        assert(dungeon.getEntity(1, 1) == null);
        //assertEquals(player.getSword(), sword);
        //player should have have one sword at a time
        //sword2 should not be picked up.
        //sword2.pickup(player);
        assert(dungeon.getEntity(1, 2) != null);
        //assert(player.getSword() == sword);
    }

    @Test
    //test for sword breaking once durability reaches 5
    public void SwordBreakingTest(){
        //sword.pickup(player);
        assert(dungeon.getEntity(1, 1) == null);
        //assert(player.getSword() == sword);
        for(int i = 0; i <= 5; i++){
            player.swingSword();
        }
        //sword breaks and not no longer in players hand
        //assert(player.getSword() == null);
    }

    @Test
    //test for enemy death once the sword swinged at the enemy
    public void SwordKillEnemyTest(){
        //sword.pickup(player);
        assert(dungeon.getEntity(1, 1) == null);
        //assert(player.getSword() == sword);
        player.move(Movement.MOVE_DOWN);
        player.swingSword();
        assert(dungeon.getEnemies().isEmpty() == true);
    }

    @Test
    public void KeyInitialTest(){
        assert(player.getKey() == null);
        assert(key.getX() == 0 && key.getY() == 0);
        assert(key.getActive() == true);
    }

    @Test 
    public void KeyHasKeyTest(){
        //key.pickup(player);
        assert(player.getKey() != null);
    }

    @Test
    public void OneKeyTest(){
        //key.pickup(player);
        assert(player.getKey().getKeyID() == key.getKeyID());
        //key2.pickup(player);
        assert(player.getKey().getKeyID() != key2.getKeyID());
        assert(key2.getActive() == true);
        assert(player.getKey().getKeyID() == key.getKeyID());
    }

    @Test
    public void TreasureInitialTest(){
        assertEquals(player.getTreasureCount(), 0);
        assert(treasure.getX() == 0 && treasure.getY() == 1);
        assert(treasure.getActive() == true);
    }

    @Test
    public void TreasurePickupTest(){
        //treasure.pickup(player);
        assertEquals(player.getTreasureCount(), 1);
        assert(treasure.getActive() == false);
    }

    @Test
    public void PotionInitialTest(){
        //assert(player.getInvincibleTime() == 0);
        assert(potion.getActive() == true);
        assert(potion.getX() == 2 && potion.getY() == 1);
    }

    @Test
    public void PotionConsumedTest(){
        //potion.pickup(player);
        //assert(player.getInvincibleTime() > 0);
        assert(potion.getActive() == false);
    }

    @Test
    public void PotionExpireTest(){
        //potion.pickup(player);
        //assert(player.getInvincibleTime() > 0);
        assert(potion.getActive() == false);
        player.move(Movement.MOVE_UP);
        //assert(player.getInvincibleTime() == 9);
    }

    @Test
    public void OnePotionTest(){
        //potion.pickup(player);
        //assert(player.getInvincibleTime() > 0);
        assert(potion.getActive() == false);
        //potion2.pickup(player);
        assert(potion2.getActive() == true);
    }
}
