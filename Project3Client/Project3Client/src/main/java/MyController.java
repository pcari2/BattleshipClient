import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class MyController {

    @FXML
    private Button playAgainstPlayerButton;
    @FXML
    private Button playAgainstAIButton;
    @FXML
    private Button rulesButton;
    @FXML
    private Button backButton;

    @FXML
    private VBox playerBoardDisplay;

    @FXML
    private VBox enemyBoardDisplay;






    //static so each instance of controller can access to update

    @FXML
    public void handlePlayerButton() {

    }

    @FXML
    public void handleAIButton() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/playScreen.fxml"));
        Stage window = (Stage) playAgainstAIButton.getScene().getWindow();
        Scene newScene = new Scene(root, 800, 500);
        newScene.getStylesheets().add(getClass().getResource("/styles/styles2.css").toExternalForm());
        window.setScene(newScene);
    }

    @FXML
    public void handleRulesButton() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/rulesScreen.fxml"));
        Stage window = (Stage) rulesButton.getScene().getWindow();
        Scene newScene = new Scene(root, 800, 500);
        newScene.getStylesheets().add(getClass().getResource("/styles/styles3.css").toExternalForm());
        window.setScene(newScene);
    }

    @FXML
    public void handleBackButton() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/startScreen.fxml"));
        Stage window = (Stage) backButton.getScene().getWindow();
        Scene newScene = new Scene(root, 800, 500);
        newScene.getStylesheets().add(getClass().getResource("/styles/styles1.css").toExternalForm());
        window.setScene(newScene);
    }

    private void handleCellClick(MouseEvent event) {
        Board.Cell clickedCell = (Board.Cell) event.getSource();
    }
    }


