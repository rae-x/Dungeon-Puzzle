package unsw.dungeon;
/**
 * Floor Switch class 
 * 
 * Switches behave like empty squares, so entities can be 
 * on top of it. When a boulder is pushed on top of the switch, 
 * it will be activated, once a boulder is pushed off, the switch 
 * will be deactivated.
 * 
 */
public class FloorSwitch extends Entity {

    private Dungeon dungeon;

    public FloorSwitch(Dungeon dungeon, int x, int y) {
        super(x, y, true, false);
        this.dungeon = dungeon;
    }

    /**
     * If a boulder is on top of a switch, the switch will be activated.
     * The number of switches activated in the dungeon will be updated.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Boulder.class) {
            dungeon.setSwitchesActivated(dungeon.getSwitchesActivated() + 1);
            DungeonSound.playFloorSwitchActivate();
        }
    }

    /**
     * If a boulder is pushed off a switch, the switch will be deactivated. 
     * The number of switch activated in the dungeon will be updated.
     */
    public void deactivate() {
        dungeon.setSwitchesActivated(dungeon.getSwitchesActivated() - 1);
    }
}