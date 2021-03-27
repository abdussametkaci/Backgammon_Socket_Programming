package backgammon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Backgammon2 {

    static int actualWidth = 0;
    static int actualHeight = 0;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(1218, 697); // width + 18, height + 43
        jFrame.setTitle("Backgammon");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(Color.BLUE);

        LinkedList<Triangle> triangles = new LinkedList<>();
        addElements(triangles);
        
        LinkedList<Triangle> pieces = new LinkedList<>();
        addElements(pieces);
        
        JPanel jpanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                for (Triangle t : triangles) {
                    g.setColor(t.color);
                    g.fillPolygon(t.x, t.y, 3);
                }
                
                g.setColor(Color.YELLOW);
                g.fillRect(480, 0, 50, 650);
            }

        };

        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (10 <= me.getX() && me.getX() <= 30 && 20 <= me.getY() && me.getY() <= 100) {
                    System.out.println("clicked");
                }
            }

            @Override
            public void mousePressed(MouseEvent me) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent me) {
                // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

        jFrame.add(jpanel);
        jFrame.setVisible(true);

        // get actual size
        Dimension actualSize = jFrame.getContentPane().getSize();
        actualWidth = actualSize.height;
        actualHeight = actualSize.width;

        System.out.println("width: " + actualWidth + " , height: " + actualHeight);

        for (Triangle t : triangles) {
            System.out.println("id: " + t.id);
        }

    }

    static void addElements(LinkedList list) {
        Color color = null;
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            }
            else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{(i - 1) * 80, 40 + 80 * (i - 1), i * 80}, new int[]{0, 300, 0}, 0, i, color));
        }

        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            }
            else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{530 + (i - 1) * 80, 530 + 40 + 80 * (i - 1), 530 + i * 80}, new int[]{0, 300, 0}, 0, i + 6, color));
        }

        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            }
            else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{530 + (i - 1) * 80, 530 + 40 + 80 * (i - 1), 530 + i * 80}, new int[]{650, 350, 650}, 0, i + 12, color));
        }

        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            }
            else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{(i - 1) * 80, 40 + 80 * (i - 1), i * 80}, new int[]{650, 350, 650}, 0, i + 18, color));
        }
    }

}
