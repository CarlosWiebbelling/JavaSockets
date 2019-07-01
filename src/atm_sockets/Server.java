package atm_sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    
    public static int port = 3000;
    private static ATMMachine sys;
    
    public static void main(String args[]){

        try {
            ServerSocket server = new ServerSocket(port);                       
            System.out.println("Servidor on port " + port);

            Socket client = server.accept();
            System.out.println("Conected with the client IP " + client.getInetAddress().getHostAddress());
            
            sys = new ATMMachine("admin", client, server);
            
            sys.options();

        } catch (IOException e) {
            System.out.println("err: " + e);
        }
    }
}