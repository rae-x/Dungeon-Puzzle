package unsw.dungeon;

public final class GameState {
    // Prevent instantiation of this object
    private GameState() {}

    public static final int IN_PROGRESS = 0;
    public static final int DUNGEON_CLEAR = 1;
    public static final int GAME_CLEAR = 2;
    public static final int GAME_OVER = 3;   
}