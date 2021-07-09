package unsw.dungeon;

import java.io.File;

import javafx.scene.image.Image;

// Class that stores all Image data of the Dungeon

public final class DungeonImages {
    
    // Prevent instantiation of this object
    private DungeonImages() {}

    public static final Image PLAYER = new Image((new File("src/images/human_new.png")).toURI().toString());
    public static final Image GROUND = new Image((new File("src/images/dirt_0_new.png")).toURI().toString());
    public static final Image WALL = new Image((new File("src/images/brick_brown_0.png")).toURI().toString());
    public static final Image TREASURE = new Image((new File("src/images/gold_pile.png")).toURI().toString());
    public static final Image CLOSED_DOOR = new Image((new File("src/images/closed_door.png")).toURI().toString());
    public static final Image OPEN_DOOR = new Image((new File("src/images/open_door.png")).toURI().toString());
    public static final Image KEY = new Image((new File("src/images/key.png")).toURI().toString());
    public static final Image BOULDER = new Image((new File("src/images/boulder.png")).toURI().toString());
    public static final Image FLOOR_SWITCH = new Image((new File("src/images/pressure_plate.png")).toURI().toString());
    public static final Image PORTAL = new Image((new File("src/images/portal.png")).toURI().toString());
    public static final Image EXIT = new Image((new File("src/images/exit.png")).toURI().toString());
    public static final Image SWORD = new Image((new File("src/images/greatsword_1_new.png")).toURI().toString());
    public static final Image ENEMY = new Image((new File("src/images/gnome.png")).toURI().toString());
    public static final Image INVINCIBLE_POTION = new Image((new File("src/images/brilliant_blue_new.png")).toURI().toString());
    public static final Image TRAP = new Image((new File("src/images/trap.png")).toURI().toString());
    public static final Image BOMB = new Image((new File("src/images/bomb.png")).toURI().toString());
    public static final Image PLAYERSWORD = new Image((new File("src/images/human_withsword.png")).toURI().toString());
    public static final Image HEALTH_POTION = new Image((new File("src/images/bubbly.png")).toURI().toString());

    // Christmas Images
    public static final Image SNOW_GROUND = new Image((new File("src/images/snow_dirt.png")).toURI().toString());
    public static final Image SNOW_WALL = new Image((new File("src/images/snow_wall3.png")).toURI().toString());
    public static final Image SNOW_PLAYER = new Image((new File("src/images/human_snow.png")).toURI().toString());
    public static final Image SNOW_PLAYERSWORD = new Image((new File("src/images/human_snow_withsword.png")).toURI().toString());
    public static final Image SNOW_BOULDER = new Image((new File("src/images/snow_boulder.png")).toURI().toString());
    public static final Image SNOW_EXIT = new Image((new File("src/images/snow_exit.png")).toURI().toString());
    public static final Image SNOW_DOOR = new Image((new File("src/images/snow_door.png")).toURI().toString());
    public static final Image SNOW_OPEN_DOOR = new Image((new File("src/images/snow_open_door.png")).toURI().toString());
}