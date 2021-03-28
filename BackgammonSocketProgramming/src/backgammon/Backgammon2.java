package backgammon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Backgammon2 {

    static int actualWidth;
    static int actualHeight;
    static int extraWidth;
    static int extraHeight;

    static Piece selectedPiece = null;
    static Triangle target_triangle = null;

    static int X;
    static int Y;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(1218, 697); // width + 18, height + 43
        jFrame.setTitle("Backgammon");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.getContentPane().setBackground(Color.BLUE);

        LinkedList<Triangle> triangles = new LinkedList<>();
        addElements(triangles);

        JPanel jpanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                for (Triangle t : triangles) {
                    g.setColor(t.color);
                    g.fillPolygon(t.x, t.y, 3);

                    for (Piece p : t.pieces) {
                        g.setColor(p.color);
                        g.fillOval(p.x, p.y, p.r, p.r);
                    }
                }

                g.setColor(Color.YELLOW);
                g.fillRect(480, 0, 50, 650);

                for (Triangle t : triangles) {
                    if (t.size() > 5) {
                        g.setColor(Color.GREEN);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                        if (t.id < 12) {
                            g.drawString("+" + t.size(), t.x[0] + selectedPiece.r/3, t.y[1] - selectedPiece.r/3);
                        } else {
                            g.drawString("+" + t.size(),  t.x[0] + selectedPiece.r/3, t.y[1] + selectedPiece.r/2);
                        }
                    }
                }

            }

        };

        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                for (Triangle t : triangles) {

                    if ((t.x[0] <= X && t.x[2] >= X && t.y[1] >= Y && t.y[0] <= Y) || (t.x[0] <= X && t.x[2] >= X && t.y[1] <= Y && t.y[0] >= Y)) {
                        System.out.println(t.id);
                        try {
                            selectedPiece = t.pieces.removeLast();
                            target_triangle = triangles.get((t.id + 1) % 24);
                            target_triangle.add(selectedPiece);
                            break;
                        } catch (NoSuchElementException e) {

                        }

                    }

                }
                jFrame.repaint();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                X = me.getX() - extraWidth < 0 ? 0 : me.getX() - extraWidth;
                Y = me.getY() - extraHeight < 0 ? 0 : me.getY() - extraHeight;
                // System.out.println("X: " + X + ", Y:" + Y);
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
        actualWidth = actualSize.width;
        actualHeight = actualSize.height;

        extraWidth = jFrame.getWidth() - actualWidth;
        extraHeight = jFrame.getHeight() - actualHeight;

        System.out.println("actual width: " + actualWidth + " , actual height: " + actualHeight);
        System.out.println("width: " + jFrame.getWidth() + " , height: " + jFrame.getHeight());

        for (Triangle t : triangles) {
            System.out.println("id: " + t.id);
        }

    }

    static void addElements(LinkedList list) {
        Color color = null;

        // Section 1
        for (int i = 6; i >= 1; i--) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{530 + (i - 1) * 80, 530 + 40 + 80 * (i - 1), 530 + i * 80}, new int[]{0, 300, 0}, 6 - i, color));
        }

        // Section 2
        for (int i = 6; i >= 1; i--) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{(i - 1) * 80, 40 + 80 * (i - 1), i * 80}, new int[]{0, 300, 0}, 12 - i, color));
        }

        // Section 3
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{(i - 1) * 80, 40 + 80 * (i - 1), i * 80}, new int[]{650, 350, 650}, i + 11, color));
        }

        // Section 4
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{530 + (i - 1) * 80, 530 + 40 + 80 * (i - 1), 530 + i * 80}, new int[]{650, 350, 650}, i + 17, color));
        }

        Triangle t = (Triangle) list.get(0);
        t.pieces.add(new Piece(t.x[0] + 10, 0, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 60, 60, Color.yellow));

        t = (Triangle) list.get(5);
        t.pieces.add(new Piece(t.x[0] + 10, 0, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 60, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 120, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 180, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 240, 60, Color.blue));

        t = (Triangle) list.get(7);
        t.pieces.add(new Piece(t.x[0] + 10, 0, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 60, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 120, 60, Color.blue));

        t = (Triangle) list.get(11);
        t.pieces.add(new Piece(t.x[0] + 10, 0, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 60, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 120, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 180, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 240, 60, Color.yellow));

        t = (Triangle) list.get(12);
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 60, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 120, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 180, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 240, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 300, 60, Color.blue));

        t = (Triangle) list.get(16);
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 60, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 120, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 180, 60, Color.yellow));

        t = (Triangle) list.get(18);
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 60, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 120, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 180, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 240, 60, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 300, 60, Color.yellow));

        t = (Triangle) list.get(23);
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 60, 60, Color.blue));
        t.pieces.add(new Piece(t.x[0] + 10, 650 - 120, 60, Color.blue));

    }

}
