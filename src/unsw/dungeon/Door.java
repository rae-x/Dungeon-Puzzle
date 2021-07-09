package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Door class
 * 
 * Doors can be opened by the corresponding key. Once a door is opened, 
 * the player can move through it. 
 * 
 */
public class Door extends Entity {
    
    private int id;
    private BooleanProperty opened;

    public Door(int x, int y, int id) {
        super(x, y, true, true);
        this.id = id;
        this.opened = new SimpleBooleanProperty(false);
    }

    /**
     * The door can be opened if the player has 
     * picked up a key with the same id as the door, 
     * the door will be opened.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            if (player.hasKey(this.id)) {
                DungeonSound.playDoorOpen();
                this.opened.set(true);
                this.setCollision(false);
                player.destroyKey();
            }
        }
    }

    /**
     * @return Return true for door opened, return false otherwise.
     */
    public BooleanProperty opened() {
        return this.opened;
    }
}