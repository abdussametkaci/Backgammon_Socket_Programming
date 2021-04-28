/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static Client.Client.sInput;
import static Message.Message.Message_Type.Bar;
import backgammon.Backgammon;
import backgammon.Bar;
import backgammon.Piece;
import backgammon.Triangle;
import java.util.LinkedList;

/**
 *
 * @author INSECT
 */
// serverdan gelecek mesajları dinleyen thread
class Listen extends Thread {

    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini bloking olarak dinyelen komut
                Message received = (Message) (sInput.readObject());
                //mesaj gelirse bu satıra geçer
                //mesaj tipine göre yapılacak işlemi ayır.
                switch (received.type) {
                    case RivalConnected:
                        
                        String rivalMessage = received.content.toString();
                        System.out.println(rivalMessage);
                        break;
                    case Disconnect:
                        break;
                    case Dice:
                        //Backgammon.ThisGame.txt_receive.setText(received.content.toString());
                        int[] recv = (int[])received.content;
                        System.out.println("dice1: " + recv[0] + ", dice2: " + recv[1]);
                        Backgammon.setDices(recv[0], recv[1]);
                        Backgammon.repaint();
                        break;
                    case Triangles:
                        //LinkedList<Triangle> r = (LinkedList<Triangle>) received.content;
                        /*
                        System.out.println("triangles: ");
                        for(Triangle t : r){
                            System.out.println("t: " + t.id + ", size: " + t.size());
                        }
                        */
                                
                        Backgammon.setTriangles((LinkedList<Triangle>) received.content);
                        Backgammon.repaint();
                        System.out.println("taslari aldim");
                        break;
                    case Bar:
                        Bar b = (Bar) received.content;
                        Backgammon.setBar(b);
                        Backgammon.repaint();
                        System.out.println("bari aldim");
                        break;
                    case Selected:
                        Backgammon.ThisGame.RivalSelection = (int) received.content;
                        break;

                    case Bitis:
                        break;

                }

            } catch (IOException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            }
        }

    }
}

public class Client {

    //her clientın bir soketi olmalı
    public static Socket socket;

    //verileri almak için gerekli nesne
    public static ObjectInputStream sInput;
    //verileri göndermek için gerekli nesne
    public static ObjectOutputStream sOutput;
    //serverı dinleme thredi 
    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket nesnesi
            Client.socket = new Socket(ip, port);
            Client.Display("Servera bağlandı");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            //ilk mesaj olarak isim gönderiyorum
            Message msg = new Message(Message.Message_Type.Name);
            //msg.content = Game.ThisGame.txt_name.getText();
            msg.content = "Player";
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //client durdurma fonksiyonu
    public static void Stop() {
        try {
            if (Client.socket != null) {
                Client.listenMe.stop();
                Client.socket.close();
                Client.sOutput.flush();
                Client.sOutput.close();

                Client.sInput.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void Display(String msg) {

        System.out.println(msg);

    }

    //mesaj gönderme fonksiyonu
    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
