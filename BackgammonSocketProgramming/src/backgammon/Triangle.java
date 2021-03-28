package backgammon;

import java.awt.Color;
import java.util.LinkedList;


public class Triangle {
    int[] x;
    int[] y;
    int id;
    Color color;
    
    LinkedList<Piece> pieces = new LinkedList<>();
    
    public Triangle(){
        
    }
    
    public Triangle(int[] x, int[] y, int id, Color color){
        this.x = x;
        this.y = y;
        this.id = id;
        this.color = color;
    }
    
    public void add(Piece p){
        if(pieces.isEmpty()){
            if(this.id < 12) p.setCoordinate(this.x[0] + 10, this.y[0]);
            else p.setCoordinate(this.x[0] + 10, this.y[0] - p.r);
        }
        else {
            if(pieces.size() >= 5) p.setCoordinate(pieces.getLast().x, pieces.getLast().y);
            else if(this.id < 12) p.setCoordinate(pieces.getLast().x, pieces.getLast().y + p.r);
            else p.setCoordinate(pieces.getLast().x, pieces.getLast().y - p.r);
        }
        
        pieces.add(p);
        
    }
    
    public int size(){
        return pieces.size();
    }
    
    
}
