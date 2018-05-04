package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	//constructor to initialize the server socket class
	//borrowed from class notes
	
	
	public static void main(String args[]){
	
		ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1230);
            System.out.println("listening on port: 1230");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1230.");
            System.exit(1);
        }
        
        DefaultSocketServer clientSocket = null;
        try {
        //	clientSocket = new DefaultSocketClient(serverSocket.accept(), "some", 1232);
       // 	clientSocket = serverSocket.accept();
            clientSocket = new DefaultSocketServer(serverSocket.accept(), "server socket", 1230);
            clientSocket.start(); 
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        
	}
	
	

}
