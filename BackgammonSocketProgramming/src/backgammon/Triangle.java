package backgammon;

import java.awt.Color;
import java.util.LinkedList;


public class Triangle {
    int[] x;
    int[] y;
    int size;
    int id;
    Color color;
    
    LinkedList<Piece> pieces = new LinkedList<>();
    
    public Triangle(){
        
    }
    
    public Triangle(int[] x, int[] y, int size, int id, Color color){
        this.x = x;
        this.y = y;
        this.size = size;
        this.id = id;
        this.color = color;
    }
    
}
