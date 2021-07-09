package unsw.dungeon;
/**
 * Wall class
 * 
 * Blocks the movement of the player, enemies and boulders
 */
public class Wall extends Entity {

    public Wall(int x, int y) {
        super(x, y, true, true);
    }

    @Override
    public void onTouch(Entity entity) {
        DungeonSound.playWallBump();
    }
}
