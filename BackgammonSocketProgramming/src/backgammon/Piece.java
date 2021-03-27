package backgammon;

import java.awt.Color;

public class Piece {
    int x;
    int y;
    int r; // radius
    Color color;
    
    public Piece(int x, int y, int r, Color color){
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
    }
}
