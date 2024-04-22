
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application {


	HashMap<String, Scene> sceneMap;
	Client clientConnection;
	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		sceneMap = new HashMap<String, Scene>(); // All the scenes

			// Scene Establishing
			sceneMap = new HashMap<String, Scene>(); // All the scenes

			loadScene("start", "/FXML/startScreen.fxml");
			loadScene("gameplay", "/FXML/startScreen.fxml");
			setScene("start");
			primaryStage.setTitle("BattleShip");
			primaryStage.show();
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});

//			clientConnection.start();
	}

	private void loadScene(String name, String fxmlPath) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
		Scene scene = new Scene(root, 500, 500);
		sceneMap.put(name, scene);
		sceneMap.get(name).setRoot(root);
	}

	private void setScene(String name) {
		primaryStage.setScene(sceneMap.get(name));
		primaryStage.setTitle("Battleship");
	}



}

