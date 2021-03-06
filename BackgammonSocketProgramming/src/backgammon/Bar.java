package backgammon;

import java.awt.Color;
import java.util.LinkedList;

public class Bar implements java.io.Serializable {

    int x, y, width, height;
    LinkedList<Piece> piecesYellow = new LinkedList<>(); // yellow pieces
    LinkedList<Piece> piecesBlue = new LinkedList<>(); // blue pieces
    
    // add piece for color and set coordinates
    public void add(Piece p) {

        if (p.color.equals(Color.YELLOW)) {
            p.setCoordinate(x + 5, y);
            piecesYellow.add(p);
        } else {
            p.setCoordinate(x + 5, height - p.r);
            piecesBlue.add(p);
        }
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int sizeYellow() {
        return piecesYellow.size();
    }

    public int sizeBlue() {
        return piecesBlue.size();
    }
    
    // remove last piece by using color from bar
    public Piece remove(Color player) {
        if (player.equals(Color.YELLOW)) {
            return piecesYellow.removeLast();
        } else {
            return piecesBlue.removeLast();
        }
    }
    
    // get piece selected color from bar
    public Piece get(Color player) {
        if (player.equals(Color.YELLOW)) {
            return piecesYellow.getLast();
        } else {
            return piecesBlue.getLast();
        }
    }
    
    // check a player has piece on bar
    public boolean hasPiece(Color player) {
        if (player.equals(Color.YELLOW) && sizeYellow() > 0) {
            return true;
        } else if (player.equals(Color.BLUE) && sizeBlue() > 0) {
            return true;
        }
        return false;
    }

}
