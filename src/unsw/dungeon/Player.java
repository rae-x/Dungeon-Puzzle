package unsw.dungeon;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The player entity
 * 
 * @author Robert Clifton-Everest
 *
 */
public class Player extends Entity {

    private Dungeon dungeon;
    private int treasure;
    private Key key;
    private IntegerProperty health;
    private IntegerProperty swordUses;
    private IntegerProperty invincibleTime;

    /**
     * Create a player positioned in square (x,y)
     * @param x
     * @param y
     */
    public Player(Dungeon dungeon, int x, int y) {
        super(x, y, true, true);
        this.dungeon = dungeon;
        this.treasure = 0;
        this.key = null;
        this.health = new SimpleIntegerProperty(5);
        this.swordUses = new SimpleIntegerProperty(0);
        this.invincibleTime = new SimpleIntegerProperty(0);
    }

    public Key getKey(){
        return this.key;
    }

    /**
     * Checks if the player has the key that has the same id as the door.
     * @param id The id of the door.
     * @return Return true if the key matches the id of the door, return false otherwise.
     */
    public boolean hasKey(int id){
        if(this.key == null){
            return false;
        }else if (this.key.getKeyID() == id){
            return true;
        }
        return false;
    }

    public void destroyKey(){
        this.key = null;
    }

    public IntegerProperty health() {
        return this.health;
    }

    public void setHealth(int health) {
        this.health.set(health);
        if (health == 0) {
            this.destroy();
        }
    }

    public int getTreasureCount() {
        return treasure;
    }

    public void setTreasureCount(int num) {
        this.treasure = num;
    }

    public IntegerProperty swordUses() {
        return this.swordUses;
    }

    public void setSwordUses(int numUses) {
        this.swordUses.set(numUses);
    }

    public IntegerProperty invincibleTime() {
        return this.invincibleTime;
    }

    public void setInvincible(){
        this.invincibleTime.set(10);
    }

    /**
     * Decrease the invinciblity duration of the player.
     */
    public void decreaseInvincibleTime() {
        if (this.invincibleTime.get() > 0) {
            this.invincibleTime.set(this.invincibleTime.get() - 1);
        }
    }

    public void addKey(Key key) {
        this.key = key;
    }

    /**
     * Move the player to a given direction, if the direction is not blocked.
     * Enemies will move with the player, and invincible time 
     * will also decrease as the player moves.
     * @param direction The direction for the player to move to.
     */
    public void move(String direction) {
        int resultX = 0;
        int resultY = 0;

        // Move enemies before moving the player
        dungeon.moveEnemies(this);
        this.decreaseInvincibleTime();
        if (direction.equals("up")) {
            resultX = getX();
            resultY = getY() - 1;
        } else if (direction.equals("down")) {
            resultX = getX();
            resultY = getY() + 1;
        } else if (direction.equals("left")) {
            resultX = getX() - 1;
            resultY = getY();
        } else if (direction.equals("right")) {
            resultX = getX() + 1;
            resultY = getY();
        }

        if (resultX < 0 || resultX > dungeon.getWidth() - 1 ||
                resultY < 0 || resultY > dungeon.getHeight() - 1) {
            // Invalid coordinates
            return;
        }

        // Check if an entity is in the direction the player is moving towards
        Entity entity = dungeon.getEntity(resultX, resultY);
        if (entity != null) {
            onTouch(entity);

            if (!entity.getCollision()) {
                // Entity doesn't block the player from moving
                x().set(resultX);
                y().set(resultY);
            }
        } else {
            // Move player to coordinates
            x().set(resultX);
            y().set(resultY);
        }
    }

    /**
     * The player swings the sword, which decrease sword uses, 
     * and enemies will be destroyed if they are adjacent to the player.
     */
    public void swingSword() {
        if (this.swordUses.get() == 0) {
            return;
        }

        //if enemy is around player, it dies...
        //get the entities around the player, if the entity is enemy, it dies.
        DungeonSound.playSwordAttack();
        this.swordUses.set(this.swordUses.get() - 1);
        for (Entity entity: dungeon.getSurroundedEntities(getX(), getY())){
            if (entity.getClass() == Enemy.class){
                Enemy enemy = (Enemy) entity;
                enemy.destroy();
                dungeon.removeEnemy(enemy);
            }
        }
    }

    @Override
    public void onTouch(Entity entity) {
        entity.onTouch(this);
    }

    /**
     * Destroy player from the dungeon, the game will be over.
     */
    @Override
    public void destroy() {
        super.destroy();
        dungeon.gameOver().set(true);
        dungeon.removeEntity(this);
    }
}
