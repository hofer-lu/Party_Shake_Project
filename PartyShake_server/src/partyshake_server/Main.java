/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package partyshake_server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;

/**
 *
 * @author hofer_lu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        // TODO code application logic here
        Main aa = new Main();
        aa.start();
    }

    public void start() throws IOException {
        Server_Party_Ds SP_DataBase = new Server_Party_Ds();
        Client_Db C_DataBase= new Client_Db();
        int port_num = 5800;
        ServerSocket socket = new ServerSocket(port_num);
        while (true) {
            Socket Sck = socket.accept();
            Server_client cc = new Server_client(Sck,C_DataBase,SP_DataBase);
            new Thread(cc).start();
        }
    }   
}

