package unsw.dungeon;
/**
 * Health Potion class
 * 
 * The player can pick up the health potion by walking into the square 
 * where the health potion is located. The potion is immediately consumed once 
 * picked up, the health potion will restore the player's health to 5.
 * 
 */
public class HealthPotion extends Entity {

    private Dungeon dungeon;

    public HealthPotion(Dungeon dungeon, int x, int y) {
        super(x, y, true, false);
        this.dungeon = dungeon;
    }

    /**
     * Once the player picks up the potion, the health of the player will be set to 5. 
     * The health potion will be destroyed.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            DungeonSound.playPotionPickup();
            Player player = (Player) entity;
            player.setHealth(5);
            this.destroy();
        }
    }

    /**
     * The health potion will be removed from the dungeon's list of entities.
     */
    @Override
    public void destroy() {
        super.destroy();
        dungeon.removeEntity(this);
    }
}