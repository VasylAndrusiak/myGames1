package sample.checkers;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Controller;
import sample.checkers.Piece;

//Шашки
public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean light, int x, int y) {
        setWidth(Controller.TILE_SIZE);
        setHeight(Controller.TILE_SIZE);

        relocate(x * Controller.TILE_SIZE, y * Controller.TILE_SIZE);

        setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }
}