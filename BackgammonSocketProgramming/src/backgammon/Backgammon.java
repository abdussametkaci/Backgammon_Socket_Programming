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
import Client.Client;
import Message.Message;
import javax.swing.JLabel;

public class Backgammon {

    public static Backgammon ThisGame;

    static Color CurrentPlayer; // = Color.YELLOW
    static Color playerColor;

    static int actualWidth = 1000;
    static int actualHeight = 450;
    static int extraWidth = 18;
    static int extraHeight = 43;

    Piece selectedPiece = null;
    Triangle target_triangle = null;

    int X;
    int Y;

    static int pieceR = 40; // radius
    static int triangleW = 60; // triangle width
    static int triangleH = 5 * pieceR; // triangle height
    static int middleBar = 50 * (triangleW / 60);

    static int dice1 = 1;
    static int dice2 = 1;
    int play = 0;    // each players can play 2, if dices are same, they can play 4
    boolean playDice = false;   // has the dice been rolled ?
    int[] steps = {0, 0, 0, 0};
    static JFrame jFrame;   // my frame
    static LinkedList<Triangle> triangles; // contains pieces
    static Bar bar; // contains eaten pieces

    // show color of player and current player
    static JLabel playerLabel;
    static JLabel currentLabel;
    static JLabel giveUpLabel;
    static boolean givedUp = false;

    public Backgammon() {
        ThisGame = this;
        jFrame = new JFrame();
        jFrame.setSize(actualWidth + extraWidth, actualHeight + extraHeight); // width + 18, height + 43
        jFrame.setTitle("Backgammon");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        triangles = new LinkedList<>();
        addElements(triangles);

        bar = new Bar();
        bar.setBounds(6 * triangleW, 0, middleBar, actualHeight);

        // draw all window elements
        JPanel jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                // draw pieces
                for (Triangle t : triangles) {
                    g.setColor(t.color);
                    g.fillPolygon(t.x, t.y, 3);

                    for (Piece p : t.pieces) {
                        g.setColor(p.color);
                        g.fillOval(p.x, p.y, p.r, p.r);
                    }
                }

                // draw bar
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(bar.x, bar.y, bar.width, bar.height);

                // write text, if a triangles has more than 5 pieces
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

                // draw dieces
                BufferedImage image = null;
                BufferedImage image2 = null;
                try {
                    image = ImageIO.read(new File("./src/image/" + dice1 + ".png"));
                    image2 = ImageIO.read(new File("./src/image/" + dice2 + ".png"));

                } catch (IOException ex) {

                }
                g.drawImage(image, 6 * triangleW, actualHeight / 2 - middleBar, this);
                g.drawImage(image2, 6 * triangleW, actualHeight / 2, this);

                // draw eaten pieces
                for (Piece p : bar.piecesYellow) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, p.r, p.r);
                }

                for (Piece p : bar.piecesBlue) {
                    g.setColor(p.color);
                    g.fillOval(p.x, p.y, p.r, p.r);
                }

                // write text that how many bar has pieces
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

        // mouse cliecked for moving piece
        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if(givedUp){
                    Client.Stop();
                    return;
                }
                // game algorithm
                try {
                    if (!playerColor.equals(CurrentPlayer)) {
                        return;
                    }
                } catch (java.lang.NullPointerException e){
                    return;
                }

                if (!playDice) {
                    return;
                }

                // if bar own a pices of current player
                if (bar.hasPiece(CurrentPlayer)) {
                    System.out.println("bar, current player: " + CurrentPlayer);
                    if (bar.x <= X && bar.x + bar.width >= X) { // cliecked bar
                        boolean played = false;
                        play++;
                        if (play == 1) {

                            if (CurrentPlayer.equals(Color.BLUE)) {
                                target_triangle = triangles.get(24 - dice1);
                                if (triangles.get(24 - dice1).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(24 - dice1).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }

                            } else {
                                target_triangle = triangles.get(dice1 - 1);
                                if (triangles.get(dice1 - 1).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(dice1 - 1).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }
                            }

                        } else if (play <= 4) {

                            if (CurrentPlayer.equals(Color.BLUE)) {
                                target_triangle = triangles.get(24 - dice2);
                                if (triangles.get(24 - dice2).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(24 - dice2).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }

                            } else {
                                target_triangle = triangles.get(dice2 - 1);
                                if (triangles.get(dice2 - 1).isEmpty() || CurrentPlayer.equals(target_triangle.getLast().color)) {
                                    target_triangle.add(bar.remove(CurrentPlayer));
                                } else if (triangles.get(dice2 - 1).size() == 1) {
                                    Piece p = bar.remove(CurrentPlayer);
                                    bar.add(target_triangle.remove());
                                    target_triangle.add(p);
                                }
                            }

                            played = true;
                        }

                        // change next player and reset play
                        if ((dice1 == dice2 && play >= 4) || (dice1 != dice2 && play >= 2) || (!played)) {
                            // change next player
                            changeCurrentPlayer();
                            play = 0;
                            playDice = false;

                            Message msg = new Message(Message.Message_Type.ChangePlayer);
                            msg.content = "Change Player";
                            Client.Send(msg);
                        }

                    }
                } else {

                    for (Triangle t : triangles) {
                        // cliecked triangle
                        if ((t.x[0] <= X && t.x[2] >= X && t.y[1] >= Y && t.y[0] <= Y) || (t.x[0] <= X && t.x[2] >= X && t.y[1] <= Y && t.y[0] >= Y)) {
                            System.out.println(t.id);
                            try {
                                selectedPiece = t.getLast();
                                if (!selectedPiece.color.equals(CurrentPlayer)) { // is it correct player
                                    System.out.println("piece color: " + selectedPiece.color + ", current player color: " + CurrentPlayer);
                                    return;
                                }

                                // Selection targte triangle
                                play++;
                                if (play == 1) {
                                    if (CurrentPlayer.equals(Color.YELLOW)) {
                                        target_triangle = triangles.get((t.id + dice1) % 24);
                                    } else {
                                        target_triangle = triangles.get((t.id - dice1) % 24);
                                    }
                                } else if (play <= 4) {
                                    if (CurrentPlayer.equals(Color.YELLOW)) {
                                        target_triangle = triangles.get((t.id + dice2) % 24);
                                    } else {
                                        target_triangle = triangles.get((t.id - dice2) % 24);
                                    }
                                }

                                // eat piece
                                if (target_triangle.size() == 1 && (!target_triangle.getLast().color.equals(selectedPiece.color))) {
                                    Piece p = target_triangle.remove();
                                    bar.add(p);
                                } else if (target_triangle.size() > 1 && (!target_triangle.getLast().color.equals(selectedPiece.color))) {
                                    play--;
                                    return;
                                }

                                selectedPiece = t.remove();
                                target_triangle.add(selectedPiece);
                                System.out.println("player: " + CurrentPlayer + ", play: " + play);
                                if ((dice1 == dice2 && play >= 4) || (dice1 != dice2 && play >= 2)) {
                                    // change next player
                                    changeCurrentPlayer();
                                    play = 0;
                                    playDice = false;

                                    Message msg = new Message(Message.Message_Type.ChangePlayer);
                                    msg.content = "Change Player";
                                    Client.Send(msg);
                                }
                                break;
                            } catch (NoSuchElementException e) {

                            }

                        }

                    }
                }

                //played piece
                // redraw the game board
                jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);
                /*
                for (Triangle t : triangles) {
                    System.out.println("t: " + t.id + ", size: " + t.size());
                }
                 */

                // send message when a piece move
                Message msg_p = new Message(Message.Message_Type.Triangles);
                LinkedList<Triangle> t = new LinkedList<>();
                t.addAll(triangles);
                msg_p.content = t;
                Client.Send(msg_p);

                Message msg_b = new Message(Message.Message_Type.Bar);
                Bar b = new Bar();
                b.piecesBlue.addAll(bar.piecesBlue);
                b.piecesYellow.addAll(bar.piecesYellow);
                msg_b.content = bar;
                Client.Send(msg_b);
                System.out.println("sended pieces");

                System.out.println("player: " + playerColor + "current color: " + CurrentPlayer);

            }

            @Override
            public void mousePressed(MouseEvent me) {
                // mouse cliecked coordinates and fixed them
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

        JButton connectServer = new JButton("Connect"); // connect button
        connectServer.setSize(90, 30);
        connectServer.setLocation(800, 0);

        connectServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client.Start("127.0.0.1", 2000);
                //Client.Send(msg);
            }
        });

        JButton diceButton = new JButton("Dice"); // dice button
        diceButton.setSize(90, 30);
        diceButton.setLocation(800, 50);

        diceButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Random random = new Random();
                dice1 = random.nextInt(6) + 1; // [1..6]
                dice2 = random.nextInt(6) + 1;
                steps[0] = dice1;
                steps[1] = dice2;
                System.out.println("dice1: " + dice1 + ", dice2: " + dice2);
                if (dice1 == dice2) {
                    steps[2] = dice2;
                    steps[3] = dice2;
                } else {
                    steps[2] = 0;
                    steps[3] = 0;
                }
                //jPanel.paint(grphcs);
                playDice = true;
                System.out.println("current player: " + CurrentPlayer);
                jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);

                // send dices when dices rolled
                Message msg = new Message(Message.Message_Type.Dice);
                //msg.content = "dice1: " + dice1 + ", dice2: " + dice2;
                int dices[] = {dice1, dice2};
                msg.content = dices;
                Client.Send(msg);

            }
        });

        JButton giveUp = new JButton("Give Up"); // give up button
        giveUp.setSize(90, 30);
        giveUp.setLocation(800, 100);

        giveUp.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                // send dices when dices rolled
                Message msg = new Message(Message.Message_Type.GiveUp);

                int dices[] = {dice1, dice2};
                msg.content = "Give Up";
                Client.Send(msg);
                giveUpLabel.setText("Game Over!");
            }
        });

        playerLabel = new JLabel();
        playerLabel.setSize(150, 30);
        playerLabel.setLocation(800, 130);
        playerLabel.setText("Your Color: ");

        currentLabel = new JLabel();
        currentLabel.setSize(150, 30);
        currentLabel.setLocation(800, 180);
        currentLabel.setText("Current Player Color: ");

        giveUpLabel = new JLabel();
        giveUpLabel.setSize(150, 30);
        giveUpLabel.setLocation(800, 230);

        jPanel.setLayout(null);

        jFrame.add(diceButton);
        jFrame.add(connectServer);
        jFrame.add(giveUp);
        jFrame.add(playerLabel);
        jFrame.add(currentLabel);
        jFrame.add(giveUpLabel);
        jFrame.add(jPanel);

        jFrame.setVisible(true);

        /* find actual size
        // get actual size
        Dimension actualSize = jFrame.getContentPane().getSize();
        actualWidth = actualSize.width;
        actualHeight = actualSize.height;

        extraWidth = jFrame.getWidth() - actualWidth;
        extraHeight = jFrame.getHeight() - actualHeight;

        System.out.println("actual width: " + actualWidth + " , actual height: " + actualHeight);
        System.out.println("width: " + jFrame.getWidth() + " , height: " + jFrame.getHeight());
         */
 /*
        for (Triangle t : triangles) {
            System.out.println("id: " + t.id + ", width: " + t.width);
        }
         */
    }

    // add all elements to lists
    void addElements(LinkedList list) {
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

    }

    public static void repaint() {
        jFrame.repaint(0, 0, 12 * triangleW + middleBar + extraWidth, actualHeight + extraHeight);
    }

    public static void setDices(int d1, int d2) {
        dice1 = d1;
        dice2 = d2;
    }

    public static void setTriangles(LinkedList<Triangle> t) {
        triangles.clear();
        triangles.addAll(t);

    }

    public static void setBar(Bar b) {
        bar.piecesBlue.clear();
        bar.piecesYellow.clear();
        bar.piecesBlue.addAll(b.piecesBlue);
        bar.piecesYellow.addAll(b.piecesYellow);
    }

    public static void setColor(int c) {
        if (c == 0) {
            CurrentPlayer = Color.YELLOW;
            playerColor = Color.YELLOW;
        } else {
            CurrentPlayer = Color.YELLOW;
            playerColor = Color.BLUE;
        }
        playerLabel.setText("<html>Your Color:<br/>" + (playerColor.equals(Color.YELLOW) ? "Yellow" : "Blue") + "</html>");
        currentLabel.setText("<html>Current Player Color:<br/>" + (CurrentPlayer.equals(Color.YELLOW) ? "Yellow" : "Blue") + "</html>");
    }

    public static void changeCurrentPlayer() {
        if (CurrentPlayer.equals(Color.YELLOW)) {
            CurrentPlayer = Color.BLUE;
        } else {
            CurrentPlayer = Color.YELLOW;
        }

        currentLabel.setText("<html>Current Player Color:<br/>" + (CurrentPlayer.equals(Color.YELLOW) ? "Yellow" : "Blue") + "</html>");
    }

    public static void giveUp() {
        giveUpLabel.setText("WIN!");
    }

    public static void main(String[] args) {
        new Backgammon();

    }

}
