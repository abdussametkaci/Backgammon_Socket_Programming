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

    public Piece remove(Piece p) {
        if (p.color == Color.YELLOW) {
            return piecesYellow.remove();
        } else {
            return piecesBlue.remove();
        }
    }

    public void setBounds(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public int sizeYellow(){
        return piecesYellow.size();
    }
    
    public int sizeBlue(){
        return piecesBlue.size();
    }
    
}
