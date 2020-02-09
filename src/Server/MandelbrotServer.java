package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * MandelbrotServer class
 * (1) Define incoming port number
 * (2) Listen to port for incoming request
 * (3) As soon as there is a new socket opened, create a new thread
 * (4) Pass the income stream to ClientHandler for further processing
 */
public class MandelbrotServer {

    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket ss = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket s = null;
            try
            {
                // socket object to receive incoming client requests
                s = ss.accept();

                // Console message
                System.out.println("*** A new client is connected : " + s);

                // obtaining input and out streams
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                OutputStream out = new DataOutputStream(s.getOutputStream());

                // Console message
                System.out.println("*** Assigning new thread for this client");

                // As soon as there is a new client connected, create a new thread object
                Thread t = new ClientHandler(s, dis, dos, out);

                // Start the thread
                t.start();

            }
            catch (Exception e){
                s.close();
                e.printStackTrace();
            }
        }
    }

}
