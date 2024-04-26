
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;


public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean isEnemy = false;
    public int ships = 5;
    private Ship[][] grid;



    public Board(boolean isEnemy, EventHandler<? super MouseEvent> handler) {
        this.isEnemy = isEnemy;

        // Populating Board with Cells
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            // Adds row to list of rows
            rows.getChildren().add(row);

        }

        // Add rows to the Parent to display it
        getChildren().add(rows);
    }

    public void setShip(int x, int y, Ship ship) {
        grid[x][y] = ship;
    }

    protected VBox getRows() {
        return rows;
    }

    public boolean shootCell(int x, int y) {
        Cell cell = getCell(x, y);
        if (!cell.wasShot) { // Check if the cell hasn't been shot before
            return cell.shoot(); // Shoot the cell and return the result
        }
        return false; // Cell was already shot
    }

    public boolean placeShip(Ship ship, int x, int y) {
        if (canPlaceShip(ship, x, y)) { // checks if you can place ship

            int length = ship.type;

            // ship is vertical check y values
            if (ship.vertical) {
                for (int i = y; i < y + length; i++) {
                    Cell cell = getCell(x, i);
                    cell.ship = ship;
                    if (!isEnemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }

            }
            // if ship is not vertical check x values hey guys
            else {
                for (int i = x; i < x + length; i++) {
                    Cell cell = getCell(i, y);
                    cell.ship = ship;
                    if (!isEnemy) {
                        cell.setFill(Color.WHITE);
                        cell.setStroke(Color.GREEN);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    private Cell[] getNeighbors(int x, int y) {
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1),
        };

        List<Cell> neighbors = new ArrayList<>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }
        return neighbors.toArray(new Cell[0]);
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.type;

        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i)) {
                    return false;
                }

                Cell cell = getCell(x, i);
                if (cell.ship != null) {
                    return false;
                }

                for (Cell neighbor : getNeighbors(x, 1)) {
                    if (!isValidPoint(x, i)) {
                        return false;
                    }

                    if (neighbor.ship != null) {
                        return false;
                    }
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(x, i)) {
                    return false;
                }

                Cell cell = getCell(i, y);
                if (cell.ship != null) {
                    return false;
                }

                for (Cell neighbor : getNeighbors(x, 1)) {
                    if (!isValidPoint(i, y)) {
                        return false;
                    }

                    if (neighbor.ship != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public String serializeBoardState() {
        StringBuilder boardState = new StringBuilder();

        // Iterate over each cell in the board
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Cell cell = getCell(x, y);
                char cellState = cell.ship != null ? 'S' : 'E'; // 'S' for ship, 'E' for empty
                boardState.append(cellState);
            }
        }

        return boardState.toString();
    }

    public class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;

        private Board board;

        public Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.TRANSPARENT);
            setStroke(Color.BLACK);

            Color lightColor = Color.rgb(255, 255, 255, 0.6);
            setFill(lightColor);
        }
        public void setShip() {
            ship = new Ship(1, false);
            return;
        }

        public boolean shoot() {
            wasShot = true;
            setFill(Color.BLACK);

            if (ship != null) {
                ship.hit();
                setFill(Color.RED);
                if (!ship.isAlive()) {
                    board.ships--;
                }
                return true;
            }
            return false;
        }
    }
}
