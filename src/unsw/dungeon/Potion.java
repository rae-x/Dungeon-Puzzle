package unsw.dungeon;
/**
 * Potion class
 * 
 * If the player picks up the potion, they become invincible to enemies. 
 * Colliding with an enemy will result in their immediate destruction. 
 * The effect of the potion only lasts a limited time.
 */
public class Potion extends Entity {

    private Dungeon dungeon;

    public Potion(Dungeon dungeon, int x, int y) {
        super(x, y, true, false);
        this.dungeon = dungeon;
    }

    /**
     * The potion is picked up by the player, and is destroyed immediately.
     * The player can only have one potion at a time.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            if (player.invincibleTime().get() == 0) {
                DungeonSound.playPotionPickup();
                player.setInvincible();
                this.destroy();
            }
        }
    }

    /**
     * The player uses the potion and becomes invincible for 10 moves.
     */
    @Override
    public void destroy() {
        super.destroy();
        dungeon.removeEntity(this);
    }
}