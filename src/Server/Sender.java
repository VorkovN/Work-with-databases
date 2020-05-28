package Server;

import Client.User;
import Msg.MessageToServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class Sender implements Runnable {

    SocketChannel socket = null;
    MessageToServer msg = null;
    User user = null;
    ObjectOutputStream toClient;

    public Sender(SocketChannel socket, MessageToServer msg, User user) {
        this.socket = socket;
        this.msg = msg;
        this.user = user;
    }

    public Sender(SocketChannel socket, User user) {
        this.socket = socket;
        this.user = user;
        this.toClient = toClient;
    }

    @Override
    public void run() {
        ObjectOutputStream toClient = null;
        try {
            toClient = new ObjectOutputStream(socket.socket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                if (msg != null){
                    toClient.writeObject(msg);
                    toClient.writeObject(user);
                }
                else{
                    toClient.writeObject(user);
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
