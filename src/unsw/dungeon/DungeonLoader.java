package unsw.dungeon;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Loads a dungeon from a .json file.
 *
 * By extending this class, a subclass can hook into entity creation. This is
 * useful for creating UI elements with corresponding entities.
 *
 * @author Robert Clifton-Everest
 *
 */
public class DungeonLoader {

    private JSONObject json;

    public DungeonLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("dungeons/" + filename)));
    }

    public DungeonLoader(JSONObject json){
        this.json = json;
    }

    /**
     * Parses the JSON to create a dungeon.
     * @return - The Dungeon object created from the JSON.
     */
    public Dungeon load() {
        int width = json.getInt("width");
        int height = json.getInt("height");

        Task task = collectGoals(json.getJSONObject("goal-condition"), null);

        Dungeon dungeon = new Dungeon(width, height, task);
        List<Portal> portals = new ArrayList<>();
        List<Boulder> boulders = new ArrayList<>();
        List<FloorSwitch> switches = new ArrayList<>();

        JSONArray jsonEntities = json.getJSONArray("entities");

        for (int i = 0; i < jsonEntities.length(); i++) {
            loadEntity(dungeon, jsonEntities.getJSONObject(i), portals, boulders,
                switches);
        }
        matchPortals(portals);
        initialiseSwitches(switches, boulders);

        return dungeon;
    }

    /**
     * Collect the list of goals from the json and returns the root element of the Tasks.
     * @return Return the list of goals.
     */
    private Task collectGoals(JSONObject goalsJSON, Task task) {
        String goal = goalsJSON.getString("goal");
    
        if (goal.equals("AND")) {
            AndGoal andGoal = new AndGoal();
            JSONArray taskArray = goalsJSON.getJSONArray("subgoals");
            andGoal.setLeft(collectGoals(taskArray.getJSONObject(0), andGoal.getLeft()));
            andGoal.setRight(collectGoals(taskArray.getJSONObject(1), andGoal.getRight()));
            task = andGoal;
        } else if (goal.equals("OR")) {
            OrGoal orGoal = new OrGoal();
            JSONArray taskArray = goalsJSON.getJSONArray("subgoals");
            orGoal.setLeft(collectGoals(taskArray.getJSONObject(0), orGoal.getLeft()));
            orGoal.setRight(collectGoals(taskArray.getJSONObject(1), orGoal.getRight()));
            task = orGoal;
        } else {
            task = new SubGoal(goal);
        }
        
        return task;
    }

    /**
     * Load entities from the json into the dungeon.
     * @param dungeon The dungeon for entities to be loaded into.
     * @param json The json to read from.
     * @param portals List of portals in the dungeon.
     * @param boulders List of boulders in the dungeon.
     * @param switches List of floor switches in the dungeon.
     */
    private void loadEntity(Dungeon dungeon, JSONObject json, List<Portal> portals,
            List<Boulder> boulders, List<FloorSwitch> switches) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player(dungeon, x, y);
            dungeon.setPlayer(player);
            entity = player;
            break;
        case "wall":
            Wall wall = new Wall(x, y);
            entity = wall;
            break;
        case "portal":
            int portalId = json.getInt("id");
            Portal portal = new Portal(x, y, portalId);
            portals.add(portal);
            entity = portal;
            break;
        case "door":
            int doorId = json.getInt("id");
            Door door = new Door(x, y, doorId);
            entity = door;
            break;
        case "key":
            int keyId = json.getInt("id");
            Key key = new Key(dungeon, x, y, keyId);
            entity = key;
            break;
        case "treasure":
            Treasure treasure = new Treasure(dungeon, x, y);
            entity = treasure;
            break;
        case "boulder":
            Boulder boulder = new Boulder(dungeon, x, y);
            boulders.add(boulder);
            entity = boulder;
            break;
        case "switch":
            FloorSwitch floorSwitch = new FloorSwitch(dungeon, x, y);
            switches.add(floorSwitch);
            entity = floorSwitch;
            dungeon.setNumFloorSwitches(dungeon.getNumFloorSwitches() + 1);
            break;
        case "exit":
            Exit exit = new Exit(dungeon, x, y);
            entity = exit;
            break;
        case "sword":
            Sword sword = new Sword(dungeon, x, y);
            entity = sword;
            break;
        case "enemy":
            Enemy enemy = new Enemy(dungeon, x, y);
            entity = enemy;
            dungeon.addEnemy(enemy);
            break;
        case "invincibility":
            Potion potion = new Potion(dungeon, x, y);
            entity = potion;
            break;
        case "trap":
            Trap trap = new Trap(x, y);
            entity = trap;
            break;
        case "health_potion":
            HealthPotion healthPotion = new HealthPotion(dungeon, x, y);
            entity = healthPotion;
            break;
        }

        dungeon.addEntity(entity);
    }

    /**
     * If two portals have the same id, then set them to be matching.
     * @param portals List of portals in the dungeon.
     */
    private void matchPortals(List<Portal> portals) {
        for (Portal portal1: portals) {
            for (Portal portal2: portals) {
                if (portal1 == portal2)
                    continue;
                if (portal1.getID() == portal2.getID()) {
                    portal1.setMatchingPortal(portal2);
                    portal2.setMatchingPortal(portal1);
                    break;
                }
            }
        }
    }

    /**
     * If a boulder is on a floor switch, the floor switch will be activated.
     * @param switches List of floor switches in the dungeon.
     * @param boulders List of boulders in the dungeon.
     */
    private void initialiseSwitches(List<FloorSwitch> switches, List<Boulder> boulders) {
        for (FloorSwitch floorSwitch: switches) {
            for (Boulder boulder: boulders) {
                if (floorSwitch.getX() == boulder.getX() &&
                        floorSwitch.getY() == boulder.getY()) {
                    floorSwitch.onTouch(boulder);
                }
            }
        }
    }
}
