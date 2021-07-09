package unsw.dungeon;

/**
 * Portal class
 * 
 * Teleport entities such as Player and Enemy 
 * to the corresponding portal in the Dungeon.
 * 
 */

public class Portal extends Entity {

    private int id;
    private Portal matchingPortal;

    public Portal(int x, int y, int id) {
        super(x, y, true, true);
        this.id = id;
        this.matchingPortal = null;
    }

    /**
     * If the player or enemy collides with the portal, they will be teleported
     * to the corresponding portal in the dungeon.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class || entity.getClass() == Enemy.class) {
            entity.x().set(matchingPortal.getX());
            entity.y().set(matchingPortal.getY());
        }
    }

    public int getID() {
        return this.id;
    }

    public void setMatchingPortal(Portal matchingPortal) {
        this.matchingPortal = matchingPortal;
    }
}