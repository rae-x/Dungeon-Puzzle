package unsw.dungeon;

public class Key extends Entity {
    
    private int id;
    private Dungeon dungeon;

    public Key(Dungeon dungeon, int x, int y, int id) {
        super(x, y, true, false);
        this.id = id;
        this.dungeon = dungeon;
    }

    public int getKeyID() {
        return this.id;
    }

    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            if (player.getKey() == null) {
                DungeonSound.playItemPickup();
                player.addKey(this);
                this.destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        dungeon.removeEntity(this);
    }
}