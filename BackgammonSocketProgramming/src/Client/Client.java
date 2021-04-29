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
import static Message.Message.Message_Type.Color;
import backgammon.Backgammon;
import backgammon.Bar;
import backgammon.Triangle;
import java.awt.Color;
import java.util.LinkedList;

// Thread of listening coming messages from server
class Listen extends Thread {

    public void run() {

        while (Client.socket.isConnected()) {
            try {
                // listen the coming message by blocking
                Message received = (Message) (sInput.readObject());
                switch (received.type) {
                    case RivalConnected:
                        String rivalMessage = received.content.toString();
                        System.out.println(rivalMessage);
                        break;
                    case Disconnected:
                        break;
                    case Dice:
                        int[] recv = (int[]) received.content;
                        System.out.println("dice1: " + recv[0] + ", dice2: " + recv[1]);
                        Backgammon.setDices(recv[0], recv[1]);
                        Backgammon.repaint();
                        break;
                    case Triangles:
                        Backgammon.setTriangles((LinkedList<Triangle>) received.content);
                        Backgammon.repaint();
                        System.out.println("get triangles");
                        break;
                    case Bar:
                        Bar b = (Bar) received.content;
                        Backgammon.setBar(b);
                        Backgammon.repaint();
                        System.out.println("get bar");
                        break;
                    case Color:
                        Backgammon.setColor((int) received.content);
                        System.out.println("client color: " + ((int) received.content));
                        break;
                    case ChangePlayer:
                        Backgammon.changeCurrentPlayer();
                        System.out.println("asdasdasdasd");
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

    public static Socket socket;

    public static ObjectInputStream sInput; // for receiving data
    public static ObjectOutputStream sOutput; // for transmitting data

    public static Listen listenMe;

    public static void Start(String ip, int port) {
        try {
            // Client Soket
            Client.socket = new Socket(ip, port);
            Client.Display("Connected to server");
            // input stream
            Client.sInput = new ObjectInputStream(Client.socket.getInputStream());
            // output stream
            Client.sOutput = new ObjectOutputStream(Client.socket.getOutputStream());
            Client.listenMe = new Listen();
            Client.listenMe.start();

            // it is first message
            // message connected
            Message msg = new Message(Message.Message_Type.Connected);
            msg.content = "Connected";
            Client.Send(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // stop client
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

    public static void Send(Message msg) {
        try {
            Client.sOutput.writeObject(msg);
            sOutput.reset(); // it is so important !!!
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
