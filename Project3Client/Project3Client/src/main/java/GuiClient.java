
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

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/startScreen.fxml"));
			Parent root = loader.load();

			Scene scene = new Scene(root, 800, 500);

			scene.getStylesheets().add(getClass().getResource("/styles/styles1.css").toExternalForm());

			sceneMap.put("start", scene);
//			sceneMap.get("start").setRoot(root);

			primaryStage.setScene(sceneMap.get("start"));
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


}

