package backgammon;

import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author asus
 */
public class Bar {

    int x, y, width, height;
    LinkedList<Piece> piecesYellow = new LinkedList<>(); // yellow
    LinkedList<Piece> piecesBlue = new LinkedList<>(); // blue

    public void add(Piece p) {

        if (p.color == Color.YELLOW) {
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
    
    public Piece remove(Color player){
        if(player == Color.YELLOW) return piecesYellow.removeLast();
        else return piecesBlue.removeLast();
    }
    
    public Piece get(Color player){
        if(player == Color.YELLOW) return piecesYellow.getLast();
        else return piecesBlue.getLast();
    }

    public boolean hasPiece(Color player) {
        if (player == Color.YELLOW && sizeYellow() > 0) {
            return true;
        } else if (player == Color.BLUE && sizeBlue() > 0) {
            return true;
        }
        return false;
    }

}
