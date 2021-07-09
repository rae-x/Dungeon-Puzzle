package unsw.dungeon;

import java.io.IOException;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DungeonModel {
    
    private IntegerProperty dungeonsCleared;
    private String[] dungeonFiles;
    private int numDungeons;
    private Player player;
    private Dungeon currentDungeon;
    private BooleanProperty gameOver;

    public DungeonModel() {
        this.dungeonsCleared = new SimpleIntegerProperty(0);
        this.dungeonFiles = new String[]{"Level1.json", "Level2.json", "Level3.json", "Level4.json",
                                         "Level5.json", "Level6.json", "Level7.json", "Level8.json",
                                         "Level10.json"};
        this.numDungeons = dungeonFiles.length;
        this.gameOver = new SimpleBooleanProperty(false);
        this.player = null;
        this.currentDungeon = null;
    }

    /**
     * Load the dungeon.
     * @throws IOException Throws an exception if loadDungeon fails.
     */
    public void loadDungeon() throws IOException {
        String dungeonFile = dungeonFiles[dungeonsCleared.get()];

        DungeonLoader dungeonLoader = new DungeonLoader(dungeonFile);
        this.currentDungeon = dungeonLoader.load();
        this.player = this.currentDungeon.getPlayer();

        addDungeonClearListener();
        addPlayerStatusListener();
    }

    public IntegerProperty dungeonsCleared() {
        return this.dungeonsCleared;
    }

    public BooleanProperty gameOver() {
        return this.gameOver;
    }

    public int getNumDungeons() {
        return this.numDungeons;
    }

    public Player getPlayer() {
        return this.player;
    }

    public IntegerProperty getPlayerHealth() {
        return this.player.health();
    }

    public IntegerProperty getPlayerSwordUses() {
        return this.player.swordUses();
    }

    public IntegerProperty getPlayerInvincibility() {
        return this.player.invincibleTime();
    }

    public Dungeon getDungeon() {
        return this.currentDungeon;
    }

    public List<Entity> getDungeonEntities() {
        return this.currentDungeon.getAllEntities();
    }

    public int getDungeonHeight() {
        return this.currentDungeon.getHeight();
    }

    public int getDungeonWidth() {
        return this.currentDungeon.getWidth();
    }

    public Task getTasks() {
        return this.currentDungeon.getAllTasks();
    }

    public void movePlayer(String direction) {
        this.player.move(direction);
    }

    public void playerAttack() {
        this.player.swingSword();
    }

    /**
     * Add a listener to set the number of dungeons the player has completed.
     */
    private void addDungeonClearListener() {
        this.currentDungeon.cleared().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                dungeonsCleared.set(dungeonsCleared.get() + 1);
            }
        });
    }

    /**
     * Add a listener to set Game over if the player dies.
     */
    private void addPlayerStatusListener() {
        this.player.active().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                this.gameOver.set(true);
            }
        });
    }

    /**
     * Reset the game to level 1.
     */
    public void resetGame() {
        this.dungeonsCleared.set(0);
        this.gameOver.set(false);
    }
}