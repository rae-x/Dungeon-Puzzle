package unsw.dungeon;
/**
 * Exit class
 * 
 * If the player goes through the exit, the dungeon is completed.
 */
public class Exit extends Entity {
    
    private Dungeon dungeon;

    public Exit(Dungeon dungeon, int x, int y) {
        super(x, y, true, false);
        this.dungeon = dungeon;
    }

    /**
     * Once the player arrive at the exit, the dungeon will be cleared if 
     * there are no tasks left.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            dungeon.reachedExit();
        }
    }

    /**
     * The dungeon is cleared if the exit is reached.
     * @param dungeon The dungeon that will be cleared.
     */
    public void clearDungeon(Dungeon dungeon) {
        dungeon.reachedExit();
    }
}