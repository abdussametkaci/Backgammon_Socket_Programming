package Server;

import Message.Message;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

// thread of coming client
class ServerThread extends Thread {

    public void run() {
        // listen until server closed
        while (!Server.serverSocket.isClosed()) {
            try {
                // wait client
                Server.Display("Client waiting...");
                //waiting until coming a client
                Socket clientSocket = Server.serverSocket.accept();
                // client came
                Server.Display("Client came...");
                // create a client object by using coming client from socket
                SClient nclient = new SClient(clientSocket, Server.IdClient);

                Server.IdClient++;
                // add client to list
                Server.Clients.add(nclient);
                // start listen thread for client
                nclient.listenThread.start();

            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

public class Server {

    public static ServerSocket serverSocket;
    public static int IdClient = 0;
    public static int port = 0;
    // Thread object to keep the server listening continuously
    public static ServerThread runThread;
    //public static PairingThread pairThread;

    public static ArrayList<SClient> Clients = new ArrayList<>();

    public static Semaphore pairTwo = new Semaphore(1, true);

    public static void Start(int openport) {
        try {
            Server.port = openport;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.runThread = new ServerThread();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Display(String msg) {
        System.out.println(msg);
    }

    // send message from server to clients
    // get client and send message
    public static void Send(SClient cl, Message msg) {

        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
