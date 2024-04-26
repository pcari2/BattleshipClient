import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OpponentBoard extends Board {
    public OpponentBoard(String boardString, EventHandler<? super MouseEvent> handler) {
        super(true, handler); // Set isEnemy to true for opponent's board

        // Populate board with cells based on the boardString
        int index = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                char cellState = boardString.charAt(index++);
                Cell cell = getCell(x, y);
                if (cellState == 'S') {
                    // If the cell represents a ship ('S'), set the ship property of the cell
                    Ship ship = new Ship(1, true); // Example: Create a ship object, adjust parameters as needed
                    placeShip(ship, x, y);
                }
            }
        }
    }
}