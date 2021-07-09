package unsw.dungeon;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.stage.Stage;

import java.io.IOException;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private VBox dungeonVbox;

    @FXML
    private GridPane dungeonGrid;

    @FXML
    private VBox statusVbox;

    @FXML
    private Text healthTxt;

    @FXML
    private HBox swordVbox;

    @FXML
    private Text swordUsesTxt;

    @FXML
    private HBox potionVbox;

    @FXML
    private Text potionTxt;

    @FXML
    private Text stageTxt;

    @FXML
    private VBox tasksVbox;

    @FXML
    private VBox clearedVbox;

    @FXML
    private Text clearedGameTxt;

    @FXML
    private VBox gameClearedVbox;

    @FXML
    private VBox helpVbox;

    private Stage primaryStage;
    private DungeonModel dungeonModel;
    private DungeonView dungeonView;
    private Scene scene;
    private int gameState;
    private boolean christmasTheme;
    private MenuController menuController;

    public DungeonController(Stage primaryStage, DungeonModel dungeonModel) {
        this.primaryStage = primaryStage;
        this.dungeonModel = dungeonModel;
        this.dungeonView = new DungeonView(this);
        this.scene = null;
        this.gameState = GameState.IN_PROGRESS;
        this.christmasTheme = false;
        this.menuController = null;
    }

    public void setChristmasTheme(boolean christmasTheme) {
        this.christmasTheme = christmasTheme;
    }

    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Initialise the dungeon by adding entities, setting up the size 
     * of the dungeon and goals. 
     * 
     * @throws IOException Throws exception if setup fails.
     */
    private void setupDungeonLevel() throws IOException {

        // Load the dungeon in the backend
        this.dungeonModel.loadDungeon();

        // Add and setup views
        dungeonView.addDungeonViews(dungeonGrid, dungeonModel.getDungeonEntities(),
            dungeonModel.getDungeonWidth(), dungeonModel.getDungeonHeight(), christmasTheme);
        //dungeonView.addTasksToView(dungeonModel.getTasksList(), tasksVbox);
        dungeonView.resetTasksList(tasksVbox);
        dungeonView.addTasks(dungeonModel.getTasks(), tasksVbox);
        dungeonView.hideSwordUses(swordVbox);
        dungeonView.hidePotionDuration(potionVbox);
        dungeonView.resetHealthText(healthTxt, dungeonModel.getPlayerHealth().get());

        // Add listeners for the player
        addHealthListener();
        addSwordUsesListener();
        addInvincibilityListener();
    }

    /**
     * Restart the dungeon to its initial state.
     * @throws IOException Throws an exception if restarting fails.
     */
    private void restartDungeon() throws IOException {
        setupDungeonLevel();
        dungeonView.showDungeonScreen(stageTxt, dungeonVbox, clearedVbox,
            gameClearedVbox, dungeonModel.dungeonsCleared().get() + 1);
        dungeonGrid.requestFocus();
    }

    /**
     * Add listener to show Dungeon cleared screen and game cleared screen.
     */
    private void addDungeonClearListener() {
        dungeonModel.dungeonsCleared().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                if (newValue.intValue() == dungeonModel.getNumDungeons()) {
                    gameState = GameState.GAME_CLEAR;
                    dungeonView.showGameClearedScreen(dungeonVbox, gameClearedVbox, clearedGameTxt);
                } else {
                    gameState = GameState.DUNGEON_CLEAR;
                    dungeonView.showDungeonClearScreen(dungeonVbox, clearedVbox);
                }
            }
        });
    }

    /**
     * Add listener to show the game over screen when the game is over.
     */
    private void addGameoverListener() {
        dungeonModel.gameOver().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                gameState = GameState.GAME_OVER;
                dungeonView.showGameOverScreen(dungeonVbox, gameClearedVbox, clearedGameTxt);
            }
        });
    }

    /**
     * Add entity listeners to set grid pane and set image for entities.
     * @param node
     * @param entity
     */
    public void addEntityListeners(Node node, Entity entity) {

        // Move the dungeon sprite when it's coordinates change
        entity.x().addListener((observable, oldValue, newValue) -> {
           animateEntityMovement(node, oldValue.intValue(), newValue.intValue(), true);
        });
        entity.y().addListener((observable, oldValue, newValue) -> {
            animateEntityMovement(node, oldValue.intValue(), newValue.intValue(), false);
        });

        // Remove image if Entity is declared inactive
        entity.active().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                ImageView image = (ImageView) node;

                // fade transition
                FadeTransition fade = new FadeTransition();
                if (entity.getClass() == Enemy.class) {
                    fade.setDuration(Duration.seconds(0.25));
                } else {
                    fade.setDuration(Duration.seconds(0.1));
                }
                fade.setNode(node);
                fade.setFromValue(1.0);
                fade.setToValue(0.0);
                fade.play();
                fade.setOnFinished(e->{
                    image.setImage(null);
                    dungeonGrid.getChildren().remove(image);

                });



            }
        });

        // Change the Door sprite when opened
        if (entity.getClass() == Door.class) {
            Door door = (Door) entity;
            door.opened().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    if (menuController.getChristmasTheme().get()){
                        ImageView image = (ImageView) node;
                        image.setImage(DungeonImages.SNOW_OPEN_DOOR);
                    } else {
                        ImageView image = (ImageView) node;
                        image.setImage(DungeonImages.OPEN_DOOR);
                    }
                }
            });
        } else if (entity.getClass() == Player.class) {
            Player player = (Player) entity;
            player.swordUses().addListener((observerable, oldValue, newValue) -> {
                if (newValue.intValue() > 0) {
                    if (menuController.getChristmasTheme().get()){
                        ImageView image = (ImageView) node;
                        image.setImage(DungeonImages.SNOW_PLAYERSWORD); 
                    } else {
                        ImageView image = (ImageView) node;
                        image.setImage(DungeonImages.PLAYERSWORD);
                    }
                } else {
                    if (menuController.getChristmasTheme().get()){
                        ImageView image = (ImageView) node;
                        image.setImage(DungeonImages.SNOW_PLAYER);
                    } else {
                        ImageView image = (ImageView) node;
                        image.setImage(DungeonImages.PLAYER);
                    }
                }
            });
        }
    }


    /**
     * Add translate transition to animate entity movement
     * @param node entity to be animated
     * @param oldValue old position
     * @param newValue new position
     * @param xMovement true if entity is moving along the "x-axis"
     */
    private void animateEntityMovement(Node node, int oldValue, int newValue, boolean xMovement) {
        double dist = 32.0;
        
        // Animate entity in opposite direction if movement is left or up
        if (newValue < oldValue) {
            dist *= -1.0;
        }

        TranslateTransition translation = new TranslateTransition();
        translation.setDuration(Duration.seconds(0.1));
        if (xMovement) {
            translation.setToX(dist - node.getTranslateX());
        } else {
            translation.setToY(dist - node.getTranslateY());
        }
        translation.setNode(node);
        translation.play();
        translation.setOnFinished(e->{
            if (xMovement) {
                GridPane.setColumnIndex(node, newValue);
                node.setTranslateX(0);
            } else {
                GridPane.setRowIndex(node, newValue);
                node.setTranslateY(0);
            }
        });
    }

    /**
     * Add a health listener to show the player's health
     */
    private void addHealthListener() {
        dungeonModel.getPlayerHealth().addListener((observable, oldValue, newValue) -> {
            healthTxt.setText("Health: " + newValue);
        });
    }

    /**
     * Add a listener for tasks the player has to complete. 
     * @param taskText
     * @param task
     */
    public void addTaskListener(Text taskText, BooleanProperty taskCompleted) {
        taskCompleted.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                taskText.setStrikethrough(true);
            }
        });
    }

    /**
     * Add a listener for sword uses to show the number of uses.
     */
    public void addSwordUsesListener() {
        dungeonModel.getPlayerSwordUses().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > 0) {
                dungeonView.showSwordUses(swordVbox, swordUsesTxt, newValue.intValue());
            } else {
                dungeonView.hideSwordUses(swordVbox);
            }
        });
    }

    /**
     * Add a listener to display potion duration on the screen.
     */
    public void addInvincibilityListener() {
        dungeonModel.getPlayerInvincibility().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() > 0) {
                dungeonView.showPotionDuration(potionVbox, potionTxt, newValue.intValue());
            } else {
                dungeonView.hidePotionDuration(potionVbox);
            }
        });
    }

    @FXML
    public void initialize() throws IOException {

        // Hide hidden screen space
        clearedVbox.setManaged(false);
        gameClearedVbox.setManaged(false);
        helpVbox.setManaged(false);

        // Add listeners
        addGameoverListener();
        addDungeonClearListener();
    }

    @FXML
    public void handleNextStage() {

        try {
            setupDungeonLevel();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gameState = GameState.IN_PROGRESS;
        dungeonView.showDungeonScreen(stageTxt, dungeonVbox, clearedVbox,
            gameClearedVbox, dungeonModel.dungeonsCleared().get() + 1);
        dungeonGrid.requestFocus();
    }

    @FXML
    public void handlePlayAgain() throws IOException {

        dungeonModel.resetGame();
        setupDungeonLevel();
        dungeonView.showDungeonScreen(stageTxt, dungeonVbox, clearedVbox,
            gameClearedVbox, dungeonModel.dungeonsCleared().get() + 1);
        gameState = GameState.IN_PROGRESS;
        dungeonGrid.requestFocus();
    }

    @FXML
    public void handleHelp() {
        dungeonView.showHelpScreen(helpVbox, dungeonVbox, clearedVbox, gameClearedVbox);
    }

    @FXML
    public void handleReturnToGame() {
        dungeonView.hideHelpScreen(helpVbox);
        if (gameState == GameState.IN_PROGRESS) {
            dungeonView.showDungeonScreen(stageTxt, dungeonVbox, clearedVbox,
                gameClearedVbox, dungeonModel.dungeonsCleared().get() + 1);
            dungeonGrid.requestFocus();
        } else if (gameState == GameState.DUNGEON_CLEAR) {
            dungeonView.showDungeonClearScreen(dungeonVbox, clearedVbox);
        } else if (gameState == GameState.GAME_CLEAR) {
            dungeonView.showGameClearedScreen(dungeonVbox, gameClearedVbox, clearedGameTxt);
        } else {
            dungeonView.showGameOverScreen(dungeonVbox, gameClearedVbox, clearedGameTxt);
        }
    }

    @FXML
    public void handleMainMenu() {
        primaryStage.setScene(menuController.getScene());
    }

    @FXML
    public void handleKeyPress(KeyEvent event) throws IOException {

        if (gameState == GameState.IN_PROGRESS) {
            switch (event.getCode()) {
            case UP:
                this.dungeonModel.movePlayer(Movement.MOVE_UP);
                break;
            case DOWN:
                this.dungeonModel.movePlayer(Movement.MOVE_DOWN);
                break;
            case LEFT:
                this.dungeonModel.movePlayer(Movement.MOVE_LEFT);
                break;
            case RIGHT:
                this.dungeonModel.movePlayer(Movement.MOVE_RIGHT);
                break;
            case A:
                this.dungeonModel.playerAttack();
                break;
            case R:
                restartDungeon();
                break;
            default:
                break;
            }
        }
        event.consume();
    }
}