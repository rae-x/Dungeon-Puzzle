package unsw.dungeon;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class DungeonSound {

    // NOTE: change the value of this variable to disable or enable sound
    private static final boolean soundOn = false;

    // Media fields
    private static Media DOOR_OPEN;
    private static Media SWORD_ATTACK;
    private static Media ITEM_PICKUP;
    private static Media POTION_PICKUP;
    private static Media WALL_BUMP;
    private static Media BOULDER_PUSH;
    private static Media FLOORSWITCH_ACTIVE;

    // MediaPlayer fields
    private static MediaPlayer doorSFXPlayer;
    private static MediaPlayer swordSFXPlayer;
    private static MediaPlayer itemSFXPlayer;
    private static MediaPlayer potionSFXPlayer;
    private static MediaPlayer wallBumpSFXPlayer;
    private static MediaPlayer boulderPushSFXPlayer;
    private static MediaPlayer floorSwitchActivateSFXPlayer;

    // Prevent instantiation of this class
    private DungeonSound() {}

    /**
     * Initialises the Media and MediaPlayer classes, only if soundOn is true
     */
    public static void initialise() {
        if (soundOn) {
            DOOR_OPEN = new Media((new File("src/sfx/door_open.wav")).toURI().toString());
            SWORD_ATTACK = new Media((new File("src/sfx/sword_attack.wav")).toURI().toString());
            ITEM_PICKUP = new Media((new File("src/sfx/item_collect.wav")).toURI().toString());
            POTION_PICKUP = new Media((new File("src/sfx/drink_potion.wav")).toURI().toString());
            WALL_BUMP = new Media((new File("src/sfx/wall_bump.wav")).toURI().toString());
            BOULDER_PUSH = new Media((new File("src/sfx/boulder_push.wav")).toURI().toString());
            FLOORSWITCH_ACTIVE = new Media((new File("src/sfx/floor_switch_active.wav")).toURI().toString());

            doorSFXPlayer = new MediaPlayer(DOOR_OPEN);
            swordSFXPlayer = new MediaPlayer(SWORD_ATTACK);
            itemSFXPlayer = new MediaPlayer(ITEM_PICKUP);
            potionSFXPlayer = new MediaPlayer(POTION_PICKUP);
            wallBumpSFXPlayer = new MediaPlayer(WALL_BUMP);
            boulderPushSFXPlayer = new MediaPlayer(BOULDER_PUSH);
            floorSwitchActivateSFXPlayer = new MediaPlayer(FLOORSWITCH_ACTIVE);
        }
    }

    /**
     * Play the door open sound.
     */
    public static void playDoorOpen() {
        if (soundOn) {
            doorSFXPlayer.play();
            doorSFXPlayer.seek(Duration.millis(0));
        }
    }

    /**
     * Play the sword attack sound.
     */
    public static void playSwordAttack() {
        if (soundOn) {
            swordSFXPlayer.play();
            swordSFXPlayer.seek(Duration.millis(0));
        }
    }

    /**
     * Play the item pickup sound.
     */
    public static void playItemPickup() {
        if (soundOn) {
            itemSFXPlayer.play();
            itemSFXPlayer.seek(Duration.millis(0));
        }
    }

    /**
     * Play the potion pickup sound.
     */
    public static void playPotionPickup() {
        if (soundOn) {
            potionSFXPlayer.play();
            potionSFXPlayer.seek(Duration.millis(0));
        }
    }

    /**
     * Play the wall bump sound.
     */
    public static void playWallBump() {
        if (soundOn) {
            wallBumpSFXPlayer.play();
            wallBumpSFXPlayer.seek(Duration.millis(0));
        }
    }

    /**
     * Play the boulder push sound.
     */
    public static void playBoulderPush() {
        if (soundOn) {
            boulderPushSFXPlayer.play();
            boulderPushSFXPlayer.seek(Duration.millis(0));
        }
    }

    /**
     * Play the floor switch activate sound.
     */
    public static void playFloorSwitchActivate() {
        if (soundOn) {
            floorSwitchActivateSFXPlayer.play();
            floorSwitchActivateSFXPlayer.seek(Duration.millis(0));
        }
    }
}