package unsw.dungeon;

/**
 * Boulder class
 * 
 * Acts like a wall in most cases. The only difference 
 * being that it can be pushed by the player into adjacent squares. 
 * The player is only strong enough to push one boulder at a time.
 * 
 */
public class Boulder extends Entity {
    private Dungeon dungeon;

    public Boulder(Dungeon dungeon, int x, int y) {
        super(x, y, true, true);
        this.dungeon = dungeon;
    }

    /**
     * The boulder can only be moved by the player. 
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            pushBoulder(player);
        }
    }

    /**
     * The boulder is able to be pushed in the direction that the player is moving
     * given that there is nothing to block the boulder.
     * 
     * @param p The player that is pushing the boulder.
     */
    public void pushBoulder(Player p) {
        boolean pushed = false;
        int oldX = getX();
        int oldY = getY();

        if (this.getY() - 1 == p.getY() && (!blocked(getX(), getY() + 1))) {
            // Push boulder down
            y().set(getY() + 1);
            pushed = true;
        } else if (this.getY() + 1 == p.getY() && (!blocked(getX(), getY() - 1))) {
            // Push boulder up
            y().set(getY() - 1);
            pushed = true;
        } else if (this.getX() - 1 == p.getX() && (!blocked(getX() + 1, getY()))) {
            // Push boulder right
            x().set(getX() + 1);
            pushed = true;
        } else if (this.getX() + 1 == p.getX() && (!blocked(getX() - 1, getY()))) {
            // Push boulder left
            x().set(getX() - 1);
            pushed = true;
        }

        if (pushed) {
            DungeonSound.playBoulderPush();
            updateFloorSwitch(oldX, oldY);
        }
    }

    /**
     * If a boulder is pushed off a floor switch, then the floor switch will be 
     * deactivated, if a boulder is pushed onto a floor switch, then it will be 
     * activated.
     * 
     * @param oldX The previous x coordinate of the boulder.
     * @param oldY The previous y coordinate of the boulder.
     */
    private void updateFloorSwitch(int oldX, int oldY) {
        FloorSwitch oldSwitch = dungeon.getFloorSwitch(oldX, oldY);
        FloorSwitch newSwitch = dungeon.getFloorSwitch(getX(), getY());

        if (oldSwitch != null) {
            oldSwitch.deactivate();
        }

        if (newSwitch != null) {
            newSwitch.onTouch(this);
        }
    }

    /**
     * Checks if the boulder will be blocked in the direction its pushed to.
     * 
     * @param x The x coordinate of the desired position of the boulder.
     * @param y The y coordinate of the desired position of the boulder.
     * @return Return true if the boulder will be blocked, return false if not.
     */
    public boolean blocked(int x, int y){
        Entity entity = dungeon.getEntity(x, y);
        if (entity == null) {
            return false;
        } else if (entity.getCollision() == true) {
            return true;
        }
        return false;
    }
}