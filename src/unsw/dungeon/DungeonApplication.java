package unsw.dungeon;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DungeonApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dungeon");
        DungeonSound.initialise();

        DungeonModel dungeonModel = new DungeonModel();
        DungeonController dungeonController = new DungeonController(primaryStage, dungeonModel);

        FXMLLoader dungeonFXMLLoader = new FXMLLoader(getClass().getResource("DungeonView.fxml"));
        dungeonFXMLLoader.setController(dungeonController);
        Parent dungeonRoot = dungeonFXMLLoader.load();
        Scene dungeonScene = new Scene(dungeonRoot);
        dungeonController.setScene(dungeonScene);

        MenuController menuController = new MenuController(primaryStage);
        menuController.setDungeonController(dungeonController);
        dungeonController.setMenuController(menuController);

        FXMLLoader mainMenuFXMLLoader = new FXMLLoader(getClass().getResource("DungeonMainMenu.fxml"));
        mainMenuFXMLLoader.setController(menuController);
        Parent mainMenuRoot = mainMenuFXMLLoader.load();
        Scene mainMenuScene = new Scene(mainMenuRoot);
        menuController.setScene(mainMenuScene);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}