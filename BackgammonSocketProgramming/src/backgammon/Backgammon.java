package backgammon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Backgammon {

    static int actualWidth = 1000;
    static int actualHeight = 450;
    static int extraWidth = 18;
    static int extraHeight = 43;

    static Piece selectedPiece = null;
    static Triangle target_triangle = null;

    static int X;
    static int Y;

    static int pieceR = 40; // radius
    static int triangleW = 60; // triangle width
    static int triangleH = 5 * pieceR; // triangle height
    static int middleBar = 50 * (triangleW / 60);

    static int dice1 = 1;
    static int dice2 = 1;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setSize(actualWidth + extraWidth, actualHeight + extraHeight); // width + 18, height + 43
        jFrame.setTitle("Backgammon");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LinkedList<Triangle> triangles = new LinkedList<>();
        addElements(triangles);

        Bar bar = new Bar();
        bar.setBounds(6 * triangleW, 0, middleBar, actualHeight);

        JPanel jPanel = new JPanel() {
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

                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(bar.x, bar.y, bar.width, bar.height);

                for (Triangle t : triangles) {
                    if (t.size() > 5) {
                        g.setColor(Color.GREEN);
                        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                        if (t.id < 12) {
                            g.drawString("" + t.size(), t.x[0] + selectedPiece.r / 2, t.y[1] - selectedPiece.r / 3);
                        } else {
                            g.drawString("" + t.size(), t.x[0] + selectedPiece.r / 2, t.y[1] + selectedPiece.r / 2);
                        }
                    }
                }

                BufferedImage image = null;
                BufferedImage image2 = null;
                try {
                    image = ImageIO.read(new File("./src/image/" + dice1 + ".png"));
                    image2 = ImageIO.read(new File("./src/image/" + dice2 + ".png"));

                } catch (IOException ex) {

                }
                g.drawImage(image, 6 * triangleW, actualHeight / 2 - middleBar, this);
                g.drawImage(image2, 6 * triangleW, actualHeight / 2, this);

                for (Piece p : bar.piecesYellow) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, p.r, p.r);
                }

                for (Piece p : bar.piecesBlue) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, p.r, p.r);
                }

                if (bar.sizeYellow() > 0) {
                    g.setColor(Color.GREEN);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                    g.drawString("" + bar.sizeYellow(), bar.x + pieceR / 2, bar.y + pieceR / 2);
                }
                
                if (bar.sizeBlue() > 0) {
                    g.setColor(Color.GREEN);
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                    g.drawString("" + bar.sizeBlue(), bar.x + pieceR / 2, bar.height - pieceR / 2);
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
                            if (target_triangle.size() > 0) {
                                if (target_triangle.getLast().color != selectedPiece.color) {
                                    Piece p = target_triangle.remove();
                                    bar.add(p);
                                }
                            }

                            target_triangle.add(selectedPiece);
                            break;
                        } catch (NoSuchElementException e) {

                        }

                    }

                }
                jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);
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

        JButton jButton = new JButton("Dice");
        jButton.setSize(70, 30);
        jButton.setLocation(800, 0);

        jButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Random random = new Random();
                dice1 = random.nextInt(6) + 1; // [1..6]
                dice2 = random.nextInt(6) + 1;
                System.out.println("dice1: " + dice1 + ", dice2: " + dice2);
                //jPanel.paint(grphcs);
                jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);

            }
        });

        jPanel.setLayout(null);
        jFrame.add(jButton);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        /*
        // get actual size
        Dimension actualSize = jFrame.getContentPane().getSize();
        actualWidth = actualSize.width;
        actualHeight = actualSize.height;

        extraWidth = jFrame.getWidth() - actualWidth;
        extraHeight = jFrame.getHeight() - actualHeight;

        System.out.println("actual width: " + actualWidth + " , actual height: " + actualHeight);
        System.out.println("width: " + jFrame.getWidth() + " , height: " + jFrame.getHeight());
         */
        for (Triangle t : triangles) {
            System.out.println("id: " + t.id + ", width: " + t.width);
        }

    }

    static void addElements(LinkedList list) {
        Color color = null;
        int partLenght = 6 * triangleW + middleBar;
        // Section 1
        for (int i = 6; i >= 1; i--) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{partLenght + (i - 1) * triangleW, partLenght + (triangleW / 2) + triangleW * (i - 1), partLenght + i * triangleW}, new int[]{0, triangleH, 0}, 6 - i, color));
        }

        // Section 2
        for (int i = 6; i >= 1; i--) {
            if (i % 2 == 0) {
                color = Color.BLACK;
            } else {
                color = Color.RED;
            }
            list.add(new Triangle(new int[]{(i - 1) * triangleW, (triangleW / 2) + triangleW * (i - 1), i * triangleW}, new int[]{0, triangleH, 0}, 12 - i, color));
        }

        // Section 3
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{(i - 1) * triangleW, (triangleW / 2) + triangleW * (i - 1), i * triangleW}, new int[]{actualHeight, actualHeight - triangleH, actualHeight}, i + 11, color));
        }

        // Section 4
        for (int i = 1; i <= 6; i++) {
            if (i % 2 == 0) {
                color = Color.RED;
            } else {
                color = Color.BLACK;
            }
            list.add(new Triangle(new int[]{partLenght + (i - 1) * triangleW, partLenght + (triangleW / 2) + triangleW * (i - 1), partLenght + i * triangleW}, new int[]{actualHeight, actualHeight - triangleH, actualHeight}, i + 17, color));
        }

        // Add Pieces
        int[] count = {2, 5, 3, 5, 5, 3, 5, 2};
        Color[] pieceColor = {Color.yellow, Color.blue, Color.blue, Color.yellow, Color.blue, Color.yellow, Color.yellow, Color.blue};
        int j = 0;
        Triangle t = null;
        for (int i = 0; i < 24; i++) { // in the 8 part, there are pieces
            if (i == 0 || i == 5 || i == 7 || i == 11) {
                t = (Triangle) list.get(i);
                for (int k = 0; k < count[j]; k++) {
                    t.pieces.add(new Piece(t.x[0] + ((triangleW - pieceR) / 2), 0 + (k * pieceR), pieceR, pieceColor[j]));
                }
                j++;
            } else if (i == 12 || i == 16 || i == 18 || i == 23) {
                t = (Triangle) list.get(i);
                for (int k = 1; k <= count[j]; k++) {
                    t.pieces.add(new Piece(t.x[0] + ((triangleW - pieceR) / 2), actualHeight - (k * pieceR), pieceR, pieceColor[j]));
                }
                j++;
            }
        }

        /*
        Triangle t = (Triangle) list.get(0);
        
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 0, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 0 + pieceR, pieceR, Color.yellow));

        t = (Triangle) list.get(5);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 0, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 60, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 120, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 180, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 240, pieceR, Color.blue));

        t = (Triangle) list.get(7);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 0, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 60, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 120, pieceR, Color.blue));

        t = (Triangle) list.get(11);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 0, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 60, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 120, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 180, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), 240, pieceR, Color.yellow));

        t = (Triangle) list.get(12);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 60, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 120, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 180, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 240, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 300, pieceR, Color.blue));

        t = (Triangle) list.get(16);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 60, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 120, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 180, pieceR, Color.yellow));

        t = (Triangle) list.get(18);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 60, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 120, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 180, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 240, pieceR, Color.yellow));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 300, pieceR, Color.yellow));

        t = (Triangle) list.get(23);
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 60, pieceR, Color.blue));
        t.pieces.add(new Piece(t.x[0] + ((triangleW-pieceR)/2), actualHeight - 120, pieceR, Color.blue));
         */
    }

}
