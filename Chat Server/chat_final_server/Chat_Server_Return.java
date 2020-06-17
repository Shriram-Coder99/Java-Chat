package chat_final_server;


import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat_Server_Return implements Runnable {

    Socket sock;
    private Scanner in;
    private PrintWriter out;
    String msg = "";

    public Chat_Server_Return(Socket sock) {

        this.sock = sock;
    }

    public void checkConnection() throws IOException {

        if (!sock.isConnected()) {

            for (int i = 1; i <= Chat_Server.Connections.size(); i++) {

                if (Chat_Server.Connections.get(i) == sock) {

                    Chat_Server.Connections.remove(i);
                }

            }

            for (int i = 1; i <= Chat_Server.Connections.size(); i++) {
                Socket Temp_Sock = Chat_Server.Connections.get(i - 1);
                PrintWriter temp_out = new PrintWriter(Temp_Sock.getOutputStream());
                temp_out.println(Temp_Sock.getLocalAddress().getHostName() + "disconnected");
                temp_out.flush();
                //for server
                System.out.println(Temp_Sock.getLocalAddress().getHostName() + "disconnected");
            }
        }
    }

    public void run() {

        try {
            try {
                in = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());

                while (true) {
                    checkConnection();

                    if (!in.hasNext()) {
                        return;
                    }

                    msg = in.nextLine();

                    System.out.println("Client said: " + msg);

                    for (int i = 1; i <= Chat_Server.Connections.size(); i++) {
                        Socket Temp_Sock = Chat_Server.Connections.get(i - 1);
                        PrintWriter temp_out = new PrintWriter(Temp_Sock.getOutputStream());
                        temp_out.println(msg);
                        temp_out.flush();
                        System.out.println("Sent to:" + Temp_Sock.getLocalAddress().getHostName());
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Chat_Server_Return.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                sock.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(Chat_Server_Return.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
