import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class BoardController {
        @FXML
    private VBox playerBoardDisplay;

    @FXML
    private VBox enemyBoardDisplay;

    private boolean running = false;
    private int shipsToPlace = 5;
    private boolean enemyTurn = false;
    private Random random = new Random();


    @FXML
    public void initialize() {
        Board playerBoard = new Board(false, event -> {
//            if (running) {
//                return;
//            }
//
//            Board.Cell cell = (Board.Cell) event.getSource();
//            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
//                if (--shipsToPlace == 0) {
////                    startGame();
//                }
//            }
        });
        playerBoardDisplay.getChildren().add(playerBoard);

        Board enemyBoard = new Board(true, event -> {

//            if (!running) {
//                return;
//            }
//
//            Board.Cell cell = (Board.Cell) event.getSource();
//
//            if (cell.wasShot) {
//                return;
//            }
//
//            enemyTurn = !cell.shoot();

//            if (enemyBoard.ships == 0) {
//
//            }

//            if (enemyTurn) {
//                enemyMove();
//            }
        });
        enemyBoardDisplay.getChildren().add(enemyBoard);

    }



    //static so each instance of controller can access to update

    private void handleCellClick(MouseEvent event) {
        Board.Cell clickedCell = (Board.Cell) event.getSource();

        if (clickedCell.wasShot) {

        }





    }
}
