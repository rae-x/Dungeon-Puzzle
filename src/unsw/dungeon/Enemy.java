package unsw.dungeon;

/**
 * Enemy Class
 * 
 * The enemy will constantly moves toward the player, 
 * stopping if it cannot move any closer. 
 * The player dies upon collision with an enemy.
 * 
 */
public class Enemy extends Entity {
    
    private Dungeon dungeon;

    public Enemy(Dungeon dungeon, int x, int y) {
        super(x, y, true, true);
        this.dungeon = dungeon;
    }

    /**
     * The enemy will move to the given direction. 
     * @param direction The direction to move the enemy to.
     */
    public void move(String direction) {
        int resultX = 0;
        int resultY = 0;

        if (direction.equals("up")) {
            resultX = getX();
            resultY = getY() - 1;
        } else if (direction.equals("down")) {
            resultX = getX();
            resultY = getY() + 1;
        } else if (direction.equals("left")) {
            resultX = getX() - 1;
            resultY = getY();
        } else if (direction.equals("right")) {
            resultX = getX() + 1;
            resultY = getY();
        }

        if (resultX < 0 || resultX > dungeon.getWidth() - 1 ||
                resultY < 0 || resultY > dungeon.getHeight() - 1) {
            // Invalid coordinates
            return;
        }

        // Check if an entity is in the direction the player is moving towards
        Entity entity = dungeon.getEntity(resultX, resultY);
        if (entity != null) {
            onTouch(entity);
            
            if (!entity.getCollision()) {
                // Entity doesn't block the enemy from moving
                x().set(resultX);
                y().set(resultY);
            }
        } else {
            // Move enemy to coordinates
            x().set(resultX);
            y().set(resultY);
        }
    }

    /**
     * The enemy will move towards the direction of the player, if 
     * the player is invincible, the enemy will move away from the player.
     * @param player The player that the enemy is moving towards.
     */
    public void moveTowardsPlayer(Player player) {
        if (player.getX() == this.getX() && player.getY() < this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_UP);
            } else {
                this.move(Movement.MOVE_DOWN);
            }
        } else if (player.getX() > this.getX() && player.getY() < this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_RIGHT);
            } else {
                this.move(Movement.MOVE_LEFT);
            }
        } else if (player.getX() > this.getX() && player.getY() == this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_RIGHT);
            } else {
                this.move(Movement.MOVE_LEFT);
            }
        } else if (player.getX() > this.getX() && player.getY() > this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_DOWN);
            } else {
                this.move(Movement.MOVE_UP);
            }
        } else if (player.getX() == this.getX() && player.getY() > this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_DOWN);
            } else {
                this.move(Movement.MOVE_UP);
            }
        } else if (player.getX() < this.getX() && player.getY() > this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_LEFT);
            } else {
                this.move(Movement.MOVE_RIGHT);
            }
        } else if (player.getX() < this.getX() && player.getY() == this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_LEFT);
            } else {
                this.move(Movement.MOVE_RIGHT);
            }
        } else if (player.getX() < this.getX() && player.getY() < this.getY()) {
            if (player.invincibleTime().get() < 1) {
                this.move(Movement.MOVE_UP);
            } else {
                this.move(Movement.MOVE_DOWN);
            }
        }
    }

    /**
     * The enemy colliding with the player will cause the player
     * to be destroyed immediately. If the player is invincible, then 
     * collision will result in enemy's death.
     */
    @Override
    public void onTouch(Entity entity) {
        if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            if (player.invincibleTime().get() == 0) {
                player.setHealth(player.health().get() - 1);
            } else {
                this.destroy();
            }
        } else if (entity.getClass() == Enemy.class){
        } else {
            entity.onTouch(this);
        }
    }

    /**
     * Remove enemy from the dungeon.
     */
    @Override
    public void destroy() {
        super.destroy();
        dungeon.removeEntity(this);
        dungeon.removeEnemy(this);
    }
}