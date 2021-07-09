package unsw.dungeon;
/**
 * Treasure Class
 * 
 * Treasures can be collected by the player to complete the goals.
 */
public class Treasure extends Entity {
    
    private Dungeon dungeon;

    public Treasure(Dungeon dungeon, int x, int y) {
        super(x, y, true, false);
        this.dungeon = dungeon;
    }

    /**
     * The treasure can be picked up by the player. Once picked up
     * the treasure is destroyed, the player's treasure count is increased.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            DungeonSound.playItemPickup();
            Player player = (Player) entity;
            player.setTreasureCount(player.getTreasureCount() + 1);
            this.destroy();
        }
    }

    /**
     * The treasure is removed from the dungeon.
     */
    @Override
    public void destroy() {
        super.destroy();
        dungeon.removeEntity(this);
    }
}