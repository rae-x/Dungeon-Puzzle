package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A dungeon in the interactive dungeon player.
 *
 * A dungeon can contain many entities, each occupy a square. More than one
 * entity can occupy the same square.
 *
 * @author Robert Clifton-Everest
 *
 */
public class Dungeon {

    private int width, height;
    private List<Entity> entities;
    private List<Enemy> enemies;
    private Player player;
    private int numFloorSwitches;
    private int switchesActivated;
    private int numTreasure;
    private Task task;
    private BooleanProperty cleared;
    private BooleanProperty gameOver;

    public Dungeon(int width, int height, Task task) {
        this.width = width;
        this.height = height;
        this.entities = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.player = null;
        this.numFloorSwitches = 0;
        this.switchesActivated = 0;
        this.numTreasure = 0;
        this.task = task;
        this.cleared = new SimpleBooleanProperty(false);
        this.gameOver = new SimpleBooleanProperty(false);
    }

    public Task getAllTasks() {
        return this.task;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Add entity to the list of entities in the dungeon.
     * @param entity The entity added to the dungeon.
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity.getClass() == Treasure.class) {
            numTreasure++;
        }
    }

    /**
     * Add enemy to the list of enemies in the dungeon.
     * @param enemy The enemy added to the dungeon.
     */
    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    /**
     * Remove an entity from the list of entities in the dungeon.
     * @param entity The entity that will be removed from the dungeon's list of entities.
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
        if (entity.getClass() == Treasure.class) {
            numTreasure--;
            if (numTreasure == 0) {
                task.clearTask("treasure");
                if (task.isCompleted()) {
                    cleared.set(true);
                }
            }
        }
    }

    /**
     * Remove an enemy from the list of enemies in the dungeon.
     * @param enemy The enemy that will be removed from the dungeon's list of enemies.
     */
    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
        if (enemies.isEmpty()) {
            task.clearTask("enemies");
            if (task.isCompleted()) {
                cleared.set(true);
            }
        }
    }

    public int getSwitchesActivated() {
        return switchesActivated;
    }

    /**
     * Set the number of activated switches in the dungeon.
     * @param switchesActivated The number of activated switch in the dungeon.
     */
    public void setSwitchesActivated(int switchesActivated) {
        this.switchesActivated = switchesActivated;
        if (numFloorSwitches != 0 && switchesActivated == numFloorSwitches) {
            task.clearTask("boulders");
            if (task.isCompleted()) {
                cleared.set(true);
            }
        }
    }

    public int getNumFloorSwitches() {
        return numFloorSwitches;
    }

    public void setNumFloorSwitches(int numFloorSwitches) {
        this.numFloorSwitches = numFloorSwitches;
    }

    /**
     * Given the x and y coordinates, return a list of entities 
     * that surround it. 
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return Returns the list of entities that surrounds the given coordinate. 
     */
    public List<Entity> getSurroundedEntities(int x, int y) {
        List<Entity> result = new ArrayList<>();
    	for (Entity entity: this.entities){
            // if its on the left or right of the player. 
            if ((entity.getX() == x-1  && entity.getY() == y || entity.getX() == x+1 && entity.getY() == y)){
                result.add(entity);
            // if its on top or below the player.
            } else if ((entity.getX() == x && entity.getY() == y+1 || entity.getX() == x && entity.getY() == y-1)){
                result.add(entity);
            // The top left diagonal and the bottom left diagonal.
            } else if ((entity.getX() == x-1 && entity.getY() == y+1 || entity.getX() == x-1 && entity.getY()==y-1)){
                result.add(entity);
            } else if ((entity.getX() == x+1 && entity.getY() == y+1) || (entity.getX() == x+1 && entity.getY() == y-1)){
                result.add(entity);
            }
        }
        return result;
    }

    /**
     * Return the entity that is on the given x and y coordinate.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The entity that is on the x and y coordinate.
     */
    public Entity getEntity(int x, int y) {
        for (Entity entity: this.entities) {
            if (entity.getClass() != FloorSwitch.class &&
                    entity.getX() == x && entity.getY() == y) {
                return entity;
            }
        }
        return null;
    }

    public List<Entity> getAllEntities() {
        return this.entities;
    }

    /**
     * Returns the floor switch on the given x and y coordinate.
     * @param x The x coordinate.
     * @param y The y coordinate.
     * @return The floor switch that is on the given x and y coordinate.
     */
    public FloorSwitch getFloorSwitch(int x, int y) {
        for (Entity entity: this.entities) {
            if (entity.getClass() == FloorSwitch.class &&
                    entity.getX() == x && entity.getY() == y) {
                FloorSwitch floorSwitch = (FloorSwitch) entity;
                return floorSwitch;
            }
        }
        return null;
    }

    /**
     * Move all enemies in the dungeon towards the player.
     * @param player The player that the enemies are moving towards.
     */
    public void moveEnemies(Player player) {
        // Make a copy of the original list to prevent list modification during
        // the loop
        List<Enemy> enemiesCopy = new ArrayList<>(this.enemies);

        for (Enemy enemy: enemiesCopy) {
            enemy.moveTowardsPlayer(player);
        }
    }

    /**
     * Once the player reach the exit, if there's only one task left 
     * clear the exit task, if there is no task left, then the dungeon is cleared.
     */
    public void reachedExit() {
        task.clearTask("exit");

        if (task.isCompleted()) {
            cleared.set(true);
        }
    }

    public BooleanProperty cleared() {
        return cleared;
    }

    public BooleanProperty gameOver() {
        return gameOver;
    }

    public List<Enemy> getEnemies(){
        return this.enemies;
    }
}
