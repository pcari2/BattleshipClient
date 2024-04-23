
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiClient extends Application {


	private HashMap<String, Scene> sceneMap;
	Client clientConnection;
	private Stage primaryStage;
	private boolean running = false;
	private Board enemyBoard, playerBoard;

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
		primaryStage.setTitle("Battleship");
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

	public void handlePlayerButton() throws Exception {

	}
	public void handleAIButton() throws Exception {
		primaryStage.setScene(sceneMap.get("playerScreen"));
	}

	public void handleRulesButton() throws Exception {
		primaryStage.setScene(sceneMap.get("rulesScreen"));
	}

	public void handleBackButton() throws Exception {
		primaryStage.setScene(sceneMap.get("startScreen"));
	}

	private Scene createStartScene() {
		BorderPane root = new BorderPane();
		root.setPrefSize(600, 400);
		root.getStyleClass().add("battleship-start");

		Button rulesButton =  new Button("Rules");
		rulesButton.getStyleClass().add("rules-button");
		rulesButton.setOnAction(event -> {
			try {
				handleRulesButton();
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
			try {
				handlePlayerButton();
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

		HBox playButtons = new HBox(20, playAgainstAIButton, playAgainstPlayerButton);
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
		root.setPrefSize(600, 400);
		root.getStyleClass().add("battleship-start");

		HBox topBox = new HBox();
		topBox.getStyleClass().add("button-container");
		Button backButton = new Button("Back");
		backButton.setOnAction(event -> {
            try {
                handleBackButton();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
		topBox.getChildren().add(backButton);

		root.setTop(topBox);

		HBox centerBox = new HBox(20);
		centerBox.setAlignment(Pos.CENTER);
		Label titleLabel = new Label("RULES");
		titleLabel.getStyleClass().add("title-label");
		Label ruleLabel = new Label("Put all the rules here!");
		ruleLabel.getStyleClass().add("title-rules");
		centerBox.getChildren().addAll(titleLabel, ruleLabel);

		root.setCenter(centerBox);
		Scene rulesScene = new Scene(root);
		rulesScene.getStylesheets().add("/styles/styles3.css");


		return rulesScene;
	}

	private Scene createPlayerScene() {
			BorderPane root = new BorderPane();
			root.setPrefSize(600, 800);
			root.getStyleClass().add("battle-start");

			enemyBoard = new Board(true, event -> {
//				if (!running)
//					return;
//
//				Board.Cell cell = (Board.Cell) event.getSource();
//				if (cell.wasShot)
//					return;
//
//				enemyTurn = !cell.shoot();
//
//				if (enemyBoard.ships == 0) {
//					System.out.println("YOU WIN");
//					System.exit(0);
//				}
//
//				if (enemyTurn)
//					enemyMove();
			});

			playerBoard = new Board(false, event -> {
//				if (running)
//					return;
//
//				Board.Cell cell = (Board.Cell) event.getSource();
//				if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
//					if (--shipsToPlace == 0) {
//						startGame();
//					}
// poopy				}
			});

			VBox vbox = new VBox(50, enemyBoard, playerBoard);
			vbox.setAlignment(Pos.CENTER);

			root.setCenter(vbox);

			Scene playerScreen = new Scene(root);

			return playerScreen;
		}

//		private void enemyMove() { your mom
//			while (enemyTurn) {
//				int x = random.nextInt(10);
//				int y = random.nextInt(10);
//
//				Board.Cell cell = playerBoard.getCell(x, y);
//				if (cell.wasShot)
//					continue;
//
//				enemyTurn = cell.shoot();
//
//				if (playerBoard.ships == 0) {
//					System.out.println("YOU LOSE");
//					System.exit(0);
//				}
//			}
//		}

//		private void startGame() {
//			// place enemy ships
//			int type = 5;
//
//			while (type > 0) {
//				int x = random.nextInt(10);
//				int y = random.nextInt(10);
//
//				if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
//					type--;
//				}
//			}
//
//			running = true;
//		}
	}



