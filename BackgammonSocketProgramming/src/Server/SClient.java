package Server;

import Message.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;

public class SClient {

    int id;
    Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    Listen listenThread; // Thread of thing that come from client
    PairingThread pairThread; // Thread of pair
    SClient rival;  // rival client

    public boolean paired = false;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.listenThread = new Listen(this);
        this.pairThread = new PairingThread(this);

    }

    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Thread of listening to client
    // every cilent own listen thread
    class Listen extends Thread {

        SClient TheClient;

        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            // keep going as long as client is connected
            while (TheClient.soket.isConnected()) {
                try {
                    // wait coming message
                    Message received = (Message) (TheClient.sInput.readObject());
                    // if a message come
                    // find message type
                    switch (received.type) {
                        case Connected:
                            TheClient.pairThread.start();
                            break;
                        case Disconnected:
                            break;
                        case RivalConnected:
                            break;
                        case Dice:
                            // send coming message to rival client
                            Server.Send(TheClient.rival, received);
                            break;
                        case Triangles:
                            Server.Send(TheClient.rival, received);
                            break;
                        case Bar:
                            Server.Send(TheClient.rival, received);
                            break;
                        case Color:
                            Server.Send(TheClient.rival, received);
                            break;
                        case ChangePlayer:
                            Server.Send(TheClient.rival, received);
                            break;
                        case GiveUp:
                            Server.Send(TheClient.rival, received);
                            break;
                        
                    }

                } catch (IOException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    // if client is diconnected, delete it from the list
                    Server.Clients.remove(TheClient);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    Server.Clients.remove(TheClient);
                }
            }

        }
    }

    // every client has pairing thread
    class PairingThread extends Thread {

        SClient TheClient;

        PairingThread(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            // keep going as long as client is connected and threre is no paired
            while (TheClient.soket.isConnected() && TheClient.paired == false) {
                try {
                    //lock mechanizm
                    // only one client can come
                    // wait the others until they release
                    Server.pairTwo.acquire(1);

                    if (!TheClient.paired) {
                        SClient crival = null;
                        // keep going until there is a paired
                        while (crival == null && TheClient.soket.isConnected()) {
                            // search rival
                            for (SClient clnt : Server.Clients) {
                                if (TheClient != clnt && clnt.rival == null) {
                                    crival = clnt;
                                    crival.paired = true;
                                    crival.rival = TheClient;
                                    TheClient.rival = crival;
                                    TheClient.paired = true;
                                    break;
                                }
                            }

                            sleep(1000); // 1 second sleep
                        }
                        // paired is ok
                        // send a message to two clients (paired clients)
                        // start game
                        Message msg1 = new Message(Message.Message_Type.RivalConnected);
                        msg1.content = "Rival Connected";
                        Server.Send(TheClient.rival, msg1);

                        Message msg2 = new Message(Message.Message_Type.RivalConnected);
                        msg2.content = "Rival Connected";
                        Server.Send(TheClient, msg2);

                        // choose color
                        Message msg3 = new Message(Message.Message_Type.Color);
                        msg3.content = 1;   // BLUE
                        Server.Send(TheClient.rival, msg3);

                        Message msg4 = new Message(Message.Message_Type.Color);
                        msg4.content = 0;   // YELLOW
                        Server.Send(TheClient, msg4);
                    }
                    // relesase the lock
                    // we can do this. Otherwise, it occur a deadlock
                    Server.pairTwo.release(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PairingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
