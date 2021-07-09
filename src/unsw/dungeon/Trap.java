package unsw.dungeon;

/**
 * Trap Class
 * 
 * If the player or enemy steps on the trap, they will be killed.
 * 
 */
public class Trap extends Entity {

    public Trap(int x, int y) {
        super(x, y, true, false);
    }

    /**
     * Enemies or player stepping on the trap will be destroyed.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            if (player.invincibleTime().get() < 1) {
                player.setHealth(player.health().get() - 1);
            }
        } else if (entity.getClass() == Enemy.class) {
            entity.destroy();
        }
    }
}