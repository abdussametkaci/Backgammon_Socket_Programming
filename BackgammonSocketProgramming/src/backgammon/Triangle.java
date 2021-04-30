package backgammon;

import java.awt.Color;
import java.util.LinkedList;

public class Triangle implements java.io.Serializable {

    int[] x; // x coordinates, it has 3 values
    int[] y; // y coordinates, it has 3 values
    public int id;
    int width;
    Color color;

    public LinkedList<Piece> pieces = new LinkedList<>(); // own pieces

    public Triangle() {

    }

    public Triangle(int[] x, int[] y, int id, Color color) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;
        this.width = x[2] - x[0];
    }

    public void add(Piece p) {
        if (pieces.isEmpty()) {
            if (this.id < 12) {
                p.setCoordinate(this.x[0] + 10, this.y[0]);
            } else {
                p.setCoordinate(this.x[0] + 10, this.y[0] - p.r);
            }
        } else {
            if (pieces.size() >= 5) {
                p.setCoordinate(pieces.getLast().x, pieces.getLast().y);
            } else if (this.id < 12) {
                p.setCoordinate(pieces.getLast().x, pieces.getLast().y + p.r);
            } else {
                p.setCoordinate(pieces.getLast().x, pieces.getLast().y - p.r);
            }
        }

        pieces.add(p);

    }

    public Piece getLast() {
        return pieces.getLast();
    }

    public Piece remove() {
        return pieces.removeLast();
    }

    public int size() {
        return pieces.size();
    }
    
    public boolean isEmpty(){
        return pieces.isEmpty();
    }

}
