package keybinding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;


public class Deneme {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.setBounds(10, 10, 512, 700);
        //jFrame.setUndecorated(true);

        Kare kare1 = new Kare(32,32, "kare1");
        Kare kare2 = new Kare(64,64, "kare2");

        LinkedList<Kare> kareler = new LinkedList<>();

        kareler.add(kare1);
        kareler.add(kare2);

        Kare selectedKare = null;

        JPanel jpanel = new JPanel(){
            @Override
            public void paint(Graphics g) {
/*
                boolean isWhite = true;
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 16; j++) {
                        if (isWhite) {
                            g.setColor(new Color(235, 235, 208));
                        } else {
                            g.setColor(new Color(119, 148, 85));
                        }
                        g.fillRect(i * 32, j * 32, 32, 32);
                        isWhite = !isWhite;
                    }
                    isWhite = !isWhite;
                }
*/
                g.fillRect(kare1.x, kare1.y, 32, 32);
                g.fillRect(kare2.x, kare2.y, 32, 32);
            }
        };

        jFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                for (Kare k:kareler) {
                    if((k.x/32) == (e.getX()/32) && (k.y/32) == (e.getY()/32)){
                        System.out.println(k.name);
                    }
                }


            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e.getX() / 32);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                /*
                kare1.x = e.getX() -16;
                kare1.y = e.getY() -16;
                jFrame.repaint();*/
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        jFrame.add(jpanel);
        jFrame.setVisible(true);

    }



}
