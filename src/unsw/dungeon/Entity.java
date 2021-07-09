package unsw.dungeon;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

// (Template Pattern)
// This is the template class for entities in the dungeon

/**
 * An entity in the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public abstract class Entity {

    // IntegerProperty is used so that changes to the entities position can be
    // externally observed.
    private IntegerProperty x, y;
    private BooleanProperty active;
    private boolean collision;

    /**
     * Create an entity positioned in square (x,y)
     * @param x
     * @param y
     */
    public Entity(int x, int y, boolean active, boolean collision) {
        this.x = new SimpleIntegerProperty(x);
        this.y = new SimpleIntegerProperty(y);
        this.active = new SimpleBooleanProperty(active);
        this.collision = collision;
    }

    public IntegerProperty x() {
        return x;
    }

    public IntegerProperty y() {
        return y;
    }

    public BooleanProperty active() {
        return active;
    }

    public int getX() {
        return x().get();
    }

    public int getY() {
        return y().get();
    }

    public boolean getActive() {
        return active().get();
    }

    public boolean getCollision() {
        return collision;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }

    public void destroy() {
        this.active.set(false);
    }

    public abstract void onTouch(Entity entity);
}
