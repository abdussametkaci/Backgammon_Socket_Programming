package backgammon;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Polygon;

public class Backgammon extends JFrame {
    
    static int extraWidth = 0;
    static int extraHeight = 0;
    
    public Backgammon() {
        add(new Panel());
    }

    public static void main(String[] args) {
        Backgammon frame = new Backgammon();
        frame.setTitle("Figuras1");
        frame.setSize(1500, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // get actual size
        Dimension actualSize = frame.getContentPane().getSize();
        extraHeight = actualSize.height;
        extraWidth = actualSize.width;
    }
}

class Panel extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //marco del tablero
        g.setColor(Color.orange);
        g.fillRect(0, 0, 700, 553);
        g.setColor(Color.black);
        g.fillRect(30, 30, 620, 505);
        g.setColor(Color.orange);
        g.fillRect(330, 0, 20, 553);

        // triangulos abajo izquierda
        g.setColor(Color.RED);
        int valoresX[] = {55, 30, 80};
        int valoresY[] = {300, 535, 535};
        Polygon poligono1 = new Polygon(valoresX, valoresY, 3);
        g.fillPolygon(poligono1);

        g.setColor(Color.white);
        int valoresX2[] = {105, 80, 130};
        int valoresY2[] = {300, 535, 535};
        Polygon poligono2 = new Polygon(valoresX2, valoresY2, 3);
        g.fillPolygon(poligono2);

        g.setColor(Color.red);
        int valoresX3[] = {155, 130, 180};
        int valoresY3[] = {300, 535, 535};
        Polygon poligono3 = new Polygon(valoresX3, valoresY3, 3);
        g.fillPolygon(poligono3);

        g.setColor(Color.white);
        int valoresX4[] = {205, 180, 230};
        int valoresY4[] = {300, 535, 535};
        Polygon poligono4 = new Polygon(valoresX4, valoresY4, 3);
        g.fillPolygon(poligono4);

        g.setColor(Color.red);
        int valoresX5[] = {255, 230, 280};
        int valoresY5[] = {300, 535, 535};
        Polygon poligono5 = new Polygon(valoresX5, valoresY5, 3);
        g.fillPolygon(poligono5);

        g.setColor(Color.white);
        int valoresX6[] = {305, 280, 330};
        int valoresY6[] = {300, 535, 535};
        Polygon poligono6 = new Polygon(valoresX6, valoresY6, 3);
        g.fillPolygon(poligono6);

        //triangulos abajo derecha 
        g.setColor(Color.RED);
        int valoresX7[] = {375, 350, 400};
        int valoresY7[] = {300, 535, 535};
        Polygon poligono7 = new Polygon(valoresX7, valoresY7, 3);
        g.fillPolygon(poligono7);

        g.setColor(Color.white);
        int valoresX8[] = {425, 400, 450};
        int valoresY8[] = {300, 535, 535};
        Polygon poligono8 = new Polygon(valoresX8, valoresY8, 3);
        g.fillPolygon(poligono8);

        g.setColor(Color.red);
        int valoresX9[] = {475, 450, 500};
        int valoresY9[] = {300, 535, 535};
        Polygon poligono9 = new Polygon(valoresX9, valoresY9, 3);
        g.fillPolygon(poligono9);

        g.setColor(Color.white);
        int valoresXa[] = {525, 500, 550};
        int valoresYa[] = {300, 535, 535};
        Polygon poligonoa = new Polygon(valoresXa, valoresYa, 3);
        g.fillPolygon(poligonoa);

        g.setColor(Color.red);
        int valoresXb[] = {575, 550, 600};
        int valoresYb[] = {300, 535, 535};
        Polygon poligonob = new Polygon(valoresXb, valoresYb, 3);
        g.fillPolygon(poligonob);

        g.setColor(Color.white);
        int valoresXc[] = {625, 600, 650};
        int valoresYc[] = {300, 535, 535};
        Polygon poligonoc = new Polygon(valoresXc, valoresYc, 3);
        g.fillPolygon(poligonoc);

        //tringulos arriba izquierda 
        g.setColor(Color.white);
        int valoresXd[] = {55, 30, 80};
        int valoresYd[] = {250, 30, 30};
        Polygon poligonod = new Polygon(valoresXd, valoresYd, 3);
        g.fillPolygon(poligonod);

        g.setColor(Color.red);
        int valoresXe[] = {105, 80, 130};
        int valoresYe[] = {250, 30, 30};
        Polygon poligonoe = new Polygon(valoresXe, valoresYe, 3);
        g.fillPolygon(poligonoe);

        g.setColor(Color.white);
        int valoresXf[] = {155, 130, 180};
        int valoresYf[] = {250, 30, 30};
        Polygon poligonof = new Polygon(valoresXf, valoresYf, 3);
        g.fillPolygon(poligonof);

        g.setColor(Color.red);
        int valoresXg[] = {205, 180, 230};
        int valoresYg[] = {250, 30, 30};
        Polygon poligonog = new Polygon(valoresXg, valoresYg, 3);
        g.fillPolygon(poligonog);

        g.setColor(Color.white);
        int valoresXh[] = {255, 230, 280};
        int valoresYh[] = {250, 30, 30};
        Polygon poligonoh = new Polygon(valoresXh, valoresYh, 3);
        g.fillPolygon(poligonoh);

        g.setColor(Color.red);
        int valoresXi[] = {305, 280, 330};
        int valoresYi[] = {250, 30, 30};
        Polygon poligonoi = new Polygon(valoresXi, valoresYi, 3);
        g.fillPolygon(poligonoi);

        //triangulos arriba derecha
        g.setColor(Color.white);
        int valoresXj[] = {375, 350, 400};
        int valoresYj[] = {250, 30, 30};
        Polygon poligonoj = new Polygon(valoresXj, valoresYj, 3);
        g.fillPolygon(poligonoj);

        g.setColor(Color.red);
        int valoresXk[] = {425, 400, 450};
        int valoresYk[] = {250, 30, 30};
        Polygon poligonok = new Polygon(valoresXk, valoresYk, 3);
        g.fillPolygon(poligonok);

        g.setColor(Color.white);
        int valoresXl[] = {475, 450, 500};
        int valoresYl[] = {250, 30, 30};
        Polygon poligonol = new Polygon(valoresXl, valoresYl, 3);
        g.fillPolygon(poligonol);

        g.setColor(Color.red);
        int valoresXm[] = {525, 500, 550};
        int valoresYm[] = {250, 30, 30};
        Polygon poligonom = new Polygon(valoresXm, valoresYm, 3);
        g.fillPolygon(poligonom);

        g.setColor(Color.white);
        int valoresXn[] = {575, 550, 600};
        int valoresYn[] = {250, 30, 30};
        Polygon poligonon = new Polygon(valoresXn, valoresYn, 3);
        g.fillPolygon(poligonon);

        g.setColor(Color.red);
        int valoresXo[] = {625, 600, 650};
        int valoresYo[] = {250, 30, 30};
        Polygon poligonoo = new Polygon(valoresXo, valoresYo, 3);
        g.fillPolygon(poligonoo);

    }
}
