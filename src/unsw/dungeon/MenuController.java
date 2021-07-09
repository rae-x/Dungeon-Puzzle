package unsw.dungeon;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController {

    @FXML
    private AnchorPane titlePane;

    @FXML
    private AnchorPane optionsPane;

    @FXML
    private AnchorPane instructionsPane;

    @FXML
    private CheckBox christmasCheckBox;

    private Stage primaryStage;
    private DungeonController dungeonController;
    private Scene scene;
    private BooleanProperty christmasTheme;

    public MenuController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.dungeonController = null;
        this.scene = null;
        this.christmasTheme = new SimpleBooleanProperty(false);
    }

    public void setDungeonController(DungeonController dungeonController) {
        this.dungeonController = dungeonController;
    }

    public Scene getScene() {
        return this.scene;
    }

    public BooleanProperty getChristmasTheme(){
        return this.christmasTheme;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @FXML
    public void initialize() {
        optionsPane.setManaged(false);
        optionsPane.setVisible(false);
        instructionsPane.setManaged(false);
        instructionsPane.setVisible(false);
        christmasCheckBox.selectedProperty().bindBidirectional(this.christmasTheme);
    }

    @FXML
    public void handlePlay() throws IOException {
        primaryStage.setScene(dungeonController.getScene());
        dungeonController.setChristmasTheme(this.christmasTheme.get());
        dungeonController.handlePlayAgain();
    }

    @FXML
    public void handleOptions() {
        optionsPane.setManaged(true);
        optionsPane.setVisible(true);
        titlePane.setManaged(false);
        titlePane.setVisible(false);
    }

    @FXML
    public void handleInstructions() {
        instructionsPane.setManaged(true);
        instructionsPane.setVisible(true);
        titlePane.setManaged(false);
        titlePane.setVisible(false);
    }

    @FXML
    public void handleBack() {
        optionsPane.setManaged(false);
        optionsPane.setVisible(false);
        instructionsPane.setManaged(false);
        instructionsPane.setVisible(false);
        titlePane.setManaged(true);
        titlePane.setVisible(true);
    }
}