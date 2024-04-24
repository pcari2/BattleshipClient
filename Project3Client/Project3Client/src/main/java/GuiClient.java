
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.Rectangle;

public class GuiClient extends Application {


	private HashMap<String, Scene> sceneMap;
	private Stage primaryStage;
	private Board enemyBoard, playerBoard;
	private Scene previousScene;

	Client clientConnection;
	ListView<String> chatListView;

//-----------------------------------------------------------
	// Unused
	private boolean running = false;
	private int shipsToPlace = 5;
	private boolean enemyTurn = false;
	private Random random = new Random();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		sceneMap = new HashMap<String, Scene>(); // All the scenes

		Scene startScene = createStartScene();
		Scene rulesScene = createRulesScene();
		Scene playerScene = createPlayerScene();

		sceneMap.put("startScreen", startScene);
		sceneMap.put("rulesScreen", rulesScene);
		sceneMap.put("player", playerScene);

		primaryStage.setScene(startScene);
		primaryStage.setMaximized(true);
		primaryStage.setTitle("Battleship");
		primaryStage.show();

		clientConnection = new Client(data-> {
			chatListView.getItems().add(data.toString());
		});

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.exit();
				System.exit(0);
			}
		});

//			clientConnection.start();
	}

	public void handlePlayerButton() throws Exception {




		primaryStage.setScene(sceneMap.get("player"));

	}
	public void handleAIButton() throws Exception {
		primaryStage.setScene(sceneMap.get("player"));
		primaryStage.setMaximized(true);
	}

	public void handleRulesButton() throws Exception {
		primaryStage.setScene(sceneMap.get("rulesScreen"));
		primaryStage.setMaximized(true);
	}

	public void handleBackButton(Scene previousScene) throws Exception {
		primaryStage.setScene(previousScene);
		primaryStage.setMaximized(true);
	}

	public void handleLeaveGameButton() throws Exception { // your mom
		sceneMap.remove("player");
		Scene newScene = createPlayerScene();
		sceneMap.put("player", newScene);
		primaryStage.setScene(sceneMap.get("startScreen"));
		primaryStage.setMaximized(true);
	}

	private Scene createStartScene() {
		BorderPane root = new BorderPane();
		Screen screen = Screen.getPrimary();
		double screenWidth = screen.getBounds().getWidth();
		double screenHeight = screen.getBounds().getHeight();

		root.setPrefSize(screenWidth, screenHeight);
		root.getStyleClass().add("battleship-start");

		Button rulesButton =  new Button("Rules");
		rulesButton.getStyleClass().add("rules-button");
		rulesButton.setOnAction(event -> {
			try {
				previousScene = primaryStage.getScene();
				handleRulesButton();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		Button playAgainstAIButton = new Button("Play Against AI");
		playAgainstAIButton.getStyleClass().add("play-button");
		playAgainstAIButton.setOnAction(event -> {
			try {
				handleAIButton();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		HBox topContainer = new HBox(rulesButton);
		topContainer.setAlignment(Pos.CENTER_RIGHT);
		root.setTop(topContainer);

		Label titleLabel = new Label("BATTLESHIP");
		titleLabel.getStyleClass().add("title-label");

		Button playAgainstPlayerButton = new Button("Play Against Player");
		playAgainstPlayerButton.getStyleClass().add("play-button");
		playAgainstPlayerButton.setOnAction(event -> {
			playAgainstPlayerButton.setText("Waiting for player...");

			playAgainstPlayerButton.setDisable(true);
			playAgainstAIButton.setDisable(true);
			rulesButton.setDisable(true);

			try {

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		HBox playButtons = new HBox(20, playAgainstPlayerButton, playAgainstAIButton);
		playButtons.setAlignment(Pos.CENTER);

		VBox centerContainer = new VBox(20, titleLabel, playButtons);
		centerContainer.setAlignment(Pos.CENTER);
		root.setCenter(centerContainer);

		Scene startScene = new Scene(root);
		startScene.getStylesheets().add("/styles/styles1.css");

		return startScene;
	}

	private Scene createRulesScene() {
		BorderPane root = new BorderPane();
		Screen screen = Screen.getPrimary();
		double screenWidth = screen.getBounds().getWidth();
		double screenHeight = screen.getBounds().getHeight();

		root.setPrefSize(screenWidth, screenHeight);

		root.getStyleClass().add("battleship-start");

		HBox topBox = new HBox();
		topBox.getStyleClass().add("button-container");
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("back-button");

		backButton.setOnAction(event -> {
			try {
				handleBackButton(previousScene);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		topBox.getChildren().add(backButton);

		root.setTop(topBox);

		StackPane centerStackPane = new StackPane();
		centerStackPane.setAlignment(Pos.TOP_CENTER);

		Rectangle rectangle = new Rectangle(600, 700);
		rectangle.getStyleClass().add("rules-rectangle");

		Label titleLabel = new Label("HOW TO PLAY");
		titleLabel.getStyleClass().add("title-label");
		Label ruleLabel = new Label("\nEach player deploys his ships (of lengths varying\nfrom 2 to 5 squares) secretly on a square grid.\n\n" +
										"Then each player alternates shooting at the\nother's grid by clicking a location.\n\n" +
										"Each move is classified as a Hit! or Miss!\nYou try to deduce where the enemy ships are and sink \nthem. " +
										"\n\nFirst to do so wins.\n\n" +
										"There are 5 ships, all spanning 5-2 spaces.\n\n" +
										"Press Play Against Player to find a player.\n\n" +
										"Press Play Against AI to play against a computer.");

		ruleLabel.getStyleClass().add("title-rules");

		VBox rulesList = new VBox(titleLabel, ruleLabel);
		rulesList.setAlignment(Pos.TOP_CENTER);

		centerStackPane.getChildren().addAll(rectangle, rulesList);

		root.setCenter(centerStackPane);
		Scene rulesScene = new Scene(root);
		rulesScene.getStylesheets().add("/styles/styles3.css");


		return rulesScene;
	}

	private Scene createPlayerScene() {

		BorderPane root = new BorderPane();

		root.setPrefSize(600, 800);
		root.getStyleClass().add("battleship-start");

		Button rulesButton =  new Button("Rules");
		Button leaveGameButton = new Button("Leave Game");

		rulesButton.getStyleClass().add("rules-button");
		leaveGameButton.getStyleClass().add("rules-button");

		rulesButton.setOnAction(event -> {
			previousScene = primaryStage.getScene();

			try {
				handleRulesButton();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});

		leaveGameButton.setOnAction(event -> {
			try {
				handleLeaveGameButton();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		});



		enemyBoard = new Board(true, event -> {
				if (!running)
					return;

				Board.Cell cell = (Board.Cell) event.getSource();
				if (cell.wasShot)
					return;

				enemyTurn = !cell.shoot();

				if (enemyBoard.ships == 0) {
					System.out.println("YOU WIN");
					System.exit(0);
				}

				if (enemyTurn)
					enemyMove();
		});

		playerBoard = new Board(false, event -> {

			if (running)
					return;

				Board.Cell cell = (Board.Cell) event.getSource();
				if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
					if (--shipsToPlace == 0) {
						startGame();
					}
				}
		});

		HBox topContainer = new HBox(1000, leaveGameButton, rulesButton);
		topContainer.setAlignment(Pos.CENTER);
		root.setTop(topContainer);


		enemyBoard.setStyle("-fx-background-color: transparent;");
		playerBoard.setStyle("-fx-background-color: transparent;");

		VBox vbox = new VBox(35, enemyBoard, playerBoard);
		vbox.setAlignment(Pos.TOP_CENTER);

		root.setCenter(vbox);


		Screen screen = Screen.getPrimary();
		double screenWidth = screen.getBounds().getWidth();
		double screenHeight = screen.getBounds().getHeight();

		root.setPrefSize(screenWidth, screenHeight);
		Scene playerScreen = new Scene(root);
		playerScreen.getStylesheets().add("/styles/styles2.css");

		return playerScreen;
	}

//--------------------------------------------------------------

//Unused For now

		private void enemyMove() {
			while (enemyTurn) {
				int x = random.nextInt(10);
				int y = random.nextInt(10);

				Board.Cell cell = playerBoard.getCell(x, y);
				if (cell.wasShot)
					continue;

				enemyTurn = cell.shoot();

				if (playerBoard.ships == 0) {
					System.out.println("YOU LOSE");
					System.exit(0);
				}
			}
		}

		private void startGame() {
			// place enemy ships
			int type = 5;

			while (type > 0) {
				int x = random.nextInt(10);
				int y = random.nextInt(10);

				if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
					type--;
				}
			}

			running = true;
		}

		private void resetGame() {
			running = false;
			shipsToPlace = 5;
			enemyTurn = false;
		}
}