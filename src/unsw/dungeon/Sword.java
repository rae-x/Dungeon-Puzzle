package unsw.dungeon;
/**
 * Sword Class
 * 
 * This can be picked up the player and used to kill enemies. 
 * Only one sword can be carried at once. Each sword is only 
 * capable of 5 hits and disappears after that. One hit of the sword 
 * is sufficient to destroy any enemy.
 * 
 */
public class Sword extends Entity {
    private Dungeon dungeon;

    public Sword(Dungeon dungeon, int x, int y) {
        super(x, y, true, false);
        this.dungeon = dungeon;
    }

    /**
     * The player is able to pick up the sword if they are in the square
     * that the sword is in. The player can only pick up one sword and there
     * are 5 uses to the sword.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            if (player.swordUses().get() == 0) {
                DungeonSound.playItemPickup();
                player.setSwordUses(5);
                this.destroy();
            }
        }
    }

    /**
     * The sword is destroyed and removed from the dungeon.
     */
    @Override
    public void destroy() {
        super.destroy();
        dungeon.removeEntity(this);
    }
}