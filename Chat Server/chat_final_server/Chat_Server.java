package chat_final_server;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat_Server {

    public static ArrayList<Socket> Connections = new ArrayList<Socket>();
    public static ArrayList<String> Users = new ArrayList<String>();
    public static String prefix_string = "#?!";
    public static void main(String[] args) {
        
        try {
            final int PORT = 1234;
            ServerSocket ServSock = new ServerSocket(PORT);
            System.out.println("Waiting for Clients...");
            
            while(true){
                
                Socket sock=ServSock.accept();
                Connections.add(sock);
                
                System.out.println("Connected From "+sock.getLocalAddress().getHostName());
                
                AddUser(sock);
                
                Chat_Server_Return csr=new Chat_Server_Return(sock);
                Thread th=new Thread(csr);
                th.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Chat_Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void AddUser(Socket sock) throws IOException{
        
         Scanner Input=new Scanner(sock.getInputStream());
         String Username=Input.nextLine();
         Users.add(Username);
         
         
         for(int i=1;i<=Chat_Server.Connections.size();i++){
             Socket temp_Sock=Chat_Server.Connections.get(i-1);
             PrintWriter out=new PrintWriter(temp_Sock.getOutputStream());
             out.println(prefix_string+Users);
             out.flush();
         }
         
    }
}
