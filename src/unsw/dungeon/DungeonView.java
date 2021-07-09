package unsw.dungeon;

import java.util.List;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DungeonView {
    
    private DungeonController dungeonController;

    public DungeonView(DungeonController dungeonController) {
        this.dungeonController = dungeonController;
    }

    /**
     * Add images to the corresponding entities in the dungeon.
     * @param dungeonGrid Grids in the dungeon.
     * @param entities List of entities in the dungeon.
     * @param width The width of the dungeon.
     * @param height The height of the dungeon.
     */
    public void addDungeonViews(GridPane dungeonGrid, List<Entity> entities, int width, int height, boolean christmasTheme) {

        // Clear dungeon grid
        dungeonGrid.getChildren().clear();

        // Add the ground first so it is below all other entities
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (christmasTheme) {
                    dungeonGrid.add(new ImageView(DungeonImages.SNOW_GROUND), x, y);
                } else {
                    dungeonGrid.add(new ImageView(DungeonImages.GROUND), x, y);
                }
            }
        }

        // Add entity images
        for (Entity e: entities) {
            ImageView image = createDungeonImage(e, christmasTheme);

            if (image == null) {
                continue;
            }

            GridPane.setColumnIndex(image, e.getX());
            GridPane.setRowIndex(image, e.getY());
            dungeonController.addEntityListeners(image, e);
            dungeonGrid.getChildren().add(image);
        }
    }

    public void resetTasksList(VBox tasksVbox) {
        tasksVbox.getChildren().clear();
    }

    public void addTasks(Task task, VBox tasksVbox) {
        if (task == null) {
            return;
        }

        if (task.getTaskCondition().equals("AND")) {
            addTasks(task.getLeft(), tasksVbox);
            Text andTxt = new Text("and");
            tasksVbox.getChildren().add(andTxt);
            addTasks(task.getRight(), tasksVbox);
        } else if (task.getTaskCondition().equals("OR")) {
            addTasks(task.getLeft(), tasksVbox);
            Text orTxt = new Text("or");
            tasksVbox.getChildren().add(orTxt);
            addTasks(task.getRight(), tasksVbox);
        } else {
            Text taskTxt = new Text();
            if (task.getTaskCondition().equals("exit")) {
                taskTxt.setText("Reach the exit");
            } else if (task.getTaskCondition().equals("boulders")) {
                taskTxt.setText("Push all boulders onto the floor switches");
            } else if (task.getTaskCondition().equals("enemies")) {
                taskTxt.setText("Defeat all enemies");
            } else if (task.getTaskCondition().equals("treasure")) {
                taskTxt.setText("Collect all treasure");
            }
            tasksVbox.getChildren().add(taskTxt);
            dungeonController.addTaskListener(taskTxt, task.completed());
        }
    }

    /**
     * Set the correct images to the corresponding entity.
     * @param entity The entity that requires an image.
     * @return The image for the entity.
     */
    private ImageView createDungeonImage(Entity entity, boolean christmasTheme) {
        if (entity.getClass() == Player.class) {
            if (christmasTheme)
                return new ImageView(DungeonImages.SNOW_PLAYER);
            else
                return new ImageView(DungeonImages.PLAYER);
        } else if (entity.getClass() == Enemy.class) {
            return new ImageView(DungeonImages.ENEMY);
        } else if (entity.getClass() == Wall.class) {
            if (christmasTheme)
                return new ImageView(DungeonImages.SNOW_WALL);
            else
                return new ImageView(DungeonImages.WALL);
        } else if (entity.getClass() == Treasure.class) {
            return new ImageView(DungeonImages.TREASURE);
        } else if (entity.getClass() == Door.class) {
            if (christmasTheme)
                return new ImageView(DungeonImages.SNOW_DOOR);
            else
                return new ImageView(DungeonImages.CLOSED_DOOR);
        } else if (entity.getClass() == Key.class) {
            return new ImageView(DungeonImages.KEY);
        } else if (entity.getClass() == Boulder.class) {
            if (christmasTheme)
                return new ImageView(DungeonImages.SNOW_BOULDER);
            else
                return new ImageView(DungeonImages.BOULDER);
        } else if (entity.getClass() == FloorSwitch.class) {
            return new ImageView(DungeonImages.FLOOR_SWITCH);
        } else if (entity.getClass() == Portal.class) {
            return new ImageView(DungeonImages.PORTAL);
        } else if (entity.getClass() == Exit.class) {
            if (christmasTheme)
                return new ImageView(DungeonImages.SNOW_EXIT);
            else
                return new ImageView(DungeonImages.EXIT);
        } else if (entity.getClass() == Sword.class) {
            return new ImageView(DungeonImages.SWORD);
        } else if (entity.getClass() == Potion.class) {
            return new ImageView(DungeonImages.INVINCIBLE_POTION);
        } else if (entity.getClass() == Trap.class) {
            return new ImageView(DungeonImages.TRAP);
        } else if (entity.getClass() == HealthPotion.class) {
            return new ImageView(DungeonImages.HEALTH_POTION);
        }

        // Unknown Entity class passed in
        return null;
    }

    /**
     * Show the number of sword uses on the screen.
     * @param swordVbox The view box for sword.
     * @param swordUsesTxt The text for sword uses.
     * @param uses The number of uses left for sword.
     */
    public void showSwordUses(HBox swordVbox, Text swordUsesTxt, int uses) {
        swordUsesTxt.setText(uses + " uses left");
        swordVbox.setVisible(true);
        swordVbox.setManaged(true);
    }

    /**
     * Hide the number of sword uses on the screen.
     * @param swordVbox The viewbox for sword.
     */
    public void hideSwordUses(HBox swordVbox) {
        swordVbox.setVisible(false);
        swordVbox.setManaged(false);
    }

    /**
     * Show the duration of potion on the screen.
     * @param potionVbox The view box for potion.
     * @param potionTxt The text for potion.
     * @param uses The number of moves left for potion.
     */
    public void showPotionDuration(HBox potionVbox, Text potionTxt, int uses) {
        potionTxt.setText(uses + " moves left");
        potionVbox.setVisible(true);
        potionVbox.setManaged(true);
    }

    /**
     * Hide the duration of potion on the screen.
     * @param potionVbox The view box for potion.
     */
    public void hidePotionDuration(HBox potionVbox) {
        potionVbox.setVisible(false);
        potionVbox.setManaged(false);
    }

    /**
     * Show the dungeon clear screen once a dungeon is cleared.
     * @param dungeonVbox
     * @param clearedVbox
     */
    public void showDungeonClearScreen(VBox dungeonVbox, VBox clearedVbox) {
        dungeonVbox.setVisible(false);
        dungeonVbox.setManaged(false);
        clearedVbox.setVisible(true);
        clearedVbox.setManaged(true);
    }

    /**
     * Show the game cleared screen once all dungeons are cleared.
     * @param dungeonVbox
     * @param gameClearedVbox
     * @param clearedGameTxt
     */
    public void showGameClearedScreen(VBox dungeonVbox, VBox gameClearedVbox, Text clearedGameTxt) {
        clearedGameTxt.setText("Congrats! You've completed all dungeons.");
        dungeonVbox.setVisible(false);
        dungeonVbox.setManaged(false);
        gameClearedVbox.setVisible(true);
        gameClearedVbox.setManaged(true);
    }

    /**
     * Show the game over screen.
     * @param dungeonVbox
     * @param gameClearedVbox
     * @param clearedGameTxt
     */
    public void showGameOverScreen(VBox dungeonVbox, VBox gameClearedVbox, Text clearedGameTxt) {
        clearedGameTxt.setText("Game Over!");
        dungeonVbox.setVisible(false);
        dungeonVbox.setManaged(false);
        gameClearedVbox.setVisible(true);
        gameClearedVbox.setManaged(true);
    }

    /**
     * Show the current stage of the dungeon and set all other view boxes to be not visible.
     * @param stageTxt
     * @param dungeonVbox
     * @param clearedVbox
     * @param gameClearedVbox
     * @param stageNum
     */
    public void showDungeonScreen(Text stageTxt, VBox dungeonVbox, VBox clearedVbox, VBox gameClearedVbox, int stageNum) {
        stageTxt.setText("Stage " + stageNum);
        dungeonVbox.setVisible(true);
        dungeonVbox.setManaged(true);
        clearedVbox.setVisible(false);
        clearedVbox.setManaged(false);
        gameClearedVbox.setVisible(false);
        gameClearedVbox.setManaged(false);
    }

    /**
     * Show the help screen for the user.
     * @param helpVbox The view box for the help screen.
     * @param dungeonVbox The view box for the dungeon.
     * @param clearedVbox The view box for level cleared.
     * @param gameClearedVbox The view box for game cleared.
     */
    public void showHelpScreen(VBox helpVbox, VBox dungeonVbox, VBox clearedVbox, VBox gameClearedVbox) {
        dungeonVbox.setVisible(false);
        dungeonVbox.setManaged(false);
        clearedVbox.setVisible(false);
        clearedVbox.setManaged(false);
        gameClearedVbox.setVisible(false);
        gameClearedVbox.setManaged(false);
        helpVbox.setVisible(true);
        helpVbox.setManaged(true);
    }

    /**
     * Hide the help screen from the user.
     * @param helpVbox The view box for the help screen.
     */
    public void hideHelpScreen(VBox helpVbox) {
        helpVbox.setVisible(false);
        helpVbox.setManaged(false);
    }

    /**
     * Reset the text for health.
     * @param healthTxt The text for the player's health.
     * @param health Health of the player.
     */
    public void resetHealthText(Text healthTxt, int health) {
        healthTxt.setText("Health: " + health);
    }
}