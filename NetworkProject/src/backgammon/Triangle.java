package backgammon;

import java.awt.Color;


public class Triangle {
    int[] x;
    int[] y;
    int size;
    int id;
    Color color;
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
