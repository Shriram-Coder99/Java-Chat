
import java.net.*;
import java.util.Scanner;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Sai Shriram
 */
public class Chat_Client implements Runnable {

    Socket sock;
    Scanner in;
    Scanner send = new Scanner(System.in);
    PrintWriter out;

    public Chat_Client(Socket sock) {
        this.sock = sock;
    }

    public void run() {
        try {
            try {
                in = new Scanner(sock.getInputStream());
                out = new PrintWriter(sock.getOutputStream());
                out.flush();
                CheckStream();
            } finally {
                sock.close();
            }
        } catch (Exception ex) {
            Logger.getLogger(Chat_Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void send(String text) {
        out.println(Chat_Client_GUI.username + ": " + text);
        out.flush();
        Chat_Client_GUI.Msg_Field.setText("");
    }

    void disconnect() throws IOException {
        out.println(Chat_Client_GUI.username  + " has disconnected");
        out.flush();
        sock.close();
        JOptionPane.showMessageDialog(null,"You Have Disconnected!!!");
        System.exit(0);
    }

    private void CheckStream() {
        while (true) {
            receive();
        }
    }

    private void receive() {

        if (in.hasNext()) {
            String msg = in.nextLine();

            if (msg.contains("#?!")) {
                String temp = msg.substring(3);
                   temp=temp.replace("[","");
                   temp=temp.replace("]","");

                String[] CurrentUsers = temp.split(", ");
                Chat_Client_GUI.Online_List.setListData(CurrentUsers);

            } else {
                Chat_Client_GUI.Conversation_TA.append(msg + "\n");
            }
        }
    }
}
